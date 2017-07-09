package com.bstek.bdf3.saas.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bstek.bdf3.saas.Constants;
import com.bstek.bdf3.saas.script.DynamicResourceDatabasePopulator;
import com.bstek.bdf3.saas.script.ScriptVarRegister;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年7月4日
 */
@Service
public class ScriptServiceImpl implements ScriptService {
	
	@Autowired
	private ConfigurableApplicationContext applicationContext;
	
	@Autowired
	private DataSourceProperties properties;
	
	@Autowired(required = false)
	private List<ScriptVarRegister> scriptVarRegisters;
	
	@Override
	public void runScripts(String organizationId, DataSource dataSource, String locations, String fallback) {
		Map<String, Object> vars = new HashMap<>();
		vars.put("organizationId", organizationId);
		if (scriptVarRegisters != null) {
			for (ScriptVarRegister scriptVarRegister : scriptVarRegisters) {
				scriptVarRegister.register(vars);
			}
		}
		List<Resource> scripts = getScripts(locations, fallback);
		runScripts(scripts, dataSource, vars);
	}

	private List<Resource> getScripts(String locations, String fallback) {
		if (StringUtils.isEmpty(locations)) {
			String platform = this.properties.getPlatform();
			locations = "classpath*:" + fallback + "-" + platform + ".sql,";
			locations += "classpath*:" + fallback + ".sql";
		}
		return getResources(locations);
	}

	private List<Resource> getResources(String locations) {
		List<Resource> resources = new ArrayList<Resource>();
		for (String location : StringUtils.commaDelimitedListToStringArray(locations)) {
			try {
				for (Resource resource : this.applicationContext.getResources(location)) {
					if (resource.exists()) {
						resources.add(resource);
					}
				}
			}
			catch (IOException ex) {
				throw new IllegalStateException(
						"Unable to load resource from " + location, ex);
			}
		}
		return resources;
	}

	private void runScripts(List<Resource> resources, DataSource dataSource, Map<String, Object> vars) {
		if (resources.isEmpty()) {
			return;
		}
		DynamicResourceDatabasePopulator populator = new DynamicResourceDatabasePopulator();
		populator.setContinueOnError(this.properties.isContinueOnError());
		populator.setSeparator(this.properties.getSeparator());
		if (this.properties.getSqlScriptEncoding() != null) {
			populator.setSqlScriptEncoding(this.properties.getSqlScriptEncoding().name());
		}
		for (Resource resource : resources) {
			populator.addScript(resource);
		}
		populator.setVars(vars);
		DatabasePopulatorUtils.execute(populator, dataSource);
	}

	@Override
	public void runScripts(DataSource dataSource, String locations, String fallback) {
		runScripts(Constants.MASTER, dataSource, locations, fallback);
		
	}



}
