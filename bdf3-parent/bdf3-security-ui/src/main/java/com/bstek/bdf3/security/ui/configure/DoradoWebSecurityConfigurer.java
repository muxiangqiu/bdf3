package com.bstek.bdf3.security.ui.configure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.bstek.bdf3.security.WebSecurityConfigurer;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年1月24日
 */
@Component
@Order(120)
public class DoradoWebSecurityConfigurer extends WebSecurityConfigurer {
		
		private static final String URL_PREFIX = "/";

		@Value("${bdf3.loginPath}")
		private String loginPath;
		
		@Value("${bdf3.logoutPath}")
		private String logoutPath;
		
		@Value("${bdf3.systemAnonymous}")
		private String systemAnonymous;
		
		@Value("${bdf3.customAnonymous}")
		private String customAnonymous;

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			
			http.authorizeRequests()
					.antMatchers(mergeAnonymous())
					.permitAll()
					.anyRequest()
					.authenticated()
				.and()
					.formLogin()
					.loginPage(URL_PREFIX + loginPath)
					.permitAll()
				.and()
					.logout()
					.logoutUrl(URL_PREFIX + logoutPath)
					.permitAll()
				.and()
					.rememberMe();
			
			super.configure(http);

		}
		
		private String[] mergeAnonymous() {
			String[] anonymous = null;
			if (StringUtils.hasText(systemAnonymous) && StringUtils.hasText(customAnonymous)) {
				anonymous = (systemAnonymous + "," + customAnonymous).split(",");
			} else if (StringUtils.hasText(systemAnonymous)) {
				anonymous = systemAnonymous.split(",");
			}  else if (StringUtils.hasText(customAnonymous)) {
				anonymous = customAnonymous.split(",");
			}
			return anonymous;
		}
		
	}


