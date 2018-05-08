package com.bstek.bdf3.dorado.jpa.policy.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Id;

import com.bstek.bdf3.dorado.jpa.BeanUtils;
import com.bstek.bdf3.dorado.jpa.FieldUtils;
import com.bstek.bdf3.dorado.jpa.GenricTypeUtils;
import com.bstek.bdf3.dorado.jpa.JpaUtil;
import com.bstek.bdf3.dorado.jpa.annotation.Generator;
import com.bstek.bdf3.dorado.jpa.policy.SaveContext;
import com.bstek.bdf3.dorado.jpa.policy.SavePolicy;
import com.bstek.dorado.util.proxy.ProxyBeanUtils;

/**
 *@author Kevin.yang
 *@since 2015年5月17日
 */
public class DirtyTreeSavePolicy implements SavePolicy {
	private SavePolicy savePolicy;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void apply(SaveContext context) {
		Object obj = context.getEntity();
		List<Field> fields = null;
		List<Map<String, Object>> generatorPolicies = null;
		Collection target = null;
		if (obj != null) {
			if (obj instanceof Collection) {
				target = (Collection) obj;
			} else {
				target = new ArrayList();
				target.add(obj);
			}
			
			for (Object entity : target) {
				context.setEntity(entity);
				if(fields == null) {
					fields = getPersistentFields(context);
				}
				
				if (generatorPolicies == null) {
					generatorPolicies = getNeedGeneratorFields(entity);
				}
				applyPersistentEntity(context, generatorPolicies);
				savePolicy.apply(context);
				applyPersistentPropertyValue(context, fields);
			}
		}
	}
	
	protected void applyPersistentEntity(SaveContext context,
			List<Map<String, Object>> generatorPolicies) {
		for (Map<String, Object> map : generatorPolicies) {
			Field field = (Field) map.get("field");
			GeneratorPolicy policy = (GeneratorPolicy) map.get("policy");
			policy.apply(context.getEntity(), field);
		}
		
	}

	protected void applyPersistentPropertyValue(SaveContext context, List<Field> fields) {
		Object entity = context.getEntity();
		Object parent = context.getParent();
		context.setParent(entity);
		for (Field field : fields) {
			Object value = BeanUtils.getFieldValue(entity, field);
			context.setEntity(value);
			apply(context);
		}
		context.setParent(parent);
	}
	
	protected List<Field> getPersistentFields(SaveContext context) {
		Object entity = context.getEntity();
		List<Field> result = new ArrayList<Field>();
		List<Field> fields = FieldUtils.getFields(ProxyBeanUtils.getProxyTargetType(entity));
		if(fields != null) {
			for (Field field : fields) {
				Class<?> propertyClass = field.getType();
				if(Collection.class.isAssignableFrom(propertyClass)) {
					propertyClass = GenricTypeUtils.getGenricType(field);
				}
				if (JpaUtil.isEntityClass(propertyClass)) {
					result.add(field);
				}
			}
		}
		return result;
	}
	
	protected List<Map<String, Object>> getNeedGeneratorFields(Object entity) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<Field> fields = FieldUtils.getFields(ProxyBeanUtils.getProxyTargetType(entity));
		if(fields != null) {
			for (Field field : fields) {
				Generator generator = field.getAnnotation(Generator.class);
				Map<String, Object> map =new HashMap<String, Object>();
				if(generator !=null) {
					map.put("field", field);
					map.put("policy", BeanUtils.newInstance(generator.policy()));
					result.add(map);
				} else {
					Id id = field.getAnnotation(Id.class);
					if (id != null && field.getType() == String.class) {
						map.put("field", field);
						map.put("policy", BeanUtils.newInstance(UUIDPolicy.class));
						result.add(map);
					}
				}
			}
		}
		return result;
	}

	public void setSavePolicy(SavePolicy savePolicy) {
		this.savePolicy = savePolicy;
	}
}