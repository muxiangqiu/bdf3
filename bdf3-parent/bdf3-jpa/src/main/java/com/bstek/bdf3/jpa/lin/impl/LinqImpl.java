package com.bstek.bdf3.jpa.lin.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.metamodel.SingularAttribute;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.bstek.bdf3.jpa.JpaUtil;
import com.bstek.bdf3.jpa.lin.Linq;
import com.bstek.bdf3.jpa.transform.ResultTransformer;
import com.bstek.bdf3.jpa.transform.impl.Transformers;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年1月31日
 */
public class LinqImpl extends LinImpl<Linq, CriteriaQuery<?>> implements Linq {
	
	protected List<Order> orders = new ArrayList<Order>();
	protected boolean distinct;
	protected Class<?> resultClass;
	protected ResultTransformer resultTransformer;

	
	public LinqImpl(Class<?> domainClass) {
		this(domainClass, (EntityManager) null);
	}
	
	public LinqImpl(Class<?> domainClass, EntityManager entityManager) {
		super(domainClass, entityManager);
		criteria = cb.createQuery(domainClass);
		root = criteria.from(domainClass);
		resultClass = domainClass;
	}
	
	public LinqImpl(Class<?> domainClass, Class<?> resultClass) {
		this(domainClass, resultClass, null);
	}
	
	@SuppressWarnings("rawtypes")
	public LinqImpl(Class<?> domainClass, Class<?> resultClass, EntityManager entityManager) {
		super(domainClass, entityManager);
		if (Tuple.class.isAssignableFrom(resultClass)) {
			criteria = cb.createTupleQuery();
			root = criteria.from(domainClass);
		} else if (Map.class.isAssignableFrom(resultClass)) {
			criteria = cb.createQuery(Object[].class);
			root = criteria.from(domainClass);
			resultTransformer = Transformers.ALIAS_TO_MAP;
			Set<?> attrs = em.getMetamodel().entity(domainClass).getDeclaredSingularAttributes();
			String[] selections = new String[attrs.size()];
			int i = 0;
			for (Object attr : attrs) {
				selections[i] = ((SingularAttribute)attr).getName();
				i++;
			}
			select(selections);
		} else {
			criteria = cb.createQuery(resultClass);
			root = criteria.from(domainClass);
		}
		this.resultClass = resultClass;
	}
	
	public LinqImpl(Linq parent, Class<?> domainClass) {
		super(parent, domainClass);
	}
	
	@Override
	public Linq distinct() {
		if (!beforeMethodInvoke()) {
			return this;
		}
		distinct = true;
		return this;
	}
	
	@Override
	public Linq groupBy(String... grouping) {
		if (!beforeMethodInvoke()) {
			return this;
		}
		List<Expression<?>> expressions = new ArrayList<Expression<?>>();
		for (String property : grouping) {
			expressions.add(root.get(property));
		}
		if (sq != null) {
			sq.groupBy(expressions);
		} else {
			criteria.groupBy(expressions);
		}
		return this;
	}
	
	@Override
	public Linq desc(String... properties) {
		if (!beforeMethodInvoke()) {
			return this;
		}
		for (String property : properties) {
			orders.add(cb.desc(root.get(property)));
		}
		return this;
	}
	
	@Override
	public Linq desc(Expression<?>... expressions) {
		if (!beforeMethodInvoke()) {
			return this;
		}
		for (Expression<?> expression : expressions) {
			orders.add(cb.desc(expression));
		}
		return this;
	}
	
	@Override
	public Linq asc(String... properties) {
		if (!beforeMethodInvoke()) {
			return this;
		}
		for (String property : properties) {
			orders.add(cb.asc(root.get(property)));
		}
		return this;
	}
	
