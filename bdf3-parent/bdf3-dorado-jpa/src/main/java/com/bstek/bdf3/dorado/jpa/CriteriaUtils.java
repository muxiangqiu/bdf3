package com.bstek.bdf3.dorado.jpa;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.bstek.bdf3.dorado.jpa.lin.Linq;
import com.bstek.dorado.data.provider.And;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Criterion;
import com.bstek.dorado.data.provider.Junction;
import com.bstek.dorado.data.provider.Or;
import com.bstek.dorado.data.provider.filter.FilterCriterion;
import com.bstek.dorado.data.provider.filter.FilterOperator;
import com.bstek.dorado.data.provider.filter.SingleValueFilterCriterion;

/**
 * 构建Dorado Criteria 对象工具类。
 * 
 *@author Kevin.yang
 *@since 2015年5月24日
 */
public class CriteriaUtils {
	
	/**
	 * 以and形式添加多个条件
	 * 
	 * @param criteria 条件载体
	 * @param map map的key为属性名称（深层次属性用点.分隔），map的value为条件值
	 * @return 条件载体
	 */
	public static Criteria add(Criteria criteria, Map<String ,?> map) {
		if (criteria == null) {
			criteria = new Criteria();
		}
		if (map != null) {
			for (Entry<String, ?> entry : map.entrySet()) {
				SingleValueFilterCriterion criterion = new SingleValueFilterCriterion();
				criterion.setProperty(entry.getKey());
				criterion.setFilterOperator(FilterOperator.eq);
				criterion.setValue(entry.getValue());
				criteria.addCriterion(criterion);
			}
		}
		return criteria;
	}
	
	/**
	 * 以and形式添加多个条件
	 * 
	 * @param junction 条件载体
	 * @param map map的key为属性名称（深层次属性用点.分隔），map的value为条件值
	 * @return 条件载体
	 */
	public static Junction add(Junction junction, Map<String ,?> map) {
		if (map != null) {
			for (Entry<String, ?> entry : map.entrySet()) {
				SingleValueFilterCriterion criterion = new SingleValueFilterCriterion();
				criterion.setProperty(entry.getKey());
				criterion.setFilterOperator(FilterOperator.eq);
				criterion.setValue(entry.getValue());
				junction.addCriterion(criterion);
			}
		}
		return junction;
	}
	
	/**
	 * 添加条件
	 * 
	 * @param criteria 条件载体
	 * @param propertyName 属性名
	 * @param filterOperator 条件操作类型
	 * @param value 条件值
	 * @return 条件载体
	 */
	public static Criteria add(Criteria criteria, String propertyName, FilterOperator filterOperator, Object value) {
		if (criteria == null) {
			criteria = new Criteria();
		}
		SingleValueFilterCriterion criterion = new SingleValueFilterCriterion();
		criterion.setProperty(propertyName);
		criterion.setFilterOperator(filterOperator);
		criterion.setValue(value);
		criteria.addCriterion(criterion);
		return criteria;
	}
	
	/**
	 * 添加条件
	 * 
	 * @param junction 条件载体
	 * @param propertyName 属性名
	 * @param filterOperator 条件操作类型
	 * @param value 条件值
	 * @return 条件载体
	 */
	public static Junction add(Junction junction, String propertyName, FilterOperator filterOperator, Object value) {
		SingleValueFilterCriterion criterion = new SingleValueFilterCriterion();
		criterion.setProperty(propertyName);
		criterion.setFilterOperator(filterOperator);
		criterion.setValue(value);
		junction.addCriterion(criterion);
		return junction;
	}
	
	public static Criteria remove(Criteria criteria, String propertyName) {
		if (criteria != null) {
			remove(criteria.getCriterions(), propertyName);
		}
		return criteria;
	}

	
	private static void remove(Collection<Criterion> criterions, String propertyName) {
		if (criterions != null) {
			Iterator<Criterion> iterator = criterions.iterator();
			while (iterator.hasNext()) {
				Criterion criterion = iterator.next();
				if (criterion instanceof Junction) {
					Junction junction = (Junction) criterion;
					remove(junction.getCriterions(), propertyName);
				} else if(criterion instanceof FilterCriterion) {
					if (propertyName.equals(((FilterCriterion) criterion).getProperty())) {
						iterator.remove();
					}
				}
				
			}
		}		
	}
	
	public static void parse(Linq linq, SingleValueFilterCriterion criterion) {
		String property = criterion.getProperty();
		if (property.contains(".")) {
			property = StringUtils.substringAfterLast(property, ".");
		}
				
		Object value = criterion.getValue();
		FilterOperator operator = criterion.getFilterOperator();
		
		if (FilterOperator.likeEnd.equals(operator)) {
			linq.like(property, "%" + (String) value);
		} else if (FilterOperator.likeStart.equals(operator)) {
			linq.like(property, (String) value + "%");
		} else if (FilterOperator.gt.equals(operator)) {
			linq.gt(property, (Number) value);
		}  else if (FilterOperator.lt.equals(operator)) {
			linq.lt(property, (Number) value);
		} else if (FilterOperator.ge.equals(operator)) {
			linq.ge(property, (Number) value);
		} else if (FilterOperator.le.equals(operator)) {
			linq.le(property, (Number) value);
		} else if (FilterOperator.eq.equals(operator)) {
			linq.equal(property, value);
		} else if (FilterOperator.ne.equals(operator)) {
			linq.notEqual(property, value);
		} else {
			linq.like(property, "%" + (String) value + "%");
		}
		
	}
	
	
	public static StringBuilder parseQL(SingleValueFilterCriterion criterion) {
		StringBuilder c = new StringBuilder();
		String p = criterion.getProperty();
		String property = p;
		Object value = criterion.getValue();
		FilterOperator operator = criterion.getFilterOperator();
		c.append(property);
		if (FilterOperator.likeEnd.equals(operator)) {
			c.append(" like ");
			criterion.setValue("%" + value);
		} else if (FilterOperator.likeStart.equals(operator)) {
			c.append(" like ");
			criterion.setValue(value + "%");
		} else if (FilterOperator.gt.equals(operator)) {
			c.append(" > ");
			criterion.setValue(value);
		}  else if (FilterOperator.lt.equals(operator)) {
			c.append(" < ");
			criterion.setValue(value);
		} else if (FilterOperator.ge.equals(operator)) {
			c.append(" >= ");
			criterion.setValue(value);
		} else if (FilterOperator.le.equals(operator)) {
			c.append(" <= ");
			criterion.setValue(value);
		} else if (FilterOperator.eq.equals(operator)) {
			c.append(" = ");
			criterion.setValue(value);
		} else if (FilterOperator.ne.equals(operator)) {
			c.append(" <> ");
			criterion.setValue(value);
		} else {
			c.append(" like ");
			criterion.setValue("%" + value + "%");
		}
		c.append(":" + property);
		return c;
	}

	/**
	 * 返回级联条件and
	 * @return And
	 */
	public static And and() {
		return new And();
	}
	
	/**
	 * 返回级联条件or
	 * @return Or
	 */
	public static Or or() {
		return new Or();
	}

}
