package com.bstek.bdf3.notice.strategy;
/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年9月13日
 */
@FunctionalInterface
public interface MemberProcessor {
	
	void process(String memberId);
}
