package com.bstek.bdf3.autoconfigure.dorado;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.bstek.bdf3.dorado.configure.DoradoConfiguration;
import com.bstek.dorado.web.servlet.DoradoServlet;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年4月27日
 */
@Configuration
@ConditionalOnClass(DoradoConfiguration.class)
@AutoConfigureAfter(EmbeddedServletContainerAutoConfiguration.class)
@Import(DoradoConfiguration.class)
public class DoradoAutoConfiguration {
	
	
	@Bean
    public ServletRegistrationBean doradoServletRegistrationBean() {
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new DoradoServlet(), "*.d", "*.dpkg", "/dorado/*");
		servletRegistrationBean.setLoadOnStartup(1);
		return servletRegistrationBean;
    }
	
	
	
}
