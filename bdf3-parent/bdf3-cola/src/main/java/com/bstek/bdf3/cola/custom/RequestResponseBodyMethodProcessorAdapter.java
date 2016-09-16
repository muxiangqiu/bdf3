package com.bstek.bdf3.cola.custom;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.SynthesizingMethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年3月6日
 */
public class RequestResponseBodyMethodProcessorAdapter extends
	RequestResponseBodyMethodProcessor {
	public RequestResponseBodyMethodProcessorAdapter(
			List<HttpMessageConverter<?>> converters,
			ContentNegotiationManager manager,
			List<Object> requestResponseBodyAdvice) {
		super(converters, manager, requestResponseBodyAdvice);
	}


		
	@SuppressWarnings("rawtypes")
	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest)
			throws IOException, HttpMediaTypeNotAcceptableException, HttpMessageNotWritableException {

		mavContainer.setRequestHandled(true);
		
		if (returnValue instanceof Page) {
			Page page = (Page) returnValue;
			Map<String, Object> pageMap = new HashMap<String, Object>();
			pageMap.put("$data", page.getContent());
			pageMap.put("$entityCount", page.getTotalElements());
			writeWithMessageConverters(pageMap, new ReturnValueMethodParameter(returnType.getMethod(), pageMap), webRequest);
		} else {
			// Try even with null return value. ResponseBodyAdvice could get involved.
			writeWithMessageConverters(returnValue, returnType, webRequest);
		}
	}

	
	/**
	 * A MethodParameter for a HandlerMethod return type based on an actual return value.
	 */
	private class ReturnValueMethodParameter extends SynthesizingMethodParameter {

		private final Object returnValue;

		public ReturnValueMethodParameter(Method method, Object returnValue) {
			super(method, -1);
			this.returnValue = returnValue;
		}

		@Override
		public Class<?> getParameterType() {
			return (this.returnValue != null ? this.returnValue.getClass() : super.getParameterType());
		}
	}
}
