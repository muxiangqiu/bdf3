package com.bstek.bdf3.cola.configure;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import com.bstek.bdf3.cola.custom.RequestResponseBodyMethodProcessorAdapter;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年1月24日
 */
@Configuration
public class ColaConfiguration {
		
	@Configuration
	public static class ReturnValueHandlerCustom implements InitializingBean {

		@Autowired(required = false)
		private RequestMappingHandlerAdapter adapter; 
		
		@SuppressWarnings("unchecked")
		@Override
		public void afterPropertiesSet() throws Exception {
			if (adapter != null) {
				Field field = ReflectionUtils.findField(RequestMappingHandlerAdapter.class, "contentNegotiationManager");
				field.setAccessible(true);
				ContentNegotiationManager contentNegotiationManager = (org.springframework.web.accept.ContentNegotiationManager) field.get(adapter);
				field = ReflectionUtils.findField(RequestMappingHandlerAdapter.class, "requestResponseBodyAdvice");
				field.setAccessible(true);
				List<Object> requestResponseBodyAdvice = (List<Object>) field.get(adapter);
				
				List<HandlerMethodReturnValueHandler> handlers = adapter.getReturnValueHandlers();
				List<HandlerMethodReturnValueHandler> result = new ArrayList<HandlerMethodReturnValueHandler>();
				for (HandlerMethodReturnValueHandler handler : handlers) {
					if (handler instanceof RequestResponseBodyMethodProcessor) {
						result.add(new RequestResponseBodyMethodProcessorAdapter(adapter.getMessageConverters(), contentNegotiationManager, requestResponseBodyAdvice));
					} else {
						result.add(handler);
					}
				}
				adapter.setReturnValueHandlers(result);
			}
		}
	}
	
	@Configuration
	public static class PageParameterCustom implements InitializingBean {

		@Autowired(required = false)
		private PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver;
		
		@Override
		public void afterPropertiesSet() throws Exception {
			if (pageableHandlerMethodArgumentResolver != null) {
				pageableHandlerMethodArgumentResolver.setPageParameterName("pageNo");
				pageableHandlerMethodArgumentResolver.setSizeParameterName("pageSize");
				pageableHandlerMethodArgumentResolver.setOneIndexedParameters(true);
			}		
		}

	}
	

	

}
