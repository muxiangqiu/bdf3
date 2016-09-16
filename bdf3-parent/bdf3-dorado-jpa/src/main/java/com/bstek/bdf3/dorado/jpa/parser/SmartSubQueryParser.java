package com.bstek.bdf3.dorado.jpa.parser;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import com.bstek.bdf3.dorado.jpa.CollectInfo;
import com.bstek.bdf3.dorado.jpa.JpaUtil;
import com.bstek.bdf3.dorado.jpa.lin.Linq;
import com.bstek.dorado.data.provider.filter.SingleValueFilterCriterion;

/**
 *@author Kevin.yang
 *@since 2015年8月16日
 */
public class SmartSubQueryParser implements CriterionParser {

	private List<CriterionParser> parsers = new ArrayList<CriterionParser>(3);
	
	public SmartSubQueryParser(Linq linq, Class<?> entityClass, List<CollectInfo> collectInfos) {
		PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(entityClass);
		for (PropertyDescriptor pd : pds) {
			if (JpaUtil.isEntityClass(pd.getPropertyType())) {
				boolean found = false;
				for (CollectInfo collectInfo : collectInfos) {
					Class<?> cls = collectInfo.getEntityClass();
					if (cls != null && pd.getPropertyType().isAssignableFrom(cls)) {
						parsers.add(new SubQueryParser(linq, cls, collectInfo.getProperties()));
						found = true;
						break;
					}
				}
				if (!found) {
					parsers.add(new SubQueryParser(linq, pd.getPropertyType()));
				}
			}
		}
	}
	
	@Override
	public boolean parse(SingleValueFilterCriterion criterion) {
		boolean result = false;
		for (CriterionParser parser : parsers) {
			result = parser.parse(criterion);
			if (result) {
				return result;
			}
		}
		return false;
	}

}
