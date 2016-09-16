package com.bstek.bdf3.jpa.lin.impl;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Predicate;

import com.bstek.bdf3.jpa.lin.Lind;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年2月20日
 */
public class LindImpl extends LinImpl<Lind, CriteriaDelete<?>> implements Lind {

	public LindImpl(Class<?> domainClass) {
		this(domainClass, null);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public LindImpl(Class<?> domainClass, EntityManager entityManager) {
		super(domainClass, entityManager);
		criteria = cb.createCriteriaDelete(domainClass);
		root = criteria.from((Class) domainClass);
	}
	
	public LindImpl(Lind parent, Class<?> domainClass) {
		super(parent, domainClass);
	}

	@Override
	public Lind createChild(Class<?> domainClass) {
		return new LindImpl(this, domainClass);
	}
	
	@Override
	public int delete() {
		if (parent != null) {
			applyPredicateToCriteria();
			return parent.delete();
		}
		applyPredicateToCriteria();
		return em.createQuery(criteria).executeUpdate();
	}
	
	protected void applyPredicateToCriteria() {
		Predicate predicate = parsePredicate(junction);
		if (predicate != null) {
			if (sq != null) {
				sq.where(predicate);
			} else {
				criteria.where(predicate);
			}
		}
	}
	
	
	

	

}
