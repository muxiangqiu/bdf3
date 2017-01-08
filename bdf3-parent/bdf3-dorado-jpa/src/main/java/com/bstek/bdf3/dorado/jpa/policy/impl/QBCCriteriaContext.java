package com.bstek.bdf3.dorado.jpa.policy.impl;

import java.util.ArrayList;
import java.util.List;

import com.bstek.bdf3.dorado.jpa.lin.Linq;
import com.bstek.bdf3.dorado.jpa.parser.CriterionParser;
import com.bstek.bdf3.dorado.jpa.policy.CriteriaContext;
import com.bstek.dorado.data.provider.Criteria;

/**
 *@author Kevin.yang
 *@since 2015年5月23日
 */
public class QBCCriteriaContext implements CriteriaContext {
	private Linq linq;
	private Object current;
	private Class<?> entityClass;
	private String alias;
	private Criteria criteria;
	private List<CriterionParser> criterionParsers = new ArrayList<CriterionParser>();
	
	
	
	
	@Override
	public Criteria getCriteria() {
		return criteria;
	}
	
	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}


	@Override
	public <E> void setCurrent(E current) {
		this.current = current;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> E getCurrent() {
		return (E) current;
	}

	public Class<?> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public List<CriterionParser> getCriterionParsers() {
		return criterionParsers;
	}

	public void setCriterionParsers(
			List<CriterionParser> criterionParsers) {
		this.criterionParsers = criterionParsers;
	}

	public Linq getLinq() {
		return linq;
	}

	public void setLinq(Linq linq) {
		this.linq = linq;
	}

	

	
	
	

}
