package com.bstek.bdf3.notice.ui.strategy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.bstek.bdf3.notice.domain.Notice;
import com.bstek.bdf3.notice.strategy.MemberProcessor;
import com.bstek.bdf3.notice.strategy.ReceiverIterator;
import com.bstek.bdf3.notice.ui.service.GroupService;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年9月13日
 */
@Order
@Component
public class ReceiverIteratorImpl implements ReceiverIterator {

	@Autowired
	private GroupService groupService;

	@Override
	public void each(Notice notice, MemberProcessor memberProcessor) {
		List<String> memberIds = groupService.getMemberIds(notice.getGroupId());
		for (String memberId : memberIds) {
			memberProcessor.process(memberId);
		}
	}

	@Override
	public boolean support(Notice notice) {
		return MessageReceiveSrategy.NOTICE_TYPE.equals(notice.getType());
	}
	
	

}
