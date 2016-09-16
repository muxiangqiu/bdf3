package com.bstek.bdf3.message.cola.ui.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.bstek.bdf3.message.domain.Notify;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年9月10日
 */
public interface AnnounceService {
	
	List<Notify> load(Pageable pageable, String title);
		
	String add(Notify notify);
	
	void modify(Notify notify);
	
	void remove(String id);

	List<Notify> loadUnread(String user);

	String getPublishPage();

	String getListPage();

	String getDetailPage(String id, String user);

	Notify getDetail(String id);

	String getManagePage();

	String getModifyPage(String id, String user);
	
	
}
