package com.bstek.bdf3.security.ui.builder;

import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.bstek.dorado.view.widget.Control;
import com.bstek.dorado.view.widget.base.accordion.Section;

@Component("maintain.sectionForIntroBuilder")
public class SectionBuilder extends AbstractBuilder<Section> {

	protected String getId(Section section){
		String name = section.getCaption();
		if (StringUtils.isEmpty(name)) {
			name = section.getName();
		}
		return name;
	}
	
	protected Collection<Control> getChildren(Section section){
		if(section.getControl()!=null){
			return Arrays.asList(section.getControl());
		}
		return null;
	}

	

}
