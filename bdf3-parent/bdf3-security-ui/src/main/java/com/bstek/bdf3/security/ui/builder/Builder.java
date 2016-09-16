package com.bstek.bdf3.security.ui.builder;

import com.bstek.dorado.view.AbstractViewElement;



public interface Builder<T extends AbstractViewElement>{
	
	void build(T control, ViewComponent parent);
	
	boolean support(Object control);
}
