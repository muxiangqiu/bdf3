package com.bstek.bdf3.dorado.jpa.parser;

import java.beans.Introspector;

import org.apache.commons.lang.StringUtils;

import com.bstek.bdf3.dorado.jpa.CriteriaUtils;
import com.bstek.bdf3.dorado.jpa.JpaUtil;
import com.bstek.bdf3.dorado.jpa.lin.Linq;
import com.bstek.dorado.data.provider.filter.SingleValueFilterCriterion;

/**
 *@author Kevin.yang
 *@since 2015年8月16日
 */
public class SubQueryParser implements CriterionParser {

	private Class<?> domainClass;
	private Linq linq;
	private String[] foreignKeys;
	
	public SubQueryParser(Linq linq, Class<?> domainClass) {
		this.linq = linq;
		this.domainClass = domainClass;	
		this.foreignKeys = new String[]{ Introspector.decapitalize(domainClass.getSimpleName()) 
				+ StringUtils.capitalize(JpaUtil.getIdName(domainClass)) };
	}
	
	public SubQueryParser(Linq linq, Class<?> domainClass, String... foreignKeys) {
		this(linq, domainClass);
		this.foreignKeys = foreignKeys;
		
	}
	
	@Override
	public boolean parse(SingleValueFilterCriterion criterion) {
		String property = criterion.getProperty();
		if (StringUtils.contains(property, '.')) {
			String alias = StringUtils.substringBefore(property, ".");
			
			for (String foreignKey : foreignKeys) {
				if (StringUtils.startsWith(alias, foreignKey)
						|| StringUtils.startsWith(foreignKey, alias)) {
					Linq l = linq.exists(domainClass)
							.equalProperty(foreignKey, JpaUtil.getIdName(domainClass));
					CriteriaUtils.parse(l, criterion);
					return true;
				}
			}
		}
		return false;
	}

}
