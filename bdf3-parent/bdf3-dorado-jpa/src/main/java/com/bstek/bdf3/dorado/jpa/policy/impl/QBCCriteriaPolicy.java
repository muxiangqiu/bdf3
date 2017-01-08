package com.bstek.bdf3.dorado.jpa.policy.impl;

import java.lang.reflect.Field;
import java.util.List;

import com.bstek.bdf3.dorado.jpa.CriteriaUtils;
import com.bstek.bdf3.dorado.jpa.FieldUtils;
import com.bstek.bdf3.dorado.jpa.lin.Linq;
import com.bstek.bdf3.dorado.jpa.parser.CriterionParser;
import com.bstek.bdf3.dorado.jpa.policy.CriteriaContext;
import com.bstek.dorado.data.provider.And;
import com.bstek.dorado.data.provider.Or;
import com.bstek.dorado.data.provider.Order;
import com.bstek.dorado.data.provider.filter.SingleValueFilterCriterion;


/**
 *@author Kevin.yang
 *@since 2015年5月23日
 */
public class QBCCriteriaPolicy extends AbstractCriteriaPolicy {

	@Override
	public void apply(CriteriaContext context) {
		QBCCriteriaContext c = (QBCCriteriaContext) context;
		if(c.getCriteria() != null) {
			c.setCurrent(c.getCriteria().getCriterions());
			parseCriterions(c);
			parseOrders(c);
		}
	}

	@Override
	protected void parseOrder(CriteriaContext context) {
		QBCCriteriaContext c = (QBCCriteriaContext) context;
		Order order = c.getCurrent();
		Linq linq = c.getLinq();
		if (order.isDesc()) {
			linq.desc(order.getProperty());
		} else {
			linq.asc(order.getProperty());
		}
		
	}

	@Override
	protected void parseAndCriterion(CriteriaContext context) {
		QBCCriteriaContext c = (QBCCriteriaContext) context;
		And and = c.getCurrent();
		c.getLinq().and();
		c.setCurrent(and.getCriterions());
		parseCriterions(c);
		c.getLinq().end();
	}

	@Override
	protected void parseOrCriterion(CriteriaContext context) {
		QBCCriteriaContext c = (QBCCriteriaContext) context;
		Or or = c.getCurrent();
		c.getLinq().or();
		c.setCurrent(or.getCriterions());
		parseCriterions(c);
		c.getLinq().end();	
	}

	@Override
	protected void parseSingleValueFilterCriterion(CriteriaContext context) {
		QBCCriteriaContext criteriaContext = (QBCCriteriaContext) context;
		List<CriterionParser> criterionParsers = criteriaContext.getCriterionParsers();
		SingleValueFilterCriterion criterion = criteriaContext.getCurrent();
		String property = criterion.getProperty();
		Object value = criterion.getValue();
		Class<?> cls = criteriaContext.getEntityClass();
		boolean result = false;
		for (CriterionParser criterionParser : criterionParsers) {
			result = criterionParser.parse(criterion);
			if (result) {
				break;
			}
		}
		
		
		if (!result) {
			if (cls != null) {
				Field field = FieldUtils.getField(cls, property);
				if (Enum.class.isAssignableFrom(field.getType()) 
						&& value instanceof String) {
					Class<?> type = field.getType();
					Enum<?>[] items = (Enum<?>[]) type.getEnumConstants();
					if(items!=null){
						for(Enum<?> item:items){
							if (item.name().equals(value)) {
								criterion.setValue(item);
								break;
							}
						}
					}
				}

			}
			Linq linq = criteriaContext.getLinq();
			
			CriteriaUtils.parse(linq, criterion);
		}		
	}

}
