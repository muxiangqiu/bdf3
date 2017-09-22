package com.bstek.bdf3.profile.service;

import java.util.Collection;
import java.util.List;

import com.bstek.bdf3.profile.domain.ComponentConfig;
import com.bstek.dorado.data.variant.Record;


public interface ComponentConfigService {

	void removeComponentProfileByControlId(String controlId, String name);

	List<ComponentConfig> loadComponentConfigs(String profileKey, String viewName);

	void saveComponentProfile(String controlId, String profileKey, String cols, Collection<Record> members);

	void resetComponentProfile(String controlId);
}
