package com.bstek.bdf3.jpa.lin;

import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.metamodel.SingularAttribute;

/**
 * 语言集成更新
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * since 2016年2月16日
 */
public interface Linu extends Lin<Linu, CriteriaUpdate<?>>{

	/**
	 * 批量更新
	 * @return 更新记录数
	 */
	int update();

	/**
	 * 设置更新值
	 * @param attribute 属性
	 * @param value 值
	 * @param <Y> 范型
	 * @return 本身
	 */
	<Y> Linu set(Path<Y> attribute, Expression<? extends Y> value);

	/**
	 * 设置更新值
	 * @param attributeName 属性名称
	 * @param value 值
	 * @return 本身
	 */
	Linu set(String attributeName, Object value);
	
	/**
	 * 设置更新值
	 * @param attribute 属性
	 * @param value 值
	 * @param <Y> 路径范型
	 * @param <X> 值范型
	 * @return 本身
	 */
	<Y, X extends Y> Linu set(Path<Y> attribute, X value);
	
	/**
	 * 设置更新值
	 * @param attribute 属性
	 * @param value 值
	 * @param <Y> 属性范型
	 * @param <X> 值范型
	 * @return 本身
	 */
	<Y, X extends Y> Linu set(SingularAttribute<? super Object, Y> attribute, X value);

	/**
	 * 设置更新值
	 * @param attribute 属性
	 * @param value 值
	 * @param <Y> 属性范型
	 * @return 本身
	 */
	<Y> Linu set(SingularAttribute<? super Object, Y> attribute, Expression<? extends Y> value);

}
