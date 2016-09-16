package com.bstek.bdf3.security.ui.builder;

import com.bstek.dorado.view.widget.Control;

/**
 * @author Kevin.yang
 */
@org.springframework.stereotype.Component
public class DefaultBuilder implements Builder<Control>{
			
	@Override
	public void build(Control control, ViewComponent parent) {
		ViewComponent component = new ViewComponent();
		component.setId(control.getId());
		component.setIcon(getIcon(control));
		component.setEnabled(true);
		component.setName(getName(control));
		parent.addChildren(component);
	}
		
	protected String getIcon(Control control){
		return ">dorado/res/"+control.getClass().getName().replaceAll("\\.", "/")+".png";
	}
	
	protected String getName(Control control){
		return control.getClass().getSimpleName();
	}
	
	@Override
	public boolean support(Object control) {
		return false;
	}

}
