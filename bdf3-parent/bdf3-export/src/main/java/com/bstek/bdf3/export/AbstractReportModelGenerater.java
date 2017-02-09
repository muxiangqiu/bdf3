package com.bstek.bdf3.export;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

import com.bstek.bdf3.export.interceptor.IDataInterceptor;
import com.bstek.bdf3.export.model.ReportForm;
import com.bstek.bdf3.export.model.ReportFormData;
import com.bstek.bdf3.export.model.ReportTitle;
import com.bstek.bdf3.export.model.ReportTitleStyle;
import com.bstek.bdf3.export.utils.ColorUtils;
import com.bstek.bdf3.export.utils.CriterionUtils;
import com.bstek.bdf3.export.view.SupportWidget;
import com.bstek.dorado.data.JsonUtils;
import com.bstek.dorado.data.ParameterWrapper;
import com.bstek.dorado.data.entity.EntityCollection;
import com.bstek.dorado.data.entity.EntityList;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.DataProvider;
import com.bstek.dorado.data.provider.Page;
import com.bstek.dorado.data.type.DataType;
import com.bstek.dorado.data.variant.MetaData;
import com.bstek.dorado.web.DoradoContext;

import net.sf.ehcache.hibernate.management.impl.BeanUtils;

public abstract class AbstractReportModelGenerater {

	public ReportTitle generateReportTitleModel(Map<String, Object> titleInfos) {
		ReportTitle reportTitle = new ReportTitle();
		if (titleInfos == null) {
			return reportTitle;
		}
		Boolean showTitle = (Boolean) titleInfos.get("showTitle");
		if (showTitle != null && showTitle) {
			reportTitle.setShowTitle(true);
			reportTitle.setTitle((String) titleInfos.get("title"));
			reportTitle.setStyle(createTitleStyle(titleInfos));
		}
		if (titleInfos.get("showBorder") != null) {
			reportTitle.setShowBorder((Boolean) titleInfos.get("showBorder"));
		}
		if (titleInfos.get("showPageNumber") != null) {
			reportTitle.setShowPageNo((Boolean) titleInfos.get("showPageNumber"));
		}
		return reportTitle;
	}

