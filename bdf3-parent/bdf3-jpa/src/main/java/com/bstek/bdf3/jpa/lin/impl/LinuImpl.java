package com.bstek.bdf3.jpa.lin.impl;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.metamodel.SingularAttribute;

import com.bstek.bdf3.jpa.lin.Linu;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年2月20日
 */
public class LinuImpl extends LinImpl<Linu, CriteriaUpdate<?>> implements Linu {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public LinuImpl(Class<?> domainClass, EntityManager entityManager) {
		super(domainClass, entityManager);
		criteria = cb.createCriteriaUpdate(domainClass);
		root = criteria.from((Class) domainClass);
	}
	
	public LinuImpl(Class<?> domainClass) {
		this(domainClass, null);
	}
	
	public LinuImpl(Linu parent, Class<?> domainClass) {
		super(parent, domainClass);
	}

	@Override
	public Linu createChild(Class<?> domainClass) {
		return new LinuImpl(this, domainClass);
	}
	
	@Override
	public Linu set(String attributeName, Object value) {
		if (!beforeMethodInvoke()) {
			return this;
		}
		criteria.set(attributeName, value);
		return this;
	}
	
	@Override
	public <Y> Linu set(Path<Y> attribute, Expression<? extends Y> value) {
		if (!beforeMethodInvoke()) {
			return this;
		}
		criteria.set(attribute, value);
		return this;
	}

	@Override
	public <Y, X extends Y> Linu set(Path<Y> attribute, X value) {
		if (!beforeMethodInvoke()) {
			return this;
		}
		criteria.set(attribute, value);
		return this;
	}

	@Override
	public <Y, X extends Y> Linu set(SingularAttribute<? super Object, Y> attribute, X value) {
		if (!beforeMethodInvoke()) {
			return this;
		}
		criteria.set(attribute, value);;
		return this;
	}

	@Override
	public <Y> Linu set(SingularAttribute<? super Object, Y> attribute, Expression<? extends Y> value) {
		if (!beforeMethodInvoke()) {
			return this;
		}
		criteria.set(attribute, value);
		return this;
	}
	
	@Override
	public int update() {
		if (parent != null) {
			applyPredicateToCriteria();
			return parent.update();
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
