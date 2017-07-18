package com.bstek.bdf3.profile.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf3.dorado.jpa.JpaUtil;
import com.bstek.bdf3.profile.domain.ComponentConfig;
import com.bstek.bdf3.profile.domain.ComponentConfigMember;
import com.bstek.bdf3.profile.provider.ProfileKeyProvider;
import com.bstek.dorado.data.variant.Record;

@Service
@Transactional(readOnly = true)
public class ComponentConfigServiceImpl implements ComponentConfigService {
	
	@Autowired
	private ProfileKeyProvider profileKeyProvider;
	
	@Override
	public List<ComponentConfig> loadComponentConfigs(String profileKey, String viewName) {
		List<ComponentConfig> componentConfigs = JpaUtil.linq(ComponentConfig.class)
				.equal("name", profileKey)
				.like("controlId", viewName + ".%")
				.list();
		if (!componentConfigs.isEmpty()) {
			List<ComponentConfigMember> list = loadComponentConfigMembers(JpaUtil.collectId(componentConfigs));
			Map<String, List<ComponentConfigMember>> map = list.stream()
					.collect(Collectors.groupingBy(ComponentConfigMember::getComponentConfigId));
			
			componentConfigs.forEach(componentConfig -> componentConfig.setComponentConfigMembers(map.get(componentConfig.getId())));
		}
		
		return componentConfigs;
	}

	@Transactional
	@Override
	public void removeComponentProfileByControlId(String controlId, String name) {
		JpaUtil.lind(ComponentConfigMember.class)
			.exists(ComponentConfig.class)
				.equalProperty("id", "componentConfigId")
				.equal("controlId", controlId)
				.equal("name", name)
			.end()
			.delete();
		JpaUtil.lind(ComponentConfig.class)
				.equal("controlId", controlId)
				.equal("name", name)
				.delete();
	}

	private ComponentConfigMember record2ConfigMember(ComponentConfig componentConfig, Record record) {
		ComponentConfigMember componentConfigMember = new ComponentConfigMember();
		componentConfigMember.setId(UUID.randomUUID().toString());
		componentConfigMember.setControlType(record.getString("controlType"));
		componentConfigMember.setControlName(record.getString("controlName"));
		componentConfigMember.setOrder(record.getInt("order"));
		componentConfigMember.setParentControlName(record.getString("parentControl"));
		componentConfigMember.setCaption(record.getString("caption"));
		componentConfigMember.setWidth(record.getString("width"));
		componentConfigMember.setColSpan(record.getInt("colSpan"));
		componentConfigMember.setRowSpan(record.getInt("rowSpan"));
		componentConfigMember.setVisible(record.getBoolean("visible"));
		componentConfigMember.setComponentConfigId(componentConfig.getId());
		return componentConfigMember;
	}

	private List<ComponentConfigMember> loadComponentConfigMembers(Set<String> configIdSet) {
		return JpaUtil.linq(ComponentConfigMember.class)
				.in("componentConfigId", configIdSet)
				.asc("order")
				.list();
	}
	
	@Override
	@Transactional
	public void saveComponentProfile(String controlId, String profileKey, String cols, Collection<Record> members) {

		if (StringUtils.isEmpty(profileKey)) {
			profileKey = profileKeyProvider.getProfileKey();
		}
		removeComponentProfileByControlId(controlId, profileKey);

		ComponentConfig componentConfig = new ComponentConfig();
		componentConfig.setId(UUID.randomUUID().toString());
		componentConfig.setName(profileKey);
		componentConfig.setCols(cols);
		componentConfig.setControlId(controlId);

		if (members != null && !members.isEmpty()) {
			JpaUtil.persist(componentConfig);
			for (Record record : members) {
				JpaUtil.persist(record2ConfigMember(componentConfig, record));
			}
		}
	}
	
	@Override
	@Transactional
	public void resetComponentProfile(String controlId) {
		String profileKey = profileKeyProvider.getProfileKey();
		removeComponentProfileByControlId(controlId, profileKey);
	}


}
