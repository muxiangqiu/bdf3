package com.bstek.bdf3.autoconfigure.dorado;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.CacheControl;

import com.bstek.bdf3.dorado.configure.DoradoConfiguration;
import com.bstek.dorado.web.resolver.AbstractResolver;
import com.bstek.dorado.web.servlet.DoradoServlet;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年4月27日
 */
@Configuration
@ConditionalOnClass(DoradoConfiguration.class)
@AutoConfigureAfter(EmbeddedServletContainerAutoConfiguration.class)
@Import(DoradoConfiguration.class)
public class DoradoAutoConfiguration implements ApplicationContextAware {
	
	@Autowired
	private ResourceProperties resourceProperties;

	
	
	@Bean
    public ServletRegistrationBean doradoServletRegistrationBean() {
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new DoradoServlet(), "*.d", "*.dpkg", "/dorado/*");
		servletRegistrationBean.setLoadOnStartup(1);
		return servletRegistrationBean;
    }
	
	@Bean
	public FilterRegistrationBean someFilterRegistration() {

	    FilterRegistrationBean registration = new FilterRegistrationBean();
	    registration.setFilter(new Filter() {
	    	
	    	private static final String HEADER_PRAGMA = "Pragma";

	    	private static final String HEADER_EXPIRES = "Expires";

	    	private static final String HEADER_CACHE_CONTROL = "Cache-Control";

			@Override
			public void destroy() {
				
			}

			@Override
			public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
					throws IOException, ServletException {
				HttpServletResponse httpResponse = (HttpServletResponse) res;
				Integer cachePeriod = resourceProperties.getCachePeriod();
				if (cachePeriod == null) {
					cachePeriod = 31536000;
				}
				applyCacheControl(httpResponse, CacheControl.maxAge(cachePeriod, TimeUnit.SECONDS));
				chain.doFilter(req, res);
				
			}
			
			private void applyCacheControl(HttpServletResponse response, CacheControl cacheControl) {
				String ccValue = cacheControl.getHeaderValue();
				if (ccValue != null) {
					// Set computed HTTP 1.1 Cache-Control header
					response.setHeader(HEADER_CACHE_CONTROL, ccValue);

					if (response.containsHeader(HEADER_PRAGMA)) {
						// Reset HTTP 1.0 Pragma header if present
						response.setHeader(HEADER_PRAGMA, "");
					}
					if (response.containsHeader(HEADER_EXPIRES)) {
						// Reset HTTP 1.0 Expires header if present
						response.setHeader(HEADER_EXPIRES, "");
					}
				}
			}


			@Override
			public void init(FilterConfig arg0) throws ServletException {
				
			}
	    	
	    });
	    registration.addUrlPatterns("*.dpkg", "/dorado/res/**", "/dorado/client/**");
	    registration.setOrder(Integer.MAX_VALUE -100);
	    return registration;
	} 

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		Map<String, AbstractResolver> map = applicationContext.getBeansOfType(AbstractResolver.class);
		for (Entry<String, AbstractResolver> entry : map.entrySet()) {
			entry.getValue().setCacheSeconds(300);;
		}
		
	}
	
	
}
