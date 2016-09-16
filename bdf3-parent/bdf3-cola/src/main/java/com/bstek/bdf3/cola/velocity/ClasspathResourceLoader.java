package com.bstek.bdf3.cola.velocity;

import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.exception.ResourceNotFoundException;


/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年4月17日
 */
public class ClasspathResourceLoader extends org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader {

	private String prefix;
	
	@Override
	public InputStream getResourceStream(String name)
			throws ResourceNotFoundException {
		if (StringUtils.isEmpty(prefix)) {
			prefix = "." + StringUtils.substringAfter(name, ".");
		}
		String newName = name;
		if (!StringUtils.equals(".html", prefix)) {
			newName = name.replace(prefix, ".html");
		}
		return super.getResourceStream("templates/" + newName);
	}

	
}
