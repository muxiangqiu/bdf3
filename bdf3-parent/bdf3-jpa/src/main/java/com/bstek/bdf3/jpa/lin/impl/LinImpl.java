package com.bstek.bdf3.jpa.lin.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CommonAbstractCriteria;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.persistence.criteria.Subquery;

import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.bstek.bdf3.jpa.And;
import com.bstek.bdf3.jpa.JpaUtil;
import com.bstek.bdf3.jpa.Junction;
import com.bstek.bdf3.jpa.Or;
import com.bstek.bdf3.jpa.lin.Lin;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年1月31日
 */
@SuppressWarnings("unchecked")
public abstract class LinImpl<T extends Lin<T, Q>, Q extends CommonAbstractCriteria> implements Lin<T, Q> {
	
	protected Class<?> domainClass;
	protected EntityManager em;
	protected CriteriaBuilder cb;
	protected Q criteria;
	protected Subquery<?> sq;
	protected Root<?> root;
	protected Stack<Junction> junctions = new Stack<Junction>();
	protected Junction junction;
	protected T parent;
	protected Junction having;
	protected List<String> aliases = new ArrayList<String>();
	private Stack<Boolean> ifResult = new Stack<Boolean>();

	
	public LinImpl(Class<?> domainClass) {
		this(domainClass, null);
	}
	
	public LinImpl(Class<?> domainClass, EntityManager entityManager) {
		this.domainClass = domainClass;
		em = entityManager;
		if (entityManager == null) {
			em = JpaUtil.getEntityManager(domainClass);
		}
		cb = em.getCriteriaBuilder();
		junction = new And();
		junctions.add(junction);
	}
	
	public LinImpl(T parent, Class<?> domainClass) {
		this.domainClass = domainClass;
		this.parent = parent;
		em = parent.entityManager();
		cb = parent.criteriaBuilder();
		criteria = parent.criteria();
		sq = criteria().subquery(domainClass);
		root = sq.from(domainClass);
		junction = new And();
		junctions.add(junction);
	}
	
	
	
	@Override
	public T addIf(Object target) {
		boolean result;
		if (target instanceof Boolean) {
			result = (Boolean) target;
		} else if (target instanceof Collection) {
			result = !CollectionUtils.isEmpty((Collection<?>) target);
		} else {
			result = !StringUtils.isEmpty(target);
		}
		ifResult.push(result);
		return (T) this;
	}

	@Override
	public T addIfNot(Object target) {
		boolean result;
		if (target instanceof Boolean) {
			result = !(Boolean) target;
		} else if (target instanceof Collection) {
			result = CollectionUtils.isEmpty((Collection<?>) target);
		} else {
			result = StringUtils.isEmpty(target);
		}
		ifResult.push(result);
		return (T) this;
	}

	@Override
	public T endIf() {
		ifResult.pop();
		return (T) this;
	}
	
	protected boolean beforeMethodInvoke() {
		for (Boolean r : ifResult) {
			if (!r) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public T selectId() {
		return select(JpaUtil.getIdName(domainClass));
	}

	@Override
	public T select(String... selections) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		List<Selection<?>> list = new ArrayList<Selection<?>>(selections.length);
		for (String selection : selections) {
			String[] ps = selection.split("\\s*,\\s*");
			for (String p : ps) {
				String alias = p.trim();
				String[] pa = alias.split("\\s+[aA][sS]\\s+");
				if (pa.length > 1) {
					alias = pa[1];
				} else {
					pa = alias.split("\\s+");
					if (pa.length > 1) {
						alias = pa[1];
					}
				}
				list.add(root.get(ps[0]).alias(alias));
			}
		}
		select(list.toArray(new Selection<?>[list.size()]));
		return (T) this;
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public T select(Selection<?>... selections) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		Assert.isTrue(sq == null || selections.length == 1, "selections can only have one in subquery! ");
		Assert.isTrue(sq == null || selections[0] instanceof Expression, "Elements in the selections must implement the " + Expression.class.getName() + " interface in subquery! ");
		Assert.isTrue(sq != null || criteria instanceof CriteriaQuery, "Not supported!");
		if (sq == null) {
			((CriteriaQuery) criteria).multiselect(selections);
		} else {
			sq.select((Expression) selections[0]);
		}
		for (Selection<?> selection : selections) {
			aliases.add(selection.getAlias());
		}
		
		return (T) this;
	}
				
	@Override
	public T and() {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		And and = new And();
		add(and);
		junctions.push(and);
		return (T) this;
	}
	
	@Override
	public T or() {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		Or or = new Or();
		add(or);
		junctions.push(or);
		return (T) this;
	}
	
	@Override
	public <Y extends Comparable<? super Y>> T between(Expression<Y> v, String x, String y) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		Expression<Y> xe = root.get(x);
		Expression<Y> ye = root.get(y);
		add(cb.between(v, xe, ye));
		return (T) this;
	}
	
