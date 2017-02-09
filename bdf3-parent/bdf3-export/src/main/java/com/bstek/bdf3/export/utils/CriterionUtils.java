package com.bstek.bdf3.export.utils;

import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import com.bstek.bdf3.export.ViewManager;
import com.bstek.dorado.data.JsonUtils;
import com.bstek.dorado.data.provider.And;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Criterion;
import com.bstek.dorado.data.provider.Junction;
import com.bstek.dorado.data.provider.Or;
import com.bstek.dorado.data.provider.Order;
import com.bstek.dorado.data.type.DataType;

public class CriterionUtils {

	private static Criterion parseCriterion(ObjectNode rudeCriterion) throws Exception {
		String junction = JsonUtils.getString(rudeCriterion, "junction");
		if (StringUtils.isNotEmpty(junction)) {
			Junction junctionCrition;
			if ("or".equals(junction)) {
				junctionCrition = new Or();
			} else {
				junctionCrition = new And();
			}
			ArrayNode criterions = (ArrayNode) rudeCriterion.get("criterions");
			if (criterions != null) {
				for (Iterator<JsonNode> it = criterions.iterator(); it.hasNext();) {
					junctionCrition.addCriterion(parseCriterion((ObjectNode) it.next()));
				}
			}
			return junctionCrition;
		} else {
			String property = JsonUtils.getString(rudeCriterion, "property");
			String expression = JsonUtils.getString(rudeCriterion, "expression");
			String dataTypeName = JsonUtils.getString(rudeCriterion, "dataType");
			DataType dataType = null;
			ViewManager viewManager = ViewManager.getInstance();
			if (StringUtils.isNotEmpty(dataTypeName)) {
				dataType = viewManager.getDataType(dataTypeName);
			}
			return viewManager.getFilterCriterionParser().createFilterCriterion(property, dataType, expression);
		}
	}

	public static Criteria getCriteria(ObjectNode rudeCriteria) throws Exception {
		Criteria criteria = new Criteria();
		if (rudeCriteria.has("criterions")) {
			ArrayNode criterions = (ArrayNode) rudeCriteria.get("criterions");
			if (criterions != null) {
				for (Iterator<JsonNode> it = criterions.iterator(); it.hasNext();) {
					criteria.addCriterion(parseCriterion((ObjectNode) it.next()));
				}
			}
		}

		if (rudeCriteria.has("orders")) {
			ArrayNode orders = (ArrayNode) rudeCriteria.get("orders");
			if (orders != null) {
				for (Iterator<JsonNode> it = orders.iterator(); it.hasNext();) {
					ObjectNode rudeCriterion = (ObjectNode) it.next();
					Order order = new Order(JsonUtils.getString(rudeCriterion, "property"), JsonUtils.getBoolean(rudeCriterion, "desc"));
					criteria.addOrder(order);
				}
			}
		}
		return criteria;
	}

}
