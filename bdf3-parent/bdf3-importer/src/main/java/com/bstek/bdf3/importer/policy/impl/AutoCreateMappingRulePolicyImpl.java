package com.bstek.bdf3.importer.policy.impl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Transient;

import org.apache.commons.lang.ClassUtils;
import org.springframework.beans.BeanUtils;

import com.bstek.bdf3.dorado.jpa.FieldUtils;
import com.bstek.bdf3.dorado.jpa.JpaUtil;
import com.bstek.bdf3.importer.model.ImporterSolution;
import com.bstek.bdf3.importer.model.MappingRule;
import com.bstek.bdf3.importer.policy.AutoCreateMappingRulePolicy;
import com.bstek.dorado.annotation.PropertyDef;
import com.bstek.dorado.data.entity.EntityUtils;


/**
 *@author Kevin.yang
 *@since 2015年9月3日
 */
public class AutoCreateMappingRulePolicyImpl implements AutoCreateMappingRulePolicy {

	@Override
	public void apply(ImporterSolution importerSolution) {
		try {
			Class<?> entityClass = ClassUtils.getClass(importerSolution.getEntityClassName());
			List<Field> fields = FieldUtils.getFields(entityClass);
			List<String> propertyNames = getPropertyNames(importerSolution);
			int col = propertyNames.size();

			for (Field field : fields) {
				Transient t = field.getAnnotation(Transient.class);
				String propertyName = field.getName();
				if (t != null 
						|| BeanUtils.getPropertyDescriptor(entityClass, propertyName) == null 
						|| !EntityUtils.isSimpleType(field.getType())
						|| contains(propertyNames, propertyName)) {
					continue;
				}
				MappingRule mappingRule = new MappingRule();
				mappingRule.setId(UUID.randomUUID().toString());
				mappingRule.setImporterSolutionId(importerSolution.getId());
				mappingRule.setName(propertyName);
				mappingRule.setPropertyName(propertyName);
				mappingRule.setExcelColumn(col);;
				
				PropertyDef propertyDef = field.getAnnotation(PropertyDef.class);
				Column column = field.getAnnotation(Column.class);
				if (propertyDef != null) {
					mappingRule.setName(propertyDef.label());
				}
				if (column != null) {
					
				}
				JpaUtil.persist(mappingRule);
				col++;
			}
					
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	private List<String> getPropertyNames(ImporterSolution importerSolution) {
		return JpaUtil.linq(MappingRule.class, String.class)
				.select("propertyName")
				.equal("importerSolutionId", importerSolution.getId())
				.list();
	}
	
	private boolean contains(List<String> propertyNames, String propertyName) {
		for (String p : propertyNames) {
			if (p.equals(propertyName)) {
				return true;
			}
		}
		return false;
	}

}
