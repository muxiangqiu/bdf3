package com.bstek.bdf3.security.ui.builder;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.bstek.dorado.view.widget.grid.Column;
import com.bstek.dorado.view.widget.grid.GridSupport;

@Component("maintain.gridBuilder")
public class GridBuilder  extends AbstractBuilder<GridSupport> {
	
	
	protected Collection<Column> getChildren(GridSupport grid){
		return grid.getColumns();
	}

	
}