	@Override
	public <Y extends Comparable<? super Y>> T between(String v, Y x, Y y) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		Expression<Y> ve = root.get(v);
		add(cb.between(ve, x, y));
		return (T) this;
	}
	
	@Override
	public <Y extends Comparable<? super Y>> T between(Expression<? extends Y> v, Expression<? extends Y> x, Expression<? extends Y> y) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.between(v, x, y));
		return (T) this;
	}
	
	@Override
	public <Y extends Comparable<? super Y>> T between(Expression<? extends Y> v, Y x, Y y) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.between(v, x, y));
		return (T) this;
	}
	
	@Override
	public T idEqual(Object id) {
		return equal(JpaUtil.getIdName(domainClass), id);
	}
	
	@Override
	public T equal(String x, Object y) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.equal(root.get(x), y));
		return (T) this;
	}
	
	@Override
	public T equal(Expression<?> x, Object y) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.equal(x, y));
		return (T) this;
	}
	
	@Override
	public T equal(Expression<?> x, Expression<?> y) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.equal(x, y));
		return (T) this;
	}
	
	@Override
	public T equalProperty(String property, String otherProperty) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		if (sq != null) {
			equal(root.get(property), parent.root().get(otherProperty));
		} else {
			equal(root.get(property), root.get(otherProperty));
		}
		return (T) this;
	}
	
	@Override
	public T exists(Class<?> domainClass) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		T lin = createChild(domainClass);
		lin.select(lin.root());
		add(cb.exists(lin.getSubquery()));
		return lin;
	}
	
	@Override
	public T notExists(Class<?> domainClass) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		T lin = createChild(domainClass);
		lin.select(lin.root());
		add(cb.not(cb.exists(lin.getSubquery())));
		return lin;
	}
	
	@Override
	public T ge(String x, Number y) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.ge(root.get(x).as(Number.class), y));
		return (T) this;
	}
	
	@Override
	public T ge(Expression<? extends Number> x, Number y) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.ge(x, y));
		return (T) this;
	}
	
	@Override
	public T ge(Expression<? extends Number> x, Expression<? extends Number> y) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.ge(x, y));
		return (T) this;
	}
	
	@Override
	public T ge(String property, String otherProperty) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		if (sq != null) {
			Expression<? extends Number> x = root.get(property);
			Expression<? extends Number> y = parent.root().get(otherProperty);
			ge(x, y);
		} else {
			Expression<? extends Number> x = root.get(property);
			Expression<? extends Number> y = root.get(otherProperty);
			ge(x, y);
		}
		return (T) this;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public T greaterThanProperty(String property, String otherProperty) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		if (sq != null) {
			Expression x = root.get(property);
			Expression y = parent.root().get(otherProperty);
			greaterThan(x, y);
		} else {
			Expression x = root.get(property);
			Expression y = root.get(otherProperty);
			greaterThan(x, y);
		}
		return (T) this;
	}
	
	@Override
	public <Y extends Comparable<? super Y>> T greaterThan(String x, Y y) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		Expression<Y> xe = root.get(x);
		add(cb.greaterThan(xe, y));
		return (T) this;
	}
		
	@Override
	public <Y extends Comparable<? super Y>> T greaterThan(Expression<? extends Y> x, Y y) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.greaterThan(x, y));
		return (T) this;
	}
	
	@Override
	public <Y extends Comparable<? super Y>> T greaterThan(Expression<? extends Y> x, Expression<? extends Y> y) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.greaterThan(x, y));
		return (T) this;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public T greaterThanOrEqualToProperty(String property, String otherProperty) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		if (sq != null) {
			Expression x = root.get(property);
			Expression y = parent.root().get(otherProperty);
			greaterThanOrEqualTo(x, y);
		} else {
			Expression x = root.get(property);
			Expression y = root.get(otherProperty);
			greaterThanOrEqualTo(x, y);
		}
		return (T) this;
	}
	
	@Override
	public <Y extends Comparable<? super Y>> T greaterThanOrEqualTo(String x, Y y) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		Expression<Y> xe = root.get(x);
		add(cb.greaterThanOrEqualTo(xe, y));
		return (T) this;
	}
	
	@Override
	public <Y extends Comparable<? super Y>> T greaterThanOrEqualTo(Expression<? extends Y> x, Y y) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.greaterThanOrEqualTo(x, y));
		return (T) this;
	}
	
	@Override
	public <Y extends Comparable<? super Y>> T greaterThanOrEqualTo(Expression<? extends Y> x, Expression<? extends Y> y) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.greaterThanOrEqualTo(x, y));
		return (T) this;
	}
	
	@Override
	public <Y extends Number> T gt(String x, Y y) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		Expression<Y> xe = root.get(x);
		add(cb.gt(xe, y));
		return (T) this;
	}
		
	@Override
	public <Y extends Number> T gt(Expression<? extends Y> x, Y y) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.gt(x, y));
		return (T) this;
	}
	
	@Override
	public <Y extends Number> T gt(Expression<? extends Y> x, Expression<? extends Y> y) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.gt(x, y));
		return (T) this;
	}
	
	@Override
	public T gt(String property, String otherProperty) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		if (sq != null) {
			Expression<? extends Number> x = root.get(property);
			Expression<? extends Number> y = parent.root().get(otherProperty);
			gt(x, y);
		} else {
			Expression<? extends Number> x = root.get(property);
			Expression<? extends Number> y = root.get(otherProperty);
			gt(x, y);
		}
		return (T) this;
	}
	
	@Override
	public T in(String property, Class<?> domainClass) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		Expression<?> e = root.get(property);
		T lin = createChild(domainClass);
		add(cb.in(e).value(lin.getSubquery()));
		return lin;
	}
	
	@Override
	public T in(Class<?> domainClass) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		Expression<?> e = root.get(JpaUtil.getIdName(this.domainClass));
		T lin = createChild(domainClass);
		add(cb.in(e).value(lin.getSubquery()));
		return lin;
	}
	
	@Override
	public T in(String property, Set<?> values) {
		return in(property, values.toArray());
	}
	
	@Override
	public T in(String property, Object... values) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		Expression<?> e = root.get(property);
		add(e.in(values));
		return (T) this;
	}
	
	@Override
	public T in(Expression<?> expression, Object... values) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(expression.in(values));
		return (T) this;
	}
	
	@Override
	public T in(String property, Expression<Collection<?>> values) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		Expression<?> e = root.get(property);
		add(e.in(values));
		return (T) this;
	}
	
	@Override
	public T in(Expression<?> expression, Expression<Collection<?>> values) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(expression.in(values));
		return (T) this;
	}
	
	@Override
	public T in(String property, Expression<?> ...values) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		Expression<?> e = root.get(property);
		add(e.in(values));
		return (T) this;
	}
	
	@Override
	public <E> T in(Expression<E> expression, Expression<?> ...values) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(expression.in(values));
		return (T) this;
	}
	
	@Override
	public T notIn(String property, Set<?> values) {
		return notIn(property, values.toArray());
	}
	
	@Override
	public T notIn(String property, Object... values) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		Expression<?> e = root.get(property);
		add(cb.not(e.in(values)));
		return (T) this;
	}
	
	@Override
	public T notIn(String property, Expression<Collection<?>> values) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		Expression<?> e = root.get(property);
		add(cb.not(e.in(values)));
		return (T) this;
	}
	
	@Override
	public <E> T notIn(Expression<E> expression, Expression<Collection<?>> values) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.not(expression.in(values)));
		return (T) this;
	}
	
	@Override
	public T notIn(Expression<?> expression, Object... values) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.not(expression.in(values)));
		return (T) this;
	}
	
	@Override
	public T notIn(String property, Expression<?> ...values) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		Expression<?> e = root.get(property);
		add(cb.not(e.in(values)));
		return (T) this;
	}
	
	@Override
	public <E> T notIn(Expression<E> expression, Expression<?> ...values) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.not(expression.in(values)));
		return (T) this;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public T isEmpty(String property) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		Expression e = root.get(property);
		add(cb.isEmpty(e));
		return (T) this;
	}
	
	@Override
	public <C extends Collection<?>> T isEmpty(Expression<C> collection) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.isEmpty(collection));
		return (T) this;
	}
	
	@Override
	public T isFalse(String property) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		Expression<Boolean> e = root.get(property);
		add(cb.isFalse(e));
		return (T) this;
	}
	
	@Override
	public T isFalse(Expression<Boolean> expression) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.isFalse(expression));
		return (T) this;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public T isMember(String elem, String collection) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		Expression elemE = root.get(elem);
		Expression collectionE = root.get(collection);
		isMember(elemE, collectionE);
		return (T) this;
	}
	
	@Override
	public <E, C extends Collection<E>> T isMember(Expression<E> elem, Expression<C> collection) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.isMember(elem, collection));
		return (T) this;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public T isNotEmpty(String collection) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		Expression collectionE = root.get(collection);
		add(cb.isNotEmpty(collectionE));
		return (T) this;
	}
	
	@Override
	public <C extends Collection<?>> T isNotEmpty(Expression<C> collection) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.isNotEmpty(collection));
		return (T) this;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public T isNotMember(String elem, String collection) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		Expression elemE = root.get(elem);
		Expression collectionE = root.get(collection);
		isNotMember(elemE, collectionE);
		return (T) this;
	}
	
	@Override
	public <E, C extends Collection<E>> T isNotMember(Expression<E> elem, Expression<C> collection) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.isNotMember(elem, collection));
		return (T) this;
	}
	
	@Override
	public T isNull(String property) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.isNull(root.get(property)));
		return (T) this;
	}
	
	@Override
	public T isNull(Expression<?> expression) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.isNull(expression));
		return (T) this;
	}
	
	@Override
	public T isNotNull(String property) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.isNotNull(root.get(property)));
		return (T) this;
	}
		
	@Override
	public T isNotNull(Expression<?> expression) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.isNotNull(expression));
		return (T) this;
	}
	
	@Override
	public T isTrue(String property) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		Expression<Boolean> e = root.get(property);
		add(cb.isTrue(e));
		return (T) this;
	}
	
	@Override
	public T isTrue(Expression<Boolean> expression) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.isTrue(expression));
		return (T) this;
	}

	@Override
	public <Y extends Number> T le(String x, Y y) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		Expression<Y> xe = root.get(x);
		add(cb.le(xe, y));
		return (T) this;
	}
	
	@Override
	public T le(String property, String otherProperty) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		if (sq != null) {
			Expression<? extends Number> x = root.get(property);
			Expression<? extends Number> y = parent.root().get(otherProperty);
			le(x, y);
		} else {
			Expression<? extends Number> x = root.get(property);
			Expression<? extends Number> y = root.get(otherProperty);
			le(x, y);
		}
		return (T) this;
	}
	
	@Override
	public <Y extends Number> T le(Expression<? extends Y> x, Y y) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.le(x, y));
		return (T) this;
	}
	
	@Override
	public <Y extends Number> T le(Expression<? extends Y> x, Expression<? extends Y> y) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.le(x, y));
		return (T) this;
	}	
	
	@Override
	public <Y extends Number> T lt(String x, Y y) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		Expression<Y> xe = root.get(x);
		add(cb.lt(xe, y));
		return (T) this;
	}
	
	@Override
	public T lt(String property, String otherProperty) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		if (sq != null) {
			Expression<? extends Number> x = root.get(property);
			Expression<? extends Number> y = parent.root().get(otherProperty);
			lt(x, y);
		} else {
			Expression<? extends Number> x = root.get(property);
			Expression<? extends Number> y = root.get(otherProperty);
			lt(x, y);
		}
		return (T) this;
	}
	
	@Override
	public <Y extends Number> T lt(Expression<? extends Y> x, Y y) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.lt(x, y));
		return (T) this;
	}
	
	@Override
	public <Y extends Number> T lt(Expression<? extends Y> x, Expression<? extends Y> y) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.lt(x, y));
		return (T) this;
	}
	
	@Override
	public <Y extends Comparable<? super Y>> T lessThan(String x, Y y) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		Expression<Y> xe = root.get(x);
		add(cb.lessThan(xe, y));
		return (T) this;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public T lessThanProperty(String property, String otherProperty) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		if (sq != null) {
			Expression x = root.get(property);
			Expression y = parent.root().get(otherProperty);
			lessThan(x, y);
		} else {
			Expression x = root.get(property);
			Expression y = root.get(otherProperty);
			lessThan(x, y);
		}
		return (T) this;
	}
	
	@Override
	public <Y extends Comparable<? super Y>> T lessThan(Expression<? extends Y> x, Y y) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.lessThan(x, y));
		return (T) this;
	}
	
	@Override
	public <Y extends Comparable<? super Y>> T lessThan(Expression<? extends Y> x, Expression<? extends Y> y) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.lessThan(x, y));
		return (T) this;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public T lessThanOrEqualToProperty(String property, String otherProperty) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		if (sq != null) {
			Expression x = root.get(property);
			Expression y = parent.root().get(otherProperty);
			lessThanOrEqualTo(x, y);
		} else {
			Expression x = root.get(property);
			Expression y = root.get(otherProperty);
			lessThanOrEqualTo(x, y);
		}
		return (T) this;
	}
	
	@Override
	public <Y extends Comparable<? super Y>> T lessThanOrEqualTo(String x, Y y) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		Expression<Y> xe = root.get(x);
		add(cb.lessThanOrEqualTo(xe, y));
		return (T) this;
	}
	
	@Override
	public <Y extends Comparable<? super Y>> T lessThanOrEqualTo(Expression<? extends Y> x, Y y) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.lessThanOrEqualTo(x, y));
		return (T) this;
	}
	
	@Override
	public <Y extends Comparable<? super Y>> T lessThanOrEqualTo(Expression<? extends Y> x, Expression<? extends Y> y) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.lessThanOrEqualTo(x, y));
		return (T) this;
	}
	
	@Override
	public T like(Expression<String> x, String pattern) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.like(x, pattern));
		return (T) this;
	}
	
	@Override
	public T like(Expression<String> x, Expression<String> pattern) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.like(x, pattern));
		return (T) this;
	}
	
	@Override
	public T like(String x,String pattern) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		Expression<String> xe = root.get(x);
		add(cb.like(xe, pattern));
		return (T) this;
	}
	
	@Override
	public T like(Expression<String> x, Expression<String> pattern, char escapeChar) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.like(x, pattern, escapeChar));
		return (T) this;
	}
	
	@Override
	public T like(Expression<String> x, Expression<String> pattern, Expression<Character> escapeChar) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.like(x, pattern, escapeChar));
		return (T) this;
	}
	
	@Override
	public T like(Expression<String> x, String pattern, char escapeChar) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.like(x, pattern, escapeChar));
		return (T) this;
	}
	
	@Override
	public T like(Expression<String> x, String pattern, Expression<Character> escapeChar) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.like(x, pattern, escapeChar));
		return (T) this;
	}
	
	@Override
	public T notLike(Expression<String> x, String pattern) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.notLike(x, pattern));
		return (T) this;
	}
	
	@Override
	public T notLike(Expression<String> x, Expression<String> pattern) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.notLike(x, pattern));
		return (T) this;
	}
	
	@Override
	public T notLike(String x, String pattern) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		Expression<String> xe = root.get(x);
		add(cb.notLike(xe, pattern));
		return (T) this;
	}
	
	@Override
	public T notLike(Expression<String> x, Expression<String> pattern, char escapeChar) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.notLike(x, pattern, escapeChar));
		return (T) this;
	}
	
	@Override
	public T notLike(Expression<String> x, Expression<String> pattern, Expression<Character> escapeChar) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.notLike(x, pattern, escapeChar));
		return (T) this;
	}
	
	@Override
	public T notLike(Expression<String> x, String pattern, char escapeChar) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.notLike(x, pattern, escapeChar));
		return (T) this;
	}
	
	@Override
	public T notLike(Expression<String> x, String pattern, Expression<Character> escapeChar) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.notLike(x, pattern, escapeChar));
		return (T) this;
	}
	
	@Override
	public T not(Expression<Boolean> restriction) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.not(restriction));
		return (T) this;
	}
	
	@Override
	public T notEqual(String x, Object y) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.notEqual(root.get(x), y));
		return (T) this;
	}
	
	@Override
	public T notEqual(Expression<?> x, Object y) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.notEqual(x, y));
		return (T) this;
	}
	
	@Override
	public T notEqualProperty(String property, String otherProperty) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		if (sq != null) {
			Expression<?> x = root.get(property);
			Expression<?> y = parent.root().get(otherProperty);
			notEqual(x, y);
		} else {
			Expression<?> x = root.get(property);
			Expression<?> y = root.get(otherProperty);
			notEqual(x, y);
		}
		return (T) this;
	}
	
	@Override
	public T notEqual(Expression<?> x, Expression<?> y) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		add(cb.notEqual(x, y));
		return (T) this;
	}
	
	@Override
	public T end() {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		if (sq != null) {
			if (junctions.size() == 1) {
				Predicate predicate = parsePredicate(junction);
				if (predicate != null) {
					sq.where(predicate);
				}
				return parent;
			}
		} 
		if (!junctions.isEmpty()) {
			junctions.pop();
		}
		return (T) this;
	}
	
	@Override
	public T groupBy(String... grouping) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		List<Expression<?>> expressions = new ArrayList<Expression<?>>();
		for (String property : grouping) {
			expressions.add(root.get(property));
		}
		if (sq != null) {
			sq.groupBy(expressions);
		} else {
			throw new RuntimeException("Not Supported!");
		}
		return (T) this;
	}
	
	@Override
	public T having() {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		having = new And();
		junctions.push(having);
		return (T) this;
	}
		
	protected Predicate parsePredicate(Object predicate) {
		if (predicate instanceof Predicate) {
			return (Predicate) predicate;
		} else if (predicate instanceof Junction) {
			Junction junction = (Junction) predicate;
			if (!CollectionUtils.isEmpty(junction.getPredicates())) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				for (Object p : junction.getPredicates()) {
					Predicate result = parsePredicate(p);
					if (result != null) {
						predicates.add(result);
					}
				}
				if (!CollectionUtils.isEmpty(predicates)) {
					if (junction instanceof And) {
						return cb.and(predicates.toArray(new Predicate[predicates.size()]));
					} else if (junction instanceof Or) {
						return cb.or(predicates.toArray(new Predicate[predicates.size()]));
					}
				}
			}
		}
		return null;
	}

	@Override
	public T add(Object predicate) {
		if (!beforeMethodInvoke()) {
			return (T) this;
		}
		this.getCurrent().add(predicate);
		return (T) this;
	}
	
	protected Junction getCurrent() {
		return this.junctions.peek();
	}
	
	@Override
	public Q criteria() {
		return criteria;
	}
	
	@Override
	public CriteriaBuilder criteriaBuilder() {
		return cb;
	}
	
	@Override
	public EntityManager entityManager() {
		return em;
	}
	
	@Override
	public <E> Subquery<E> getSubquery() {
		return (Subquery<E>) sq;
	}

	@Override
	public Class<?> domainClass() {
		return domainClass;
	}
	
	@Override
	public <E> Root<E> root() {
		return (Root<E>) root;
	}


}
