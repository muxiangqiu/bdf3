package com.bstek.bdf3.profile.listener;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Component;

import com.bstek.bdf3.profile.Constants;
import com.bstek.bdf3.profile.domain.ComponentConfig;
import com.bstek.bdf3.profile.filter.ProfileFilter;
import com.bstek.bdf3.profile.provider.ProfileKeyProvider;
import com.bstek.bdf3.profile.service.ComponentConfigService;
import com.bstek.dorado.data.listener.GenericObjectListener;
import com.bstek.dorado.view.View;
import com.bstek.dorado.view.ViewElement;

@Component("profile.genericViewListener")
public class GenericViewListener extends GenericObjectListener<View> implements InitializingBean {
	
	@Autowired
	private List<ProfileFilter<?>> profileFilters;
	
	@Autowired
	private ComponentConfigService componentConfigService;
	
	@Autowired
	private ProfileKeyProvider profileKeyProvider;

	@Override
	public boolean beforeInit(View view) throws Exception {
		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void onInit(View view) throws Exception {
		if (!needProfile(view)) {
			return;
		}
		String viewName = view.getName();
		String profileKey = profileKeyProvider.getProfileKey();
		
		if (StringUtils.isEmpty(profileKey)) {
			return;
		}

		List<ComponentConfig> configs = componentConfigService.loadComponentConfigs(profileKey, viewName);
		if (configs.isEmpty()) {
			return;
		}
		
		for (ComponentConfig componentConfig : configs) {
			ViewElement viewElement = view.getViewElement(StringUtils.substringAfterLast(componentConfig.getControlId(), "."));
			if (viewElement != null) {
				for (ProfileFilter profileFilter : profileFilters) {
					if (profileFilter.support(viewElement)) {
						profileFilter.apply(viewElement, componentConfig);
					}
				}
			}
		}
		
	}
	
	private boolean needProfile(View view) {
		Map<String, Object> metaData = view.getMetaData();
		if (metaData != null && metaData.containsKey(Constants.NEED_PROFILE_FLAG_NAME) && (Boolean) metaData.get(Constants.NEED_PROFILE_FLAG_NAME)) {
			return true;
		}
		return false;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		AnnotationAwareOrderComparator.sort(profileFilters);
		
	}

}
