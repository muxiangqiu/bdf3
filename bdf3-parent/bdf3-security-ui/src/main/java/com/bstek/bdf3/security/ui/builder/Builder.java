package com.bstek.bdf3.security.ui.builder;

import com.bstek.dorado.view.AbstractViewElement;
import com.bstek.dorado.view.manager.ViewConfig;

public interface Builder<T extends AbstractViewElement> {

	void build(T control, ViewComponent parent, ViewComponent root, ViewConfig viewConfig);

	boolean support(Object control);
}
