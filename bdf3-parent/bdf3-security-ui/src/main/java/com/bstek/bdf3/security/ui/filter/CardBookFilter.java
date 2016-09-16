package com.bstek.bdf3.security.ui.filter;


import java.util.Collection;

import org.springframework.stereotype.Component;

import com.bstek.dorado.view.widget.Control;
import com.bstek.dorado.view.widget.base.CardBook;


@Component
public class CardBookFilter extends AbstractFilter<CardBook> {

	@Override
	protected Collection<Control> getChildren(CardBook cardBook){
		return cardBook.getControls();
	}
	
	@Override
	public boolean support(Object control) {
		return CardBook.class.isAssignableFrom(control.getClass());
	}
}