package com.bstek.bdf3.export.context;

import com.bstek.dorado.core.io.ResourceLoader;
import com.bstek.dorado.core.pkgs.PackageConfigurer;

public class ExportContextLocationConfigurer implements PackageConfigurer {

	public String[] getPropertiesConfigLocations(ResourceLoader resourceLoader) throws Exception {
		return new String[]{"classpath:com/bstek/bdf3/export/configure.properties"};
	}

	public String[] getContextConfigLocations(ResourceLoader resourceLoader) throws Exception {
		return null;
	}

	public String[] getServletContextConfigLocations(ResourceLoader resourceLoader) throws Exception {
		return null;
	}

}
