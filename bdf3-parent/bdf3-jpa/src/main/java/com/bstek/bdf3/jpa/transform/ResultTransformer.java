package com.bstek.bdf3.jpa.transform;
/**
 * 查询结果转换器
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年2月21日
 */
public interface ResultTransformer {

	public Object transformTuple(Object[] tuple, String[] aliases);
}
