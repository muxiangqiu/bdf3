package com.bstek.bdf3.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.bstek.bdf3.security.access.intercept.FilterSecurityInterceptor;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年1月24日
 */
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
		
		@Autowired
		private FilterInvocationSecurityMetadataSource securityMetadataSource;
		
		@Autowired
		private AccessDecisionManager accessDecisionManager;
		
		@Autowired
		private UserDetailsService userDetailsService;
	
		@Autowired
		private PasswordEncoder passwordEncoder;

		@Override
		protected void configure(AuthenticationManagerBuilder auth)
				throws Exception {
			auth.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder);
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			
			http.csrf().disable();
			http.headers().frameOptions().disable();
			http.headers().xssProtection().disable();
			http.headers().disable();
			
			FilterSecurityInterceptor securityInterceptor = createFilterSecurityInterceptor();
			http.addFilterAfter(securityInterceptor, org.springframework.security.web.access.intercept.FilterSecurityInterceptor.class);
			http.setSharedObject(FilterSecurityInterceptor.class, securityInterceptor);
		} 
		
		private FilterSecurityInterceptor createFilterSecurityInterceptor() throws Exception {
			FilterSecurityInterceptor securityInterceptor = new FilterSecurityInterceptor();
			securityInterceptor.setSecurityMetadataSource(securityMetadataSource);
			securityInterceptor.setAccessDecisionManager(accessDecisionManager);
			securityInterceptor.setAuthenticationManager(this.authenticationManagerBean());
			securityInterceptor.afterPropertiesSet();
			return securityInterceptor;
		}

		
	}


