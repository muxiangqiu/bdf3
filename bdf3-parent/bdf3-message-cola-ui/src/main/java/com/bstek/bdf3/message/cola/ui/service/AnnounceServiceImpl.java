package com.bstek.bdf3.message.cola.ui.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf3.jpa.JpaUtil;
import com.bstek.bdf3.message.domain.Notify;
import com.bstek.bdf3.message.domain.NotifyType;
import com.bstek.bdf3.message.domain.UserNotify;
import com.bstek.bdf3.message.service.NotifyService;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年9月10日
 */
@Service
@Transactional(readOnly = true)
public class AnnounceServiceImpl implements AnnounceService {

	@Autowired
	private NotifyService notifyService;
	
	@Override
	public String getPublishPage() {
		return "bdf3/announce/publish";
	}
	
	@Override
	public String getManagePage() {
		return "bdf3/announce/manage";
	}
	
	@Override
	@Transactional
	public String getDetailPage(String id, String user) {
		JpaUtil.linu(UserNotify.class)
			.set("read", true)
			.equal("notifyId", id)
			.equal("user", user)
			.update();
		return "bdf3/announce/detail";
	}
	
	@Override
	public String getModifyPage(String id, String user) {
		return "bdf3/announce/modify";
	}
	
	@Override
	public String getListPage() {
		return "bdf3/announce/list";
	}

	@Override
	public List<Notify> load(Pageable pageable, String title) {
		return JpaUtil.linq(Notify.class)
				.equal("type", NotifyType.Announce)
				.addIf(title)
					.like("title", "%" + title + "%")
				.endIf()
				.desc("createdAt")
				.list(pageable);
	}

	@Override
	@Transactional
	public List<Notify> loadUnread(String user) {
		notifyService.pullAnnounce(user);
		return JpaUtil.linq(Notify.class)
				.equal("type", NotifyType.Announce)
				.in(UserNotify.class)
					.select("notifyId")
					.equal("user", user)
					.isFalse("read")
				.end()
				.desc("createdAt")
				.findAll();
	}

	@Override
	@Transactional
	public String add(Notify notify) {
		notify.setId(UUID.randomUUID().toString());
		notify.setType(NotifyType.Announce);
		JpaUtil.persist(notify);
		return notify.getId();
	}

	@Override
	@Transactional
	public void modify(Notify notify) {
		JpaUtil.merge(notify);
		
	}

	@Override
	@Transactional
	public void remove(String id) {
		Notify notify = JpaUtil.getOne(Notify.class, id);
		JpaUtil.remove(notify);
		JpaUtil.lind(UserNotify.class).equal("notifyId", id).delete();
	}

	@Override
	public Notify getDetail(String id) {
		return JpaUtil.getOne(Notify.class, id);
	}

	
}
