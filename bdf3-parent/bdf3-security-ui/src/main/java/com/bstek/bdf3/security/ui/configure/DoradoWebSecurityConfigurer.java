package com.bstek.bdf3.security.ui.configure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

import com.bstek.bdf3.security.WebSecurityConfigurer;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年1月24日
 */
@Component
@Order(120)
public class DoradoWebSecurityConfigurer extends WebSecurityConfigurer {
		

		@Value("${bdf3.security.loginPath:/bdf3.security.ui.view.Login.d}")
		private String loginPath;
		
		@Value("${bdf3.security.logoutPath:/logout}")
		private String logoutPath;

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			
			http.authorizeRequests()
					.antMatchers("/static/**", "/**/*.dpkg", "/dorado/client/**", "/**/*.js", "/**/*.css")
					.permitAll()
					.anyRequest()
					.authenticated()
				.and()
					.formLogin()
					.loginPage(loginPath)
					.permitAll()
				.and()
					.logout()
					.logoutUrl(logoutPath)
					.permitAll()
				.and()
					.rememberMe();
			
			super.configure(http);

		}
		
	}


