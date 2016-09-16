package com.bstek.bdf3.dorado.jpa;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.bstek.bdf3.dorado.jpa.policy.CriteriaPolicy;
import com.bstek.bdf3.dorado.jpa.policy.SavePolicy;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年2月14日
 */
public class JpaUtilAble implements InitializingBean{
	@Autowired
	private CriteriaPolicy defaultQBCCriteriaPolicy;
	
	@Autowired
	private SavePolicy defaultSavePolicy;
	
	
	@SuppressWarnings("unused")
	@Autowired(required = false)
	private List<JpaUtilAble> jpaUtilAbles;

	@Override
	public void afterPropertiesSet() throws Exception {
		JpaUtil.setDefaultQBCCriteriaPolicy(defaultQBCCriteriaPolicy);
		JpaUtil.setDefaultSavePolicy(defaultSavePolicy);
	}
}
