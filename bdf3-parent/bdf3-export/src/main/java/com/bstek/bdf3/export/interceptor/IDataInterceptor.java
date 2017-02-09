package com.bstek.bdf3.export.interceptor;

import java.util.List;
import java.util.Map;

public interface IDataInterceptor {

	public String getName();

	public String getDesc();

	public void interceptGridData(List<Map<String, Object>> list) throws Exception;

	public void interceptAutoFormData(List<Map<String, Object>> list) throws Exception;

}
