package com.bstek.bdf3.security.ui.builder;

import java.util.Collection;

import com.bstek.dorado.view.widget.Component;
import com.bstek.dorado.view.widget.Container;
import com.bstek.dorado.view.widget.base.Panel;

@org.springframework.stereotype.Component("maintain.containerBuilder")
public class ContainerBuilder extends AbstractBuilder<Container> {
		
	protected Collection<Component> getChildren(Container container){
		return container.getChildren();
	}

	public boolean support(Object control) {
		return (control instanceof Container) && !(control instanceof Panel);
	}

}

