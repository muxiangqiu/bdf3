package com.bstek.bdf3.importer.policy;


/**
 *@author Kevin.yang
 *@since 2015年8月22日
 */
public interface ExcelPolicy<T extends Context> {
	
	void apply(T context) throws Exception;
	
	T createContext();
	
	boolean support(String fileName);

}
