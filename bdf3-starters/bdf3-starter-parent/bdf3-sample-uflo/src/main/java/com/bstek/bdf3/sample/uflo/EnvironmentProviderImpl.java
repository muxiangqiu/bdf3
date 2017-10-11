package com.bstek.bdf3.sample.uflo;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import com.bstek.bdf3.security.ContextUtils;
import com.bstek.uflo.env.EnvironmentProvider;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年10月11日
 */
@Component
public class EnvironmentProviderImpl implements EnvironmentProvider {

	@Autowired
	@Qualifier("platformTransactionManager")
	private PlatformTransactionManager platformTransactionManager;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public String getCategoryId() {
		return "Bstek";
	}

	@Override
	public String getLoginUser() {
		return ContextUtils.getLoginUsername();
	}

	@Override
	public PlatformTransactionManager getPlatformTransactionManager() {
		return this.platformTransactionManager;
	}

	@Override
	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

}
