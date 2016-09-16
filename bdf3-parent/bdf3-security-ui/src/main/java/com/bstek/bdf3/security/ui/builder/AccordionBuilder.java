package com.bstek.bdf3.security.ui.builder;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.bstek.dorado.view.widget.base.accordion.Accordion;
import com.bstek.dorado.view.widget.base.accordion.Section;
/**
 * @author Kevin.yang
 */
@Component("maintain.accordionBuilder")
public class AccordionBuilder extends AbstractBuilder<Accordion> {

	@Override
	protected Collection<Section> getChildren(Accordion accordion){
		
		return accordion.getSections();
	}
	
	

}
