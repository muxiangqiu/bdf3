package com.bstek.bdf3.notice.ui.strategy;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.bstek.bdf3.notice.domain.GroupMember;
import com.bstek.bdf3.notice.domain.Notice;
import com.bstek.bdf3.notice.strategy.NoticeTransform;
import com.bstek.dorado.view.socket.Message;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年9月13日
 */
@Component
public class NoticeTransformImpl implements NoticeTransform<Message> {

	@SuppressWarnings("unchecked")
	@Override
	public Notice transform(Message message) {
		Map<String, Object> data = (Map<String, Object>) message.getData();
		Notice notice = new Notice();
		if (data != null) {
			notice.setType((String) data.get("type"));
			notice.setContent((String) data.get("content"));
			notice.setTitle((String) data.get("title"));
			notice.setSender((String) data.get("sender"));
			notice.setGroupId((String) data.get("groupId"));
			notice.setTemplateId((String) data.get("templateId"));
			if (data.get("all") != null) {
				notice.setAll((Boolean) data.get("all"));
			}
			Map<String, Object> senderGroupMemberMap = (Map<String, Object>) data.get("senderGroupMember");
			if (senderGroupMemberMap != null) {
				GroupMember senderGroupMember = new GroupMember();
				senderGroupMember.setGroupId((String) senderGroupMemberMap.get("groupId"));
				senderGroupMember.setMemberId((String) senderGroupMemberMap.get("memberId"));
				senderGroupMember.setNickname((String) senderGroupMemberMap.get("nickname"));
				notice.setSenderGroupMember(senderGroupMember);
			}
		}
		if (notice.getType() == null) {
			notice.setType(message.getType());
		}
		notice.setId(UUID.randomUUID().toString());
		notice.setSendTime(new Date());
		return notice;
	}

}
