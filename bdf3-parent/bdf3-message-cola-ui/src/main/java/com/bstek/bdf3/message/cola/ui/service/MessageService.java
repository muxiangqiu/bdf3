package com.bstek.bdf3.message.cola.ui.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.bstek.bdf3.message.domain.Chat;
import com.bstek.bdf3.message.domain.Notify;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年9月10日
 */
public interface MessageService {
	
	List<Chat> load(String user);
				
	void remove(String id);

	List<Notify> loadUnread(String user);

	String getListPage();

	String add(String content, String sender, String receiver);
	
	String getSendPage();

	String getChatPage(String chatId, String username);

	void removeChat(String id);

	void clearChat(String user);

	Chat getChat(String chatId, UserDetails user);
	
	
}
