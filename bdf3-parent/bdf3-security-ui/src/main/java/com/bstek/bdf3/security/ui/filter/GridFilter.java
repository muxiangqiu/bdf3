package com.bstek.bdf3.security.ui.filter;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.bstek.dorado.view.widget.grid.Column;
import com.bstek.dorado.view.widget.grid.GridSupport;

@Component
public class GridFilter  extends AbstractFilter<GridSupport> {
	
	
	protected Collection<Column> getChildren(GridSupport grid){
		return grid.getColumns();
	}
	
	@Override
	public boolean support(Object control) {
		return GridSupport.class.isAssignableFrom(control.getClass());
	}

	
}
