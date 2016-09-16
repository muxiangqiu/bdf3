package com.bstek.bdf3.jpa.lin;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CommonAbstractCriteria;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.persistence.criteria.Subquery;

/**
 * 语言集成抽象接口<br>
 * 抽象了语言集成查询、语言集成删除和语言集成更新的共有方法
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年2月16日
 *
 * @param <T> 具体语言集成操作类型，如语言集成查询（{@link com.bstek.bdf3.jpa.lin.Linq}）
 * @param <Q> JPA的{@link javax.persistence.criteria.CommonAbstractCriteria}的类型
 */
public interface Lin<T extends Lin<T, Q>, Q extends CommonAbstractCriteria> {
	
	/**
	 * 动态添加条件<br>
	 * 根据目标对象（target）决定后继相关操作是否有效，endIf是后继相关操作范围边界<br>
	 * 判断规则：<br>
	 * 1.当target为String时，target为""和null则后继操作无效，否则有效<br>
	 * 2.当target为Collection时，target为空集合和null则后继操作无效，否则有效<br>
	 * 3.当target为boolean时，true则后继操作有效，否则无效<br>
	 * 4.当target为null时，则后继操作无效，否则有效
	 * @param target 目标对象
	 * @return 自身
	 */
	T addIf(Object target);
	
	/**
	 * 与addIf功能类似，只是规则相反
	 * @param target 目标对象
	 * @return 自身
	 */
	T addIfNot(Object target);
	
	/**
	 * 动态添加条件结束
	 * @return 自身
	 */
	T endIf();
	
	/**
	 * 创建子查询，主要内部使用
	 * @param domainClass 实体类
	 * @return 自身
	 */
	T createChild(Class<?> domainClass);
	
	/**
	 * 添加条件
	 * @param predicate 条件
	 * @return 自身
	 */
	T add(Object predicate);

	/**
	 * 获取条件
	 * @return 自身
	 */
	Q criteria();

	/**
	 * 获取条件构造器
	 * @return 自身
	 */
	CriteriaBuilder criteriaBuilder();

	/**
	 * 获取当前查询的EntityMananger
	 * @return 自身
	 */
	EntityManager entityManager();

	/**
	 * 返回子查询对象
	 * @param <E> 实体类型
	 * @return 自身
	 */
	<E> Subquery<E> getSubquery();
	
	/**
	 * 查询结果投影设置<br>
	 * 例如：<br>
	 * ...select("id", "name", ...) 等价于 select id, name, ... from ...<br>
	 * 其中第一个id和name为实体类的属性名，第二个id和name为实体类对应表的字段名
	 * @param selections 实体属性名数组
	 * @return 自身
	 */
	T select(String... selections);
	
	/**
	 * 查询结果投影设置
	 * @param selections JPA标准{@link javax.persistence.criteria.Selection}
	 * @return 自身
	 */
	T select(Selection<?>... selections);
	
	/**
	 * 并且联合条件（支持递归定义）
	 * @return 自身
	 */
	T and();
	
	/**
	 * 或者联合条件（支持递归定义）
	 * @return 自身
	 */
	T or();

    <Y extends Comparable<? super Y>> T between(Expression<Y> v, String x, String y);

    <Y extends Comparable<? super Y>> T between(String v, Y x, Y y);

    <Y extends Comparable<? super Y>> T between(Expression<? extends Y> v, Expression<? extends Y> x,
			Expression<? extends Y> y);

    <Y extends Comparable<? super Y>> T between(Expression<? extends Y> v, Y x, Y y);

	T equal(String x, Object y);

	T equal(Expression<?> x, Object y);

	T equal(Expression<?> x, Expression<?> y);

	T exists(Class<?> domainClass);

	T ge(String x, Number y);

	T ge(Expression<? extends Number> x, Number y);

	<Y extends Comparable<? super Y>> T greaterThan(String x, Y y);

	<Y extends Comparable<? super Y>> T greaterThan(Expression<? extends Y> x, Y y);

	<Y extends Comparable<? super Y>> T greaterThan(Expression<? extends Y> x, Expression<? extends Y> y);

	<Y extends Comparable<? super Y>> T greaterThanOrEqualTo(String x, Y y);

	<Y extends Comparable<? super Y>> T greaterThanOrEqualTo(Expression<? extends Y> x, Y y);

	<Y extends Comparable<? super Y>> T greaterThanOrEqualTo(Expression<? extends Y> x,
			Expression<? extends Y> y);

	<Y extends Number> T gt(String x, Y y);

	<Y extends Number> T gt(Expression<? extends Y> x, Y y);

	<Y extends Number> T gt(Expression<? extends Y> x, Expression<? extends Y> y);

	T in(String property, Object... values);
	
	T in(String property, Expression<Collection<?>> values);
	
	T in(Expression<?> expression, Expression<Collection<?>> values);

