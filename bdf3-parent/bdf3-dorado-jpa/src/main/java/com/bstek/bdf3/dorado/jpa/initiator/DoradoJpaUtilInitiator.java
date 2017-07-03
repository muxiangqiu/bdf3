package com.bstek.bdf3.dorado.jpa.initiator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.bstek.bdf3.dorado.jpa.JpaUtil;
import com.bstek.bdf3.dorado.jpa.policy.CriteriaPolicy;
import com.bstek.bdf3.dorado.jpa.policy.SavePolicy;
import com.bstek.bdf3.jpa.initiator.JpaUtilInitiator;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年7月3日
 */
public class DoradoJpaUtilInitiator implements JpaUtilInitiator {

	@Autowired
	private CriteriaPolicy defaultQBCCriteriaPolicy;
	
	@Autowired
	private SavePolicy defaultSavePolicy;
	

	@Override
	public void initialize(ApplicationContext applicationContext) {
		JpaUtil.setDefaultQBCCriteriaPolicy(defaultQBCCriteriaPolicy);
		JpaUtil.setDefaultSavePolicy(defaultSavePolicy);
	}

}
