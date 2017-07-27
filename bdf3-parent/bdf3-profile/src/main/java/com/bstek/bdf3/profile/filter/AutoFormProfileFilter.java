package com.bstek.bdf3.profile.filter;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.bstek.bdf3.profile.domain.ComponentConfig;
import com.bstek.bdf3.profile.domain.ComponentConfigMember;
import com.bstek.dorado.view.ViewElement;
import com.bstek.dorado.view.widget.HideMode;
import com.bstek.dorado.view.widget.form.autoform.AutoForm;
import com.bstek.dorado.view.widget.form.autoform.AutoFormElement;
import com.bstek.dorado.view.widget.layout.CommonLayoutConstraint;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年7月17日
 */
@Component
@Order(200)
public class AutoFormProfileFilter implements ProfileFilter<AutoForm> {

	@Override
	public void apply(AutoForm autoForm, ComponentConfig config) {
		autoForm.setCols(config.getCols());
		if (StringUtils.isNotEmpty(config.getHideMode())
				&& config.getHideMode().equals(ComponentConfig.HIDE_MODE_VISIBILITY)) {
			autoForm.setHideMode(HideMode.visibility);
		} else {
			autoForm.setHideMode(HideMode.display);
		}
		if (config.getComponentConfigMembers() != null) {
			applyChildren(autoForm, config.getComponentConfigMembers());
		}
	}
	
	private void applyChildren(AutoForm autoForm, List<ComponentConfigMember> members) {
		Map<String, AutoFormElement> sources = autoForm.getElements()
				.stream()
				.filter(c -> c instanceof AutoFormElement)
				.map(c -> (AutoFormElement) c)
				.filter(c -> c.getName() != null)
				.collect(Collectors.toMap(AutoFormElement::getName, Function.identity()));
		autoForm.getElements().clear();
		HideMode hideMode = autoForm.getHideMode();
		for (ComponentConfigMember member : members) {
			AutoFormElement autoFormElement = sources.get(member.getControlName());
			autoForm.addElement(autoFormElement);
			if (autoFormElement != null) {
				autoFormElement.setHideMode(hideMode);
				autoFormElement = (AutoFormElement) autoFormElement;
				autoFormElement.setLabel(member.getCaption());
				Object layoutConstraint = autoFormElement.getLayoutConstraint();
				if (layoutConstraint == null && (member.getColSpan() != 1 || member.getRowSpan() != 1)) {
					CommonLayoutConstraint commonLayoutConstraint = new CommonLayoutConstraint();
					commonLayoutConstraint.put("colSpan", member.getColSpan());
					commonLayoutConstraint.put("rowSpan", member.getRowSpan());
					autoFormElement.setLayoutConstraint(commonLayoutConstraint);
				} else if (layoutConstraint instanceof CommonLayoutConstraint) {
					CommonLayoutConstraint commonLayoutConstraint = (CommonLayoutConstraint) layoutConstraint;
					commonLayoutConstraint.put("colSpan", member.getColSpan());
					commonLayoutConstraint.put("rowSpan", member.getRowSpan());
				}
				autoFormElement.setVisible(member.getVisible());
			}
		}
	}

	@Override
	public boolean support(ViewElement viewElement) {
		return viewElement instanceof AutoForm;
	}

}
