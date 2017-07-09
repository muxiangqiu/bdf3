package com.bstek.bdf3.saas.listener;

import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder.Builder;

import com.bstek.bdf3.saas.domain.Organization;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年7月4日
 */
public interface EntityManagerFactoryCreateListener {

	void onCreate(Organization organization, Builder builder);
}
