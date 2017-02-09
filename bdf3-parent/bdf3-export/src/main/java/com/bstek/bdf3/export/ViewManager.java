package com.bstek.bdf3.export;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.bstek.dorado.data.Constants;
import com.bstek.dorado.data.config.DataTypeName;
import com.bstek.dorado.data.provider.DataProvider;
import com.bstek.dorado.data.provider.filter.FilterCriterionParser;
import com.bstek.dorado.data.provider.manager.DataProviderManager;
import com.bstek.dorado.data.type.DataType;
import com.bstek.dorado.data.type.manager.DataTypeManager;
import com.bstek.dorado.view.config.xml.ViewXmlConstants;
import com.bstek.dorado.view.manager.ViewConfig;
import com.bstek.dorado.view.manager.ViewConfigManager;
import com.bstek.dorado.view.service.DataServiceProcessorSupport.ParsedDataObjectName;
import com.bstek.dorado.web.DoradoContext;


@Component(ViewManager.BEAN_ID)
public class ViewManager {
	protected static final String PRIVATE_VIEW_OBJECT_PREFIX = ViewXmlConstants.PATH_VIEW_SHORT_NAME + Constants.PRIVATE_DATA_OBJECT_SUBFIX;
	public static final String BEAN_ID = "bdf3.viewManager";

	@Autowired
	@Qualifier("dorado.viewConfigManager")
	public ViewConfigManager viewConfigManager;

	@Autowired
	@Qualifier("dorado.dataProviderManager")
	public DataProviderManager dataProviderManager;

	@Autowired
	@Qualifier("dorado.dataTypeManager")
	public DataTypeManager dataTypeManager;

	@Autowired
	@Qualifier("dorado.filterCriterionParser")
	public FilterCriterionParser filterCriterionParser;

	public static ViewManager getInstance() {
		return (ViewManager) DoradoContext.getAttachedWebApplicationContext().getBean(ViewManager.BEAN_ID);
	}

	public FilterCriterionParser getFilterCriterionParser() {
		return filterCriterionParser;
	}

	public ViewConfig getViewConfig(DoradoContext context, String viewName) throws Exception {
		ViewConfig viewConfig = (ViewConfig) context.getAttribute(viewName);
		if (viewConfig == null) {
			viewConfig = viewConfigManager.getViewConfig(viewName);
			context.setAttribute(viewName, viewConfig);
		}
		return viewConfig;
	}

	public DataProvider getDataProvider(String dataProviderId) throws Exception {
		DataProvider dataProvider;
		if (dataProviderId.startsWith(PRIVATE_VIEW_OBJECT_PREFIX)) {
			ParsedDataObjectName parsedName = new ParsedDataObjectName(dataProviderId);
			ViewConfig viewConfig = getViewConfig(DoradoContext.getCurrent(), parsedName.getViewName());
			dataProvider = viewConfig.getDataProvider(parsedName.getDataObject());
		} else {
			dataProvider = dataProviderManager.getDataProvider(dataProviderId);
		}
		return dataProvider;
	}

	public DataType getDataType(String dataTypeName) throws Exception {
		DataType dataType;
		// 判断是否View中的私有DataType
		if (dataTypeName.startsWith(PRIVATE_VIEW_OBJECT_PREFIX)) {
			DoradoContext context = DoradoContext.getCurrent();
			dataType = (DataType) context.getAttribute(dataTypeName);
			if (dataType == null) {
				ParsedDataObjectName parsedName = new ParsedDataObjectName(dataTypeName);
				String viewName = parsedName.getViewName();
				ViewConfig viewConfig = this.getViewConfig(context, viewName);
				String name = parsedName.getDataObject();
				DataTypeName dtn = new DataTypeName(name);
				if (dtn.getSubDataTypes().length == 1) {
					String subName = dtn.getSubDataTypes()[0];
					if (subName.startsWith(PRIVATE_VIEW_OBJECT_PREFIX)) {
						parsedName = new ParsedDataObjectName(subName);
						name = new StringBuffer().append(dtn.getOriginDataType()).append('[').append(parsedName.getDataObject()).append(']').toString();
					}
				}
				dataType = viewConfig.getDataType(name);
				context.setAttribute(dataTypeName, dataType);
			}
		} else {
			dataType = dataTypeManager.getDataType(dataTypeName);
		}
		return dataType;
	}

}
