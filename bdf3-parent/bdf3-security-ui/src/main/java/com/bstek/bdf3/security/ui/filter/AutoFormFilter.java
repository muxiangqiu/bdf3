package com.bstek.bdf3.security.ui.filter;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.bstek.dorado.view.widget.Control;
import com.bstek.dorado.view.widget.form.autoform.AutoForm;
/**
 * @author Kevin.yang
 */
@Component
public class AutoFormFilter extends AbstractFilter<AutoForm> {
	
	@Override
	protected Collection<Control> getChildren(AutoForm autoForm){
		return autoForm.getElements();
	}
	
	@Override
	public boolean support(Object control) {
		return AutoForm.class.isAssignableFrom(control.getClass());
	}

}