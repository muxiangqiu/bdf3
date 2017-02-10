package com.bstek.bdf3.dbconsole.view.datatab;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import com.bstek.bdf3.dbconsole.model.ColumnInfo;
import com.bstek.bdf3.dbconsole.service.DbService;
import com.bstek.bdf3.dbconsole.utils.ColumnTypeUtils;
import com.bstek.dorado.data.type.DefaultEntityDataType;
import com.bstek.dorado.data.type.manager.DataTypeManager;
import com.bstek.dorado.data.type.property.BasePropertyDef;
import com.bstek.dorado.data.type.property.PropertyDef;
import com.bstek.dorado.view.View;
import com.bstek.dorado.view.widget.base.toolbar.Button;
import com.bstek.dorado.view.widget.data.DataSet;
import com.bstek.dorado.view.widget.datacontrol.DataPilot;
import com.bstek.dorado.view.widget.grid.DataColumn;
import com.bstek.dorado.view.widget.grid.DataGrid;
import com.bstek.dorado.view.widget.grid.RowSelectorColumn;
import com.bstek.dorado.web.DoradoContext;

@Controller
public class DataTabInterceptor {
	private static final Logger log = Logger.getLogger(DataTabInterceptor.class);
	private static final String COLUMN_WIDTH = "150";
	private static final String SIMPLE_TYPE = "1";
	private static final String MULTI_TYPE = "2";

	@Autowired
	@Qualifier(DbService.BEAN_ID)
	private DbService dbService;

	public void onInit(DefaultEntityDataType dataTypeData) throws Exception {
		DoradoContext dc = DoradoContext.getCurrent();
		String dbInfoId = (String) dc.getAttribute(DoradoContext.VIEW, "dbInfoId");
		String tableName = (String) dc.getAttribute(DoradoContext.VIEW, "tableName");
		String sql = (String) dc.getAttribute(DoradoContext.VIEW, "sql");
		String type = (String) dc.getAttribute(DoradoContext.VIEW, "type");
		List<ColumnInfo> columns = null;
		if (type.equals(SIMPLE_TYPE)) {
			if (StringUtils.hasText(tableName)) {
				columns = dbService.findColumnInfos(dbInfoId, tableName);
			}
		} else {
			columns = dbService.findMultiColumnInfos(dbInfoId, sql);
		}
		DataTypeManager dataTypeManager = (DataTypeManager) DoradoContext.getCurrent().getServiceBean("dataTypeManager");
		BasePropertyDef pd;
		if (columns != null) {
			for (ColumnInfo info : columns) {
				pd = new BasePropertyDef();
				PropertyDef propertyDef = dataTypeData.getPropertyDef(info.getColumnName());
				if (propertyDef != null) {
					continue;
				}
				pd.setName(info.getColumnName());
				log.debug(String.format("datagrid[columnName=%s,columnType=%s]", info.getColumnName(), info.getColumnType()));
				String doradoType = ColumnTypeUtils.getDroadoType(info);
				if (StringUtils.hasText(doradoType)) {
					pd.setDataType(dataTypeManager.getDataType(doradoType));
				}
				if (type.equals(SIMPLE_TYPE)) {
					if (info.isIsnullAble()) {
						pd.setRequired(false);
					} else {
						pd.setRequired(true);
					}
				}
				dataTypeData.addPropertyDef(pd);
			}
		}
	}

	public void onInit(View view) throws Exception {
		DataPilot dataPilotData = (DataPilot) view.getViewElement("dataPilotData");
		Button buttonSave = (Button) view.getViewElement("toolBarButtonSave");
		DataSet dataSetData = (DataSet) view.getViewElement("dataSetData");
		DataGrid dataGridData = (DataGrid) view.getViewElement("dataGridData");
		this.onInit(dataSetData);
		this.onInit(dataGridData);
		String type = (String) DoradoContext.getCurrent().getAttribute(DoradoContext.VIEW, "type");
		if (type.endsWith(MULTI_TYPE)) {
			dataGridData.setReadOnly(true);
			dataPilotData.setIgnored(true);
			buttonSave.setIgnored(true);
		}

	}

	private void onInit(DataSet dataSetData) throws Exception {
		String dbInfoId = (String) DoradoContext.getCurrent().getAttribute(DoradoContext.VIEW, "dbInfoId");
		String tableName = (String) DoradoContext.getCurrent().getAttribute(DoradoContext.VIEW, "tableName");
		String sql = (String) DoradoContext.getCurrent().getAttribute(DoradoContext.VIEW, "sql");
		String type = (String) DoradoContext.getCurrent().getAttribute(DoradoContext.VIEW, "type");
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("dbInfoId", dbInfoId);
		parameter.put("tableName", tableName);
		parameter.put("sql", sql);
		parameter.put("type", type);
		dataSetData.setParameter(parameter);
	}

	private void onInit(DataGrid dataGridData) throws Exception {
		DoradoContext dc = DoradoContext.getCurrent();
		String dbInfoId = (String) dc.getAttribute(DoradoContext.VIEW, "dbInfoId");
		String tableName = (String) dc.getAttribute(DoradoContext.VIEW, "tableName");
		String sql = (String) dc.getAttribute(DoradoContext.VIEW, "sql");
		String type = (String) dc.getAttribute(DoradoContext.VIEW, "type");
		List<ColumnInfo> columns = null;
		if (type.equals(SIMPLE_TYPE)) {
			if (StringUtils.hasText(tableName)) {
				columns = dbService.findColumnInfos(dbInfoId, tableName);
			}
			RowSelectorColumn selector = new RowSelectorColumn();
			selector.setVisible(true);
			selector.setIgnored(false);
			selector.setSupportsOptionMenu(true);
			dataGridData.addColumn(selector);
		} else {
			columns = dbService.findMultiColumnInfos(dbInfoId, sql);
		}
		DataColumn column;
		if (columns != null) {
			for (ColumnInfo info : columns) {
				column = new DataColumn();
				column.setName(info.getColumnName());
				column.setWidth(COLUMN_WIDTH);
				dataGridData.addColumn(column);
			}

		}
	}

}
