package com.bstek.bdf3.dorado.jpa.policy.impl;

import java.lang.reflect.Field;
import java.util.Date;

/**
 *@author Kevin.yang
 *@since 2015年5月18日
 */
public class UpdatedDatePolicy extends AbstractModifiedGeneratorPolicy {

	@Override
	protected Object getValue(Object entity, Field field) {
		return new Date();
	}


}
