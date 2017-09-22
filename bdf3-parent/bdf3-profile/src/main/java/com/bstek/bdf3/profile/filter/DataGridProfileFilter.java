package com.bstek.bdf3.profile.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.bstek.bdf3.profile.domain.ComponentConfig;
import com.bstek.bdf3.profile.domain.ComponentConfigMember;
import com.bstek.dorado.view.ViewElement;
import com.bstek.dorado.view.widget.grid.Column;
import com.bstek.dorado.view.widget.grid.ColumnGroup;
import com.bstek.dorado.view.widget.grid.DataColumn;
import com.bstek.dorado.view.widget.grid.DataGrid;
import com.bstek.dorado.view.widget.grid.IndicatorColumn;
import com.bstek.dorado.view.widget.grid.RowNumColumn;
import com.bstek.dorado.view.widget.grid.RowSelectorColumn;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年7月17日
 */
@Component
@Order(100)
public class DataGridProfileFilter implements ProfileFilter<DataGrid> {

	@Override
	public void apply(DataGrid dataGrid, ComponentConfig config) {
		try {
			if (StringUtils.isNotEmpty(config.getCols())) {
				int fixedColumnCount = Integer.parseInt(config.getCols());
				dataGrid.setFixedColumnCount(fixedColumnCount);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (config.getComponentConfigMembers() != null) {
			applyChildren(dataGrid, config.getComponentConfigMembers());
		}
	}

	private void applyChildren(DataGrid dataGrid, List<ComponentConfigMember> columnProfiles) {
		Map<String, Column> columnMap = parseMap(dataGrid.getColumns());
		Map<String, Column> topMap = new HashMap<>();
		Map<String, List<Column>> childrenMap = new HashMap<>();
		dataGrid.getColumns().clear();
		

		for (ComponentConfigMember cp : columnProfiles) {
			Column column = getOrCreateColumn(columnMap, cp);
			if (column != null) {
				buildColumn(column, cp, topMap, childrenMap, dataGrid);
				column.setVisible(cp.getVisible());
				column.setCaption(cp.getCaption());
				if (DataColumn.class.getSimpleName().equals(cp.getControlType())) {
					((DataColumn) column).setWidth(cp.getWidth());
				}
			}
		}
	}
	
	private void buildColumn(Column column, ComponentConfigMember cp, Map<String, Column> topMap,
			Map<String, List<Column>> childrenMap, DataGrid dataGrid) {
		if (column instanceof ColumnGroup) {
			( (ColumnGroup) column).getColumns().clear();
		}
		if (cp.getParentControlName() == null) {
			dataGrid.addColumn(column);
			topMap.put(cp.getControlName(), column);
		} else {
			if (topMap.containsKey(cp.getParentControlName())) {
				Column c = topMap.get(cp.getParentControlName());
				if (c instanceof ColumnGroup) {
					( (ColumnGroup) c).addColumn(column);
				}
			} else {
				List<Column> children = childrenMap.get(cp.getParentControlName());
				if (children == null) {
					children = new ArrayList<>();
					childrenMap.put(cp.getParentControlName(), children);
				}
				children.add(column);
			}
		}
		
		if (childrenMap.containsKey(cp.getControlName())) {
			if (column instanceof ColumnGroup) {
				( (ColumnGroup) column).getColumns().addAll(childrenMap.get(cp.getControlName()));
			}
		}
		
	}

	private Column getOrCreateColumn(Map<String, Column> columnMap, ComponentConfigMember cp) {
		if (columnMap.containsKey(cp.getControlName())) {
			return columnMap.get(cp.getControlName());
		}
		if (ColumnGroup.class.getSimpleName().equals(cp.getControlType())) {
			return new ColumnGroup();
		}
		if (IndicatorColumn.class.getSimpleName().equals(cp.getControlType())) {
			return new IndicatorColumn();
		}
		if (RowNumColumn.class.getSimpleName().equals(cp.getControlType())) {
			return new RowNumColumn();
		}
		if (RowSelectorColumn.class.getSimpleName().equals(cp.getControlType())) {
			return new RowSelectorColumn();
		}
		return null;
	}
	
	
	private Map<String, Column> parseMap(List<Column> columns) {
		Map<String, Column> map = new HashMap<>();
		for (Column column : columns) {
			if (column instanceof ColumnGroup) {
				map.putAll(parseMap(((ColumnGroup) column).getColumns()));
			}
			String name = column.getName();
			if (StringUtils.isNotEmpty(name)) {
				map.put(column.getName(), column);
			}
		}
		return map;
	}

	@Override
	public boolean support(ViewElement viewElement) {
		return viewElement instanceof DataGrid;
	}

}
