package com.bstek.bdf3.message.cola.ui.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf3.jpa.JpaUtil;
import com.bstek.bdf3.message.domain.Chat;
import com.bstek.bdf3.message.domain.Notify;
import com.bstek.bdf3.message.domain.NotifyType;
import com.bstek.bdf3.message.domain.UserNotify;
import com.bstek.bdf3.message.service.NotifyService;
import com.bstek.bdf3.security.user.SecurityUserUtil;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年9月10日
 */
@Service
@Transactional(readOnly = true)
public class MessageServiceImpl implements MessageService {

	@Autowired
	private NotifyService notifyService;
	
	@Override
	@Transactional
	public String getChatPage(String chatId, String receiver) {
		JpaUtil.linu(UserNotify.class)
			.set("read", true)
			.equal("user", receiver)
			.isFalse("read")
			.equal("group", chatId)
			.update();
		return "bdf3/message/chat";
	}
	
	@Override
	public String getListPage() {
		return "bdf3/message/list";
	}
	
	@Override
	public String getSendPage() {
		return "bdf3/message/send";
	}

	@Override
	public List<Chat> load(String user) {
		List<Chat> chats = JpaUtil.linq(Chat.class)
				.or()
					.equal("sender", user)
					.equal("receiver", user)
				.end()
				.desc("recentTime")
				.findAll();
		if (!chats.isEmpty()) {
			for (Chat chat : chats) {
				if (StringUtils.equals(user, chat.getSender())) {
					chat.setSender(chat.getReceiver());
					chat.setReceiver(user);
				}
			}
			List<Notify> notifies = 
					JpaUtil.linq(Notify.class)
						.in("id", JpaUtil.collect(chats, "recentNotifyId"))
						.findAll();
			Map<String, Notify> notifyMap = JpaUtil.index(notifies);
			
			List<UserDetails> users = 
					JpaUtil.linq(SecurityUserUtil.getSecurityUserType())
					.in(SecurityUserUtil.getUsernameProp(), JpaUtil.collect(chats, "sender"))
					.findAll();
			Map<String, UserDetails> userMap = JpaUtil.index(users);


			for (Chat chat : chats) {
				Map<String, Object> additional = new HashMap<String, Object>(2);
				additional.put("message", notifyMap.get(chat.getRecentNotifyId()));
				additional.put("sender", userMap.get(chat.getSender()));
				chat.setAdditional(additional);
			}
		}
		
		return chats;
	}

	@Override
	public List<Notify> loadUnread(String user) {
		List<Notify> result = new ArrayList<Notify>();
		List<Notify> notifies = JpaUtil.linq(Notify.class)
				.equal("type", NotifyType.Message)
				.in(UserNotify.class)
					.select("notifyId")
					.equal("user", user)
					.isFalse("read")
				.end()
				.desc("createdAt")
				.findAll();
		if (!notifies.isEmpty()) {
			Map<String, List<Notify>> senderMap = JpaUtil.classify(notifies, "sender");

			List<UserDetails> users = JpaUtil.linq(SecurityUserUtil.getSecurityUserType()).in(SecurityUserUtil.getUsernameProp(), senderMap.keySet()).findAll();
			Map<String, UserDetails> userMap = JpaUtil.index(users);
			for (Entry<String, List<Notify>> entry : senderMap.entrySet()) {
				Notify notify = entry.getValue().get(0);
				Map<String, Object> additional = new HashMap<String, Object>(2);
				additional.put("sender", userMap.get(notify.getSender()));
				additional.put("count", entry.getValue().size());
				notify.setAdditional(additional);
				result.add(notify);
			}
		}
		return result;
	}

	@Override
	@Transactional
	public String add(String content, String sender, String receiver) {
		return notifyService.createMessage(content, sender, receiver).getId();
	}

	@Override
	@Transactional
	public void remove(String id) {
		Notify notify = JpaUtil.getOne(Notify.class, id);
		JpaUtil.remove(notify);
		JpaUtil.lind(UserNotify.class).equal("notifyId", id).delete();
	}
	
	@Override
	@Transactional
	public void removeChat(String id) {
		Chat chat = JpaUtil.getOne(Chat.class, id);
		JpaUtil.remove(chat);
		JpaUtil
			.lind(UserNotify.class)
			.equal("group", id)
			.delete();
		JpaUtil
			.lind(Notify.class)
			.equal("group", id)
			.delete();
	}

	@Override
	public void clearChat(String user) {
		List<String> chatIds = JpaUtil
				.linq(Chat.class, String.class)
				.select("id")
				.or()
					.equal("sender", user)
					.equal("receiver", user)
				.findAll();
		if (!chatIds.isEmpty()) {
			JpaUtil
				.lind(UserNotify.class)
				.in("group", chatIds)
				.delete();
			JpaUtil
				.lind(Notify.class)
				.in("group", chatIds)
				.delete();
			JpaUtil
				.lind(Chat.class)
				.in("id", chatIds)
				.delete();
		}
		
	}
	
	@Override
	public Chat getChat(String chatId, UserDetails user) {
		Chat chat = JpaUtil.getOne(Chat.class, chatId);
		String otherUsername = chat.getSender();
		if (StringUtils.equals(chat.getSender(), user.getUsername())) {
			otherUsername = chat.getReceiver();
			chat.setSender(otherUsername);
			chat.setReceiver(user.getUsername());
		}
		UserDetails otherUser = JpaUtil.getOne(SecurityUserUtil.getSecurityUserType(), otherUsername);
		List<Notify> notities = JpaUtil
				.linq(Notify.class)
				.equal("group", chat.getId())
				.asc("createdAt")
				.findAll();
		Map<String, Object> additional = new HashMap<String, Object>();
		additional.put("sender", otherUser);
		additional.put("receiver", user);
		additional.put("messages", notities);
		chat.setAdditional(additional);
		
		return chat;
	}

	
}
