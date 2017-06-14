package com.bstek.bdf3.security.ui.builder;

import com.bstek.dorado.view.AbstractViewElement;

public interface Builder<T extends AbstractViewElement> {

	void build(T control, ViewComponent parent, ViewComponent root);

	boolean support(Object control);
}
