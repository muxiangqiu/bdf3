package com.bstek.bdf3.security.ui.builder;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.bstek.dorado.view.widget.Control;
import com.bstek.dorado.view.widget.form.autoform.AutoForm;
/**
 * @author Kevin.yang
 */
@Component("maintain.autoFormBuilder")
public class AutoFormBuilder extends AbstractBuilder<AutoForm> {
	@Override
	protected Collection<Control> getChildren(AutoForm autoForm){
		return autoForm.getElements();
	}

}