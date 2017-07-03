package com.bstek.bdf3.dorado.jpa.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bstek.bdf3.dorado.jpa.initiator.DoradoJpaUtilInitiator;
import com.bstek.bdf3.dorado.jpa.policy.CriteriaPolicy;
import com.bstek.bdf3.dorado.jpa.policy.SavePolicy;
import com.bstek.bdf3.dorado.jpa.policy.impl.DirtyTreeSavePolicy;
import com.bstek.bdf3.dorado.jpa.policy.impl.QBCCriteriaPolicy;
import com.bstek.bdf3.dorado.jpa.policy.impl.SmartSavePolicy;


/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年1月24日
 */
@Configuration
public class JpaConfiguration {
	
	
	@Bean
	public CriteriaPolicy defaultQBCCriteriaPolicy() {
		return new QBCCriteriaPolicy();
	}
	
	@Bean
	public SavePolicy defaultSavePolicy() {
		DirtyTreeSavePolicy savePolicy = new DirtyTreeSavePolicy();
		savePolicy.setSavePolicy(new SmartSavePolicy());
		return savePolicy;
	}
	
	@Bean
	public DoradoJpaUtilInitiator doradoJpaUtilInitiator() {
		return new DoradoJpaUtilInitiator();
	}


}
