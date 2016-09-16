package com.bstek.bdf3.security.ui.utils;

import com.bstek.dorado.web.DoradoContext;

/**
 * @author Kevin.yang
 */
public final class UrlUtils {

	public static String getRequestPath() {
		String url = DoradoContext.getCurrent().getRequest().getServletPath().substring(1);
		if (DoradoContext.getCurrent().getRequest().getPathInfo() != null) {
			url += DoradoContext.getCurrent().getRequest().getPathInfo();
		}
		return url;
	}
}