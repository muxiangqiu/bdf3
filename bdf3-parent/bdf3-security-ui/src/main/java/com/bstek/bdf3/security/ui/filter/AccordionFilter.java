package com.bstek.bdf3.security.ui.filter;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.bstek.dorado.view.widget.base.accordion.Accordion;
import com.bstek.dorado.view.widget.base.accordion.Section;
/**
 * @author Kevin.yang
 */
@Component
public class AccordionFilter extends AbstractFilter<Accordion> {

	@Override
	protected Collection<Section> getChildren(Accordion accordion){
		
		return accordion.getSections();
	}

	@Override
	public boolean support(Object control) {
		return Accordion.class.isAssignableFrom(control.getClass());
	}
	
	

}
