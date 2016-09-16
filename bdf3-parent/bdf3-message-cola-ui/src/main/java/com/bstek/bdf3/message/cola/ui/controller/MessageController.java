package com.bstek.bdf3.message.cola.ui.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bstek.bdf3.message.cola.ui.service.AnnounceService;
import com.bstek.bdf3.message.domain.Notify;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年9月4日
 */

public class MessageController {
	
	@Autowired
	private AnnounceService announceService;
	
	@RequestMapping(path = "/announce", method = RequestMethod.GET)
	public String list() {
		return announceService.getListPage();
	}
	
	@RequestMapping(path = "/announce/publish", method = RequestMethod.GET)
	public String publish() {
		return announceService.getPublishPage();
	}
	
	@RequestMapping(path = "/announce/manage", method = RequestMethod.GET)
	public String manage() {
		return announceService.getManagePage();
	}
	
	@RequestMapping(path = "/announce/detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable String id, Authentication authentication, Model model) {
		UserDetails user = (UserDetails) authentication.getPrincipal();
		return announceService.getDetailPage(id, user.getUsername());
	}
	
	@RequestMapping(path = "/announce/modify/{id}", method = RequestMethod.GET)
	public String modify(@PathVariable String id, Authentication authentication, Model model) {
		UserDetails user = (UserDetails) authentication.getPrincipal();
		return announceService.getModifyPage(id, user.getUsername());
	}
	
	@RequestMapping(path = "/api/announce/detail/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Notify getDetail(@PathVariable String id) {
		return announceService.getDetail(id);
	}
	
	@RequestMapping(path = "/api/announce/load", method = RequestMethod.GET)
	@ResponseBody
	public List<Notify> load(Pageable pageable, String title) {
		return announceService.load(pageable, title);
	}
	
	@RequestMapping(path = "/api/announce/load-unread", method = RequestMethod.GET)
	@ResponseBody
	public List<Notify> loadUnread(Authentication authentication) {
		UserDetails user = (UserDetails) authentication.getPrincipal();
		return announceService.loadUnread(user.getUsername());
	}
	
	@RequestMapping(path = "/api/announce/add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@RequestBody Notify notify, Authentication authentication) {
		UserDetails user = (UserDetails) authentication.getPrincipal();
		notify.setSender(user.getUsername());
		notify.setCreatedAt(new Date());
		return announceService.add(notify);
	}
	
	@RequestMapping(path = "/api/announce/modify", method = RequestMethod.PUT)
	@ResponseBody
	public void modify(@RequestBody Notify notify) {
		announceService.modify(notify);
	}
	
	@RequestMapping(path = "/api/announce/remove/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void remove(@PathVariable String id) {
		announceService.remove(id);
	}

}