	T in(Expression<?> expression, Object... values);
	
	T in(String property, Expression<?>... values);

	<E> T in(Expression<E> expression, Expression<?>... values);
	
	T notIn(String property, Object... values);
	
	T notIn(String property, Expression<Collection<?>> values);
	
	<E> T notIn(Expression<E> expression, Expression<Collection<?>> values);

	T notIn(Expression<?> expression, Object... values);
	
	T notIn(String property, Expression<?>... values);

	<E> T notIn(Expression<E> expression, Expression<?>... values);

	T isEmpty(String property);

	<C extends Collection<?>> T isEmpty(Expression<C> collection);

	T isFalse(String property);

	T isFalse(Expression<Boolean> expression);

	T isMember(String elem, String collection);

	<E, C extends Collection<E>> T isMember(Expression<E> elem, Expression<C> collection);

	T isNotEmpty(String collection);

	<C extends Collection<?>> T isNotEmpty(Expression<C> collection);

	T isNotMember(String elem, String collection);

	<E, C extends Collection<E>> T isNotMember(Expression<E> elem, Expression<C> collection);

	T isNull(String property);

	T isNull(Expression<?> expression);

	T isNotNull(String property);

	T isNotNull(Expression<?> expression);

	T isTrue(String property);

	T isTrue(Expression<Boolean> expression);

	<Y extends Number> T le(String x, Y y);

	T le(String property, String otherProperty);

	<Y extends Number> T le(Expression<? extends Y> x, Y y);

	<Y extends Number> T le(Expression<? extends Y> x, Expression<? extends Y> y);

	<Y extends Number> T lt(String x, Y y);

	T lt(String property, String otherProperty);

	<Y extends Number> T lt(Expression<? extends Y> x, Y y);

	<Y extends Number> T lt(Expression<? extends Y> x, Expression<? extends Y> y);

	<Y extends Comparable<? super Y>> T lessThan(String x, Y y);

	<Y extends Comparable<? super Y>> T lessThan(Expression<? extends Y> x, Y y);

	<Y extends Comparable<? super Y>> T lessThan(Expression<? extends Y> x, Expression<? extends Y> y);

	<Y extends Comparable<? super Y>> T lessThanOrEqualTo(String x, Y y);

	<Y extends Comparable<? super Y>> T lessThanOrEqualTo(Expression<? extends Y> x, Y y);

	<Y extends Comparable<? super Y>> T lessThanOrEqualTo(Expression<? extends Y> x, Expression<? extends Y> y);

	T like(Expression<String> x, String pattern);

	T like(Expression<String> x, Expression<String> pattern);

	T like(String x, String pattern);

	T like(Expression<String> x, Expression<String> pattern, char escapeChar);

	T like(Expression<String> x, Expression<String> pattern,
			Expression<Character> escapeChar);

	T like(Expression<String> x, String pattern, char escapeChar);

	T like(Expression<String> x, String pattern,
			Expression<Character> escapeChar);

	T notLike(Expression<String> x, String pattern);

	T notLike(Expression<String> x, Expression<String> pattern);

	T notLike(String x, String pattern);

	T notLike(Expression<String> x, Expression<String> pattern,
			char escapeChar);

	T notLike(Expression<String> x, Expression<String> pattern,
			Expression<Character> escapeChar);

	T notLike(Expression<String> x, String pattern, char escapeChar);

	T notLike(Expression<String> x, String pattern,
			Expression<Character> escapeChar);

	T not(Expression<Boolean> restriction);

	T notEqual(String x, Object y);

	T notEqual(Expression<?> x, Object y);

	T notEqual(Expression<?> x, Expression<?> y);

	/**
	 * 复杂条件结束标记方法<br>
	 * 复杂条件后面如果是执行数据库操作方法（findAll、delete和update等等），可以不加结束标记方法end<br>
	 * 
	 * @return 自身
	 */
	T end();
	
	T groupBy(String... grouping);

	/**
	 * 领域类（实体类）
	 * @return 领域类（实体类）
	 */
	Class<?> domainClass();

	/**
	 * JPA Root
	 * @param <E> 领域类（实体类）
	 * @return Root
	 */
	<E> Root<E> root();

	T having();

	T equalProperty(String property, String otherProperty);

	T ge(Expression<? extends Number> x, Expression<? extends Number> y);

	T ge(String property, String otherProperty);

	T gt(String property, String otherProperty);

	T lessThanProperty(String property, String otherProperty);

	T greaterThanProperty(String property, String otherProperty);

	T greaterThanOrEqualToProperty(String property, String otherProperty);

	T lessThanOrEqualToProperty(String property, String otherProperty);

	T notEqualProperty(String property, String otherProperty);

	T notExists(Class<?> domainClass);

	T in(String property, Class<?> domainClass);

	T in(Class<?> domainClass);
}