	@Override
	public Linq asc(Expression<?>... expressions) {
		if (!beforeMethodInvoke()) {
			return this;
		}
		for (Expression<?> expression : expressions) {
			orders.add(cb.asc(expression));
		}
		return this;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> T findOne() {
		if (parent != null) {
			applyPredicateToCriteria(sq);
			return (T) parent.findOne();
		}
		applyPredicateToCriteria(criteria);
		List<T> list = transform(em.createQuery(criteria), true);
		return list.get(0);
	}

	@Override
	public <T> List<T> list() {
		if (parent != null) {
			applyPredicateToCriteria(sq);
			return parent.list();
		}
		applyPredicateToCriteria(criteria);
		return transform(em.createQuery(criteria), false);
	}
	
	@Override
	public <T> Page<T> paging(Pageable pageable) {
		if (parent != null) {
			applyPredicateToCriteria(sq);
			return parent.paging(pageable);
		}
		List<T> list;
		if (pageable == null) {
			list = list();
			return new PageImpl<T>(list);
		} else {
			Sort sort = pageable.getSort();
			orders.addAll(QueryUtils.toOrders(sort, root, cb));
			applyPredicateToCriteria(criteria);
			TypedQuery<?> query = em.createQuery(criteria);
			
			query.setFirstResult(pageable.getOffset());
			query.setMaxResults(pageable.getPageSize());

			Long total = JpaUtil.count(criteria);
			List<T> content = Collections.<T> emptyList();
			if (total > pageable.getOffset()) {
				content = transform(query, false);
			}

			return new PageImpl<T>(content, pageable, total);
		}
	}
	
	@Override
	public <T> List<T> list(Pageable pageable) {
		if (parent != null) {
			applyPredicateToCriteria(sq);
			return parent.list(pageable);
		}
		if (pageable == null) {
			return list();
		} else {
			Sort sort = pageable.getSort();
			orders.addAll(QueryUtils.toOrders(sort, root, cb));
			applyPredicateToCriteria(criteria);
			TypedQuery<?> query = em.createQuery(criteria);
			
			query.setFirstResult(pageable.getOffset());
			query.setMaxResults(pageable.getPageSize());

			return transform(query, false);
		}
	}
	
	@Override
	public <T> List<T> list(int page, int size) {
		if (parent != null) {
			applyPredicateToCriteria(sq);
			return parent.list(page, size);
		}
		applyPredicateToCriteria(criteria);
		TypedQuery<?> query = em.createQuery(criteria);
		
		query.setFirstResult(page*size);
		query.setMaxResults(size);

		return transform(query, false);
	}
	
	@Override
	public Long count() {
		if (parent != null) {
			applyPredicateToCriteria(sq);
			return parent.count();
		}
		return executeCountQuery(getCountQuery());
	}
	
	@Override
	public boolean exists() {
		if (parent != null) {
			applyPredicateToCriteria(sq);
			return parent.exists();
		}
		return count() > 0;
	}
	
	protected Long executeCountQuery(TypedQuery<Long> query) {

		Assert.notNull(query);

		List<Long> totals = query.getResultList();
		Long total = 0L;

		for (Long element : totals) {
			total += element == null ? 0 : element;
		}

		return total;
	}
	
	protected TypedQuery<Long> getCountQuery() {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = cb.createQuery(Long.class);
		criteria.getRoots().add(root);
		applyPredicateToCriteria(criteria);
		criteria.getOrderList().clear();
		if (distinct) {
			criteria.select(cb.countDistinct(root));
		} else {
			criteria.select(cb.count(root));
		}
		return em.createQuery(criteria);
	}
	
	protected void applyPredicateToCriteria(AbstractQuery<?> criteria) {

		Predicate predicate = parsePredicate(junction);
		if (predicate != null) {
			((AbstractQuery<?>) criteria).where(predicate);
		}
		
		predicate = parsePredicate(having);
		if (predicate != null) {
			((AbstractQuery<?>) criteria).having(predicate);
		}
		
		if (criteria instanceof CriteriaQuery) {
			if (!CollectionUtils.isEmpty(orders)) {
				((CriteriaQuery<?>) criteria).orderBy(orders);
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected <T> List<T> transform(TypedQuery<?> query, boolean single) {
		List<T> result;
		if (resultTransformer != null) {
			List tuples;
			if (single) {
				tuples = new ArrayList(1);
				tuples.add(query.getSingleResult());
			} else {
				tuples = query.getResultList();
			}
			result = new ArrayList<T>(tuples.size());
			String[] aliases = this.aliases.toArray(new String[this.aliases.size()]);
			for (Object tuple : tuples) {
				if (tuple != null) {
					if (tuple.getClass().isArray()) {
						result.add((T) resultTransformer.transformTuple((Object[]) tuple, aliases));
					} else {
						result.add((T) resultTransformer.transformTuple(new Object[]{ tuple }, aliases));
					}
				}
			}
		} else {
			if (single) {
				result = new ArrayList<T>(1);
				result.add((T) query.getSingleResult());
			} else {
				result = (List<T>) query.getResultList();
			}
		}
		return result;
		
	}

	@Override
	public Linq createChild(Class<?> domainClass) {
		return new LinqImpl(this, domainClass);
	}

	@Override
	public Linq aliasToBean() {
		if (!beforeMethodInvoke()) {
			return this;
		}
		criteria = cb.createQuery(Object[].class);
		root = criteria.from(domainClass);
		resultTransformer = Transformers.aliasToBean(domainClass);
		return this;
	}
	
	@Override
	public Linq aliasToBean(Class<?> resultClass) {
		if (!beforeMethodInvoke()) {
			return this;
		}
		criteria = cb.createQuery(Object[].class);
		root = criteria.from(domainClass);
		this.resultClass = resultClass;
		resultTransformer = Transformers.aliasToBean(resultClass);
		return this;
	}

	@Override
	public Linq aliasToMap() {
		if (!beforeMethodInvoke()) {
			return this;
		}
		criteria = cb.createQuery(Object[].class);
		root = criteria.from(domainClass);
		this.resultClass = Map.class;
		resultTransformer = Transformers.ALIAS_TO_MAP;
		return this;
	}

	@Override
	public Linq aliasToTuple() {
		if (!beforeMethodInvoke()) {
			return this;
		}
		criteria = cb.createTupleQuery();
		root = criteria.from(domainClass);
		resultClass = Tuple.class;
		return this;
	}


}
