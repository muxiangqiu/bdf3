package com.bstek.bdf3.notice.strategy;

import com.bstek.bdf3.notice.domain.Notice;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年9月13日
 */
public interface ReceiverIterator {
		
	void each(Notice notice, MemberProcessor memberProcessor);
	
	boolean support(Notice notice);

}
