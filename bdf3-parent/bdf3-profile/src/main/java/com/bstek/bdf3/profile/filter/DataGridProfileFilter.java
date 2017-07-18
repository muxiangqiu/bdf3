package com.bstek.bdf3.profile.filter;

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

		for (ComponentConfigMember cp : columnProfiles) {
			Column column = columnMap.get(cp.getControlName());
			if (column != null) {
				column.setVisible(cp.getVisible());
				if (DataColumn.class.getSimpleName().equals(cp.getControlType())) {
					((DataColumn) column).setWidth(cp.getWidth());
				}
			}
		}
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
