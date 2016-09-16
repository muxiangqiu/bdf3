package com.bstek.bdf3.jpa;

import java.util.ArrayList;
import java.util.List;

/**
 * 联合条件
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年1月31日
 */
public class Junction {
	private List<Object> predicates = new ArrayList<Object>();
	public void add(Object predicate) {
		this.predicates.add(predicate);
	}
	
	public List<Object> getPredicates() {
		return this.predicates;
	}
}
