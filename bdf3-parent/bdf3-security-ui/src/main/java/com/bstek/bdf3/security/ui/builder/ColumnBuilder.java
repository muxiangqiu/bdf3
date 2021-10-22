package com.bstek.bdf3.security.ui.builder;

import java.util.Collection;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import com.bstek.bdf3.security.ui.utils.DoradoUtil;
import com.bstek.dorado.data.type.EntityDataType;
import com.bstek.dorado.view.View;
import com.bstek.dorado.view.ViewElement;
import com.bstek.dorado.view.manager.ViewConfig;
import com.bstek.dorado.view.widget.grid.Column;
import com.bstek.dorado.view.widget.grid.ColumnGroup;
import com.bstek.dorado.view.widget.grid.DataColumn;
import com.bstek.dorado.view.widget.grid.DataGrid;

@Component("intro.maintain.columnBuilder")
public class ColumnBuilder extends AbstractBuilder<Column> {
	
	protected String getId(Column column){
		String id = column.getName();
		if (StringUtils.isEmpty(id)) {
			id = column.getCaption();
		}
		return id;
	}
	
	protected Collection<Column> getChildren(Column column){
		if(column instanceof ColumnGroup){
			return ((ColumnGroup)column).getColumns();
		}
		return null;
	}



  @Override
  protected String getDesc(Column column, ViewConfig viewConfig) {
    String desc = super.getDesc(column, viewConfig);
    if (desc != null) {
      return desc;
    }
    if (column.getId() != null) {
      return column.getId();
    }
    if (column.getCaption() != null) {
      return column.getCaption();
    }

    if (column instanceof DataColumn) {
      DataColumn dataColumn = (DataColumn) column;
      View view = viewConfig.getView();
      ViewElement parentViewElement = column.getParent();
      if (parentViewElement instanceof DataGrid) {
        DataGrid dataGrid = (DataGrid) parentViewElement;
        EntityDataType entityDataType = dataGrid.getDataType();
        if (entityDataType == null) {
          entityDataType = DoradoUtil.getEntityDataType(view, dataGrid);
        }

        if (entityDataType != null) {
          desc = DoradoUtil.getLabel(entityDataType, dataColumn.getProperty());
        }

        System.out.println(entityDataType);
        System.out.println(desc);
      }
    }
    return desc;
  }

}

