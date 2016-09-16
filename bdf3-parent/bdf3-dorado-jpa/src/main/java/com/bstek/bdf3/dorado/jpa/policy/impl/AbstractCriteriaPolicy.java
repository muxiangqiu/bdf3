package com.bstek.bdf3.dorado.jpa.policy.impl;

import java.util.List;

import com.bstek.bdf3.dorado.jpa.policy.CriteriaContext;
import com.bstek.bdf3.dorado.jpa.policy.CriteriaPolicy;
import com.bstek.dorado.data.provider.And;
import com.bstek.dorado.data.provider.Criterion;
import com.bstek.dorado.data.provider.Or;
import com.bstek.dorado.data.provider.Order;
import com.bstek.dorado.data.provider.filter.SingleValueFilterCriterion;


/**
 *@author Kevin.yang
 *@since 2015年5月23日
 */
public abstract class AbstractCriteriaPolicy implements CriteriaPolicy {

	protected void parseOrders(CriteriaContext context) {
		List<Order> orders = context.getCriteria().getOrders();
		for (Order order : orders) {
			context.setCurrent(order);
			parseOrder(context);
		}
	}
	
	abstract protected void parseOrder(CriteriaContext context);
	
	protected void parseCriterions(CriteriaContext context) {
		List<Criterion> criterions = context.getCurrent();
		if (criterions != null) {
			for (Criterion criterion : criterions) {
				context.setCurrent(criterion);
				parseCriterion(context);
			}
		}
	}
	
	protected void parseCriterion(CriteriaContext context) {
		Criterion criterion = context.getCurrent();
		if (criterion instanceof SingleValueFilterCriterion) {
			parseSingleValueFilterCriterion(context);
		} else if (criterion instanceof Or) {
			parseOrCriterion(context);
		} else if (criterion instanceof And) {
			parseAndCriterion(context);
		}
	}
	
	abstract protected void parseAndCriterion(CriteriaContext context);

	abstract protected void parseOrCriterion(CriteriaContext context);

	abstract protected void parseSingleValueFilterCriterion(CriteriaContext context);

}