	@SuppressWarnings("unchecked")
	public ReportForm generateReportFormModel(Map<String, Object> formInfo, String intercepterBean) throws Exception {
		Map<String, Object> map = (Map<String, Object>) formInfo.get("formStyle");
		ReportForm reportFormModel = new ReportForm();
		List<ReportFormData> listReportFormDatas = new ArrayList<ReportFormData>();
		int columnCount = (Integer) formInfo.get("columnCount");
		boolean showBorder = true;
		if (map.get("showBorder") != null) {
			showBorder = (Boolean) map.get("showBorder");
		}
		reportFormModel.setColumnCount(columnCount);
		reportFormModel.setShowBorder(showBorder);
		List<Map<String, Object>> datas = (List<Map<String, Object>>) formInfo.get("datas");
		this.fireAutoFormDataInterceptor(intercepterBean, datas);
		int labelAlign = 2;
		int dataAlign = 0;
		int dataStyle = 0;
		if (map.get("labelAlign") != null) {
			labelAlign = (Integer) map.get("labelAlign");
		}
		if (map.get("dataStyle") != null) {
			dataStyle = (Integer) map.get("dataStyle");
		}
		if (map.get("dataAlign") != null) {
			dataAlign = (Integer) map.get("dataAlign");
		}
		ReportFormData reportFormDataModel = null;
		for (Map<String, Object> dataMap : datas) {
			reportFormDataModel = new ReportFormData();
			Object data = dataMap.get("value");
			int colSpan = (Integer) dataMap.get("colSpan");
			int rowSpan = (Integer) dataMap.get("rowSpan");
			String label = (String) dataMap.get("label");
			reportFormDataModel.setLabel(label);
			reportFormDataModel.setLabelAlign(labelAlign);
			reportFormDataModel.setData(data);
			reportFormDataModel.setDataAlign(dataAlign);
			reportFormDataModel.setDataStyle(dataStyle);
			reportFormDataModel.setColSpan(colSpan);
			reportFormDataModel.setRowSpan(rowSpan);
			listReportFormDatas.add(reportFormDataModel);
		}
		reportFormModel.setListReportFormDataModel(listReportFormDatas);
		return reportFormModel;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map<String, Object>> getGridModelData(Map<String, Object> map, List<Map<String, Object>> columnInfos, String intercepterBean) throws Exception {
		List<Map<String, Object>> dataList = null;
		String dataScope = (String) map.get("dataScope");
		String treeColumn = (String) map.get("treeColumn");
		int maxSize = Integer.valueOf(map.get("maxSize").toString());
		ViewManager viewManager = ViewManager.getInstance();
		if (dataScope.equals("serverAll")) {
			Object dataProviderParameter = map.get("dataProviderParameter");
			String dataProviderId = (String) map.get("dataProviderId");
			int pageSize = Integer.valueOf(map.get("pageSize").toString());
			if (map.get("sysParameter") != null) {
				ObjectMapper om = JsonUtils.getObjectMapper();
				String sp = om.writeValueAsString(map.get("sysParameter"));
				ObjectNode rudeSysParameter = (ObjectNode) om.readTree(sp);
				JsonNode rudeCriteria = null;
				if (rudeSysParameter != null) {
					rudeCriteria = rudeSysParameter.remove("criteria");
				}
				MetaData sysParameter = null;
				if (rudeSysParameter != null) {
					sysParameter = (MetaData) JsonUtils.toJavaObject(rudeSysParameter, null, null, false, null);
					if (rudeCriteria != null && rudeCriteria instanceof ObjectNode) {
						sysParameter.put("criteria", CriterionUtils.getCriteria((ObjectNode) rudeCriteria));
					}
					if (sysParameter != null && !sysParameter.isEmpty()) {
						dataProviderParameter = new ParameterWrapper(dataProviderParameter, sysParameter);
					}
				}
			}
			DataType resultDataType = null;
			String resultDataTypeName = (String) map.get("resultDataType");
			if (StringUtils.isNotEmpty(resultDataTypeName)) {
				resultDataType = viewManager.getDataType(resultDataTypeName);
			}
			DataProvider dataProvider = viewManager.getDataProvider(dataProviderId);
			Collection<Object> collection = null;
			if (pageSize > 0) {
				Page<Object> page = new Page<Object>(maxSize, 1);
				dataProvider.getPagingResult(dataProviderParameter, page, resultDataType);
				collection = page.getEntities();
			} else {
				collection = (Collection<Object>) dataProvider.getResult(dataProviderParameter, resultDataType);
			}
			if (collection instanceof EntityList) {
				if(collection.size()<=65536){
					collection = ((EntityList) collection).getTarget();
				}else{
					Field field = ((EntityCollection)collection).getClass().getField("target");
					field.setAccessible(true);
					collection = (Collection<Object>) field.get(collection);
				}
			}
			dataList = new ArrayList<Map<String, Object>>();
			for (Object obj : collection) {
//				if (ProxyBeanUtils.isProxy(obj)) {
//					obj = ProxyBeanUtils.getProxyTarget(obj);
//				}
				if (obj instanceof Map) {
					dataList.add((Map<String, Object>) obj);
				} else {
					if(collection.size()<=65536){
						Map<String, Object> targetMap = new HashMap<String, Object>();
						EntityUtils.copyProperties(targetMap, obj);
						dataList.add(targetMap);
					}else{
						dataList.add(PropertyUtils.describe(obj));
					}
				}
			}
			dataList = retrieveServerComplexPropertyData(dataList, columnInfos);
		} else if (dataScope.equals("currentPage")) {
			dataList = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> clientData = (List<Map<String, Object>>) map.get("data");
			List<Object> subDataList;
			for (Map<String, Object> tempMap : clientData) {
				if (tempMap.get(treeColumn) != null) {
					map.put(treeColumn, tempMap.get(treeColumn));
				}
				dataList.add(tempMap);
				if (tempMap.get("children") != null) {
					subDataList = (List<Object>) tempMap.get("children");
					this.createChildData(dataList, subDataList, "", treeColumn);
				}
			}
		}
		for (Map<String, Object> dataMap : dataList) {
			Set<String> nameSet = dataMap.keySet();
			for (String name : nameSet) {
				replaceValueWithMapping(dataMap, columnInfos, name);
			}
		}
		fireGridDataInterceptor(intercepterBean, dataList);
		return dataList;
	}

	@SuppressWarnings("unchecked")
	public void replaceValueWithMapping(Map<String, Object> dataMap, List<Map<String, Object>> columnInfos, String name) throws Exception {
		for (Map<String, Object> columnMap : columnInfos) {
			String displayName = name + "_$displayProperty";
			if (columnMap.containsKey(displayName)) {
				String propertyName = (String) columnMap.get(displayName);
				if (StringUtils.isNotEmpty(propertyName)) {
					Object data = dataMap.get(name);
					if (data != null) {
						dataMap.put(name, BeanUtils.getBeanProperty(data, propertyName));
					}
				}
			}
			Object obj = columnMap.get(name + "_$mapping");
			if (obj != null) {
				List<Map<Object, Object>> mappingList = (List<Map<Object, Object>>) obj;
				for (Map<Object, Object> mappingMap : mappingList) {
					Object mappingKey = mappingMap.get("key");
					if (mappingKey != null)
						mappingKey = mappingKey.toString();
					Object value = dataMap.get(name);
					if (value != null)
						value = value.toString();
					if (mappingKey.equals(value)) {
						dataMap.put(name, mappingMap.get("value"));
						break;
					}
				}
			} else {
				Object children = columnMap.get("children");
				if (children != null) {
					List<Map<String, Object>> childrenList = (List<Map<String, Object>>) children;
					replaceValueWithMapping(dataMap, childrenList, name);
				}
			}

		}
	}

	public List<Map<String, Object>> retrieveServerComplexPropertyData(List<Map<String, Object>> dataList, List<Map<String, Object>> columnInfos) throws Exception {
		for (Map<String, Object> columnMap : columnInfos) {
			String columnName = (String) columnMap.get("columnName");
			if (StringUtils.isNotEmpty(columnName)) {
				String[] columnNames = columnName.split("\\.");
				if (columnNames.length > 1) {
					int size = dataList.size();
					Map<String, Object> map;
					for (int i = 0; i < size; i++) {
						map = dataList.get(i);
						Object data = calculateBeanPropertyData(columnNames, map);
						map.put(columnName, data);
					}
				}
			}
		}
		return dataList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Object calculateBeanPropertyData(String[] columnNames, Map<String, Object> map) throws Exception {
		Object obj = map.get(columnNames[0]);
		if(obj==null){
			return null;
		}
		if (columnNames.length == 1) {
			return obj;
		} else if (columnNames.length == 2) {
			return BeanUtils.getBeanProperty(obj, columnNames[1]);
		} else {
			Map subMap = PropertyUtils.describe(obj);
			List<String> list = new ArrayList<String>();
			int i = 1;
			while (i < columnNames.length) {
				list.add(columnNames[i]);
				i++;
			}
			String[] array = list.toArray(new String[list.size()]);
			return calculateBeanPropertyData(array, subMap);
		}
	}

	@SuppressWarnings({ "unchecked" })
	public void createChildData(List<Map<String, Object>> dataList, List<Object> sub, String header, String treeColumn) {
		for (Object obj : sub) {
			Map<String, Object> tempMap = null;
			if (obj instanceof Map){
				tempMap = (Map<String, Object>)obj;
			} else {
				tempMap = new HashMap<String, Object>();
				try {
					EntityUtils.copyProperties(tempMap, obj);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			String tmp = header + "\t";
			if (tempMap.get(treeColumn) != null) {
				tempMap.put(treeColumn, tmp + tempMap.get(treeColumn));
			}
			dataList.add(tempMap);
			if (tempMap.get("children") != null) {
				this.createChildData(dataList, (List<Object>) tempMap.get("children"), tmp, treeColumn);
			}
		}
	}

	public ReportTitleStyle createTitleStyle(Map<String, Object> map) {
		ReportTitleStyle titleStyle = new ReportTitleStyle();
		int fontSize = (Integer) map.get("fontSize");
		String fontColor = (String) map.get("fontColor");
		String bgColor = (String) map.get("bgColor");
		titleStyle.setBgColor(this.createRGB(bgColor));
		titleStyle.setFontSize(fontSize);
		titleStyle.setFontColor(this.createRGB(fontColor));
		return titleStyle;
	}

	public int[] createRGB(String s) {
		if (StringUtils.isNotEmpty(s)) {
			return ColorUtils.parse2RGB(s);
		}
		return null;
	}

	public void fireGridDataInterceptor(String beanName, List<Map<String, Object>> list) throws Exception {
		fireControlDataInterceptor(beanName, SupportWidget.grid, list);
	}

	public void fireAutoFormDataInterceptor(String beanName, List<Map<String, Object>> list) throws Exception {
		fireControlDataInterceptor(beanName, SupportWidget.form, list);
	}

	private void fireControlDataInterceptor(String beanName, SupportWidget supportWidget, List<Map<String, Object>> list) throws Exception {
		if (StringUtils.isNotEmpty(beanName)) {
			Map<String, IDataInterceptor> map = DoradoContext.getCurrent().getApplicationContext().getBeansOfType(IDataInterceptor.class);
			for (Map.Entry<String, IDataInterceptor> entry : map.entrySet()) {
				if (entry.getValue().getName().equals(beanName)) {
					if (supportWidget.equals(SupportWidget.grid)) {
						entry.getValue().interceptGridData(list);
					} else if (supportWidget.equals(SupportWidget.form)) {
						entry.getValue().interceptAutoFormData(list);
					}
				}
			}
		}
	}

}
