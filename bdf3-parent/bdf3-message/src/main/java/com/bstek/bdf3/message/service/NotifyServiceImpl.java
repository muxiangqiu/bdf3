package com.bstek.bdf3.message.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bstek.bdf3.jpa.JpaUtil;
import com.bstek.bdf3.message.domain.Chat;
import com.bstek.bdf3.message.domain.Notify;
import com.bstek.bdf3.message.domain.NotifyConfig;
import com.bstek.bdf3.message.domain.NotifyType;
import com.bstek.bdf3.message.domain.Subscription;
import com.bstek.bdf3.message.domain.SubscriptionConfig;
import com.bstek.bdf3.message.domain.UserNotify;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年9月6日
 */
@Service
public class NotifyServiceImpl implements NotifyService {
	
	@Value("${bdf3.message.subscriptions:}")
	private String subscriptions;
	

	@Override
	public Notify createAnnounce(String content, String sender) {
		Notify notify = new Notify();
		notify.setContent(content);
		notify.setId(UUID.randomUUID().toString());
		notify.setSender(sender);
		notify.setType(NotifyType.Announce);
		notify.setCreatedAt(new Date());
		return JpaUtil.persist(notify);
		
	}

	@Override
	public Notify createRemind(String target, String targetType, String action,
			String sender, String content) {
		Notify notify = new Notify();
		notify.setContent(content);
		notify.setId(UUID.randomUUID().toString());
		notify.setSender(sender);
		notify.setType(NotifyType.Remind);
		notify.setTarget(target);
		notify.setTargetType(targetType);
		notify.setAction(action);
		notify.setCreatedAt(new Date());
		return JpaUtil.persist(notify);
	}

	@Override
	public Notify createMessage(String content, String sender, String receiver) {
		Notify notify = new Notify();
		notify.setContent(content);
		notify.setId(UUID.randomUUID().toString());
		notify.setSender(sender);
		notify.setType(NotifyType.Message);
		notify.setCreatedAt(new Date());
		
		UserNotify userNotify = new UserNotify();
		userNotify.setId(UUID.randomUUID().toString());
		userNotify.setNotifyId(notify.getId());
		userNotify.setUser(receiver);
		userNotify.setCreatedAt(notify.getCreatedAt());
		
		Chat chat = null;
		List<Chat> chats = JpaUtil
				.linq(Chat.class)
				.or()
					.and()
						.equal("sender", sender)
						.equal("receiver", receiver)
					.end()
					.and()
						.equal("sender", receiver)
						.equal("receiver", sender)
					.end()
				.findAll();
			if (chats.isEmpty()) {
				chat = new Chat();
				chat.setId(UUID.randomUUID().toString());
				chat.setRecentNotifyId(notify.getId());
				chat.setReceiver(receiver);
				chat.setSender(sender);
				chat.setRecentTime(notify.getCreatedAt());
				JpaUtil.persist(chat);
			} else {
				chat = chats.get(0);
				chat.setRecentNotifyId(notify.getId());
				chat.setReceiver(receiver);
				chat.setSender(sender);
				chat.setRecentTime(notify.getCreatedAt());
				JpaUtil.merge(chat);
			}
		notify.setGroup(chat.getId());
		userNotify.setGroup(chat.getId());
		JpaUtil.persist(userNotify);

		return JpaUtil.persist(notify);
	}

	@Override
	public List<Notify> pullAnnounce(String user) {
		UserNotify un = null;
		try {
			un = JpaUtil
				.linq(UserNotify.class)
				.equal("user", user)
				.notExists(UserNotify.class)
					.equal("user", user)
					.greaterThanProperty("createdAt", "createdAt")
				.end()
				.exists(Notify.class)
					.equal("type", NotifyType.Announce)
					.equalProperty("id", "notifyId")
				.end()
				.findOne();
		} catch (NoResultException e) {
		}
		
		Date lastTime = un != null ? un.getCreatedAt() : null;
		
		List<Notify> notifies = JpaUtil
				.linq(Notify.class)
				.equal("type", NotifyType.Announce)
				.addIf(lastTime)
					.greaterThan("createdAt", lastTime)
				.endIf()
				.findAll();
		
		for (Notify notify : notifies) {
			UserNotify userNotify = new UserNotify();
			userNotify.setId(UUID.randomUUID().toString());
			userNotify.setNotifyId(notify.getId());
			userNotify.setUser(user);
			userNotify.setCreatedAt(new Date());
			JpaUtil.persist(userNotify);
			notify.setUserNotify(userNotify);
		}
		return notifies;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Notify> pullRemind(String user) {
		List<String> actions = JpaUtil
				.linq(SubscriptionConfig.class, String.class)
				.select("action")
				.equal("user", user)
				.findAll();
		if (!StringUtils.isEmpty(subscriptions) && actions.isEmpty()) {
			actions.addAll(Arrays.asList(subscriptions.split(",")));
		}
	
		if (!actions.isEmpty()) {
			List<Notify> notifies = JpaUtil
				.linq(Notify.class)
				.equal("type", NotifyType.Remind)
				.in("action", actions)
				.exists(Subscription.class)
					.equal("user", user)
					.equal("target", "target")
					.equal("targetType", "targetType")
					.equal("action", "action")
					.lessThanProperty("createdAt", "createdAt")
				.findAll();
			for (Notify notify : notifies) {
				UserNotify userNotify = new UserNotify();
				userNotify.setId(UUID.randomUUID().toString());
				userNotify.setNotifyId(notify.getId());
				userNotify.setUser(user);
				userNotify.setCreatedAt(new Date());
				JpaUtil.persist(userNotify);
				notify.setUserNotify(userNotify);
			}
			return notifies;
		}
		
		return Collections.EMPTY_LIST;
	}

	@Override
	public void subscribe(String user, String target, String targetType,
			String reason) {
		List<String> actions = JpaUtil
				.linq(NotifyConfig.class, String.class)
				.select("action")
				.equal("reason", reason)
				.findAll();
		Date now = new Date();
		for (String action : actions) {
			Subscription subscription = new Subscription();
			subscription.setId(UUID.randomUUID().toString());
			subscription.setAction(action);
			subscription.setCreatedAt(now);
			subscription.setTarget(target);
			subscription.setTargetType(targetType);
			subscription.setUser(user);
			JpaUtil.persist(subscription);
		}
	}

	@Override
	public void cancelSubscription(String user, String target, String targetType) {
		JpaUtil.lind(Subscription.class)
			.equal("user", user)
			.equal("target", target)
			.equal("targetType", targetType)
			.delete();
	}

	@Override
	public List<SubscriptionConfig> getSubscriptionConfigs(String user) {
		return JpaUtil.linq(SubscriptionConfig.class, String.class)
				.equal("user", user)
				.findAll();
	}

	@Override
	public void addSubscriptionConfig(SubscriptionConfig subscriptionConfig) {
		subscriptionConfig.setId(UUID.randomUUID().toString());;
		JpaUtil.persist(subscriptionConfig);
	}
	
	@Override
	public void removeSubscriptionConfig(String subscriptionConfigId) {
		SubscriptionConfig config = JpaUtil.getOne(SubscriptionConfig.class, subscriptionConfigId);
		JpaUtil.remove(config);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Notify> getUserNotify(String user) {
		List<UserNotify> userNotifies = JpaUtil.linq(UserNotify.class)
			.equal("user", user)
			.isFalse("read")
			.findAll();
		if (!userNotifies.isEmpty()) {
			Set<String> notifyIds = JpaUtil.collect(userNotifies, "notifyId");
			Map<String, UserNotify> userNotifyMap = JpaUtil.index(userNotifies, "notifyId");

			List<Notify> notifies = JpaUtil.linq(Notify.class)
					.in("id", notifyIds)
					.findAll();
			for (Notify notify : notifies) {
				notify.setUserNotify(userNotifyMap.get(notify.getId()));
			}
			return notifies;
		}
		
		return Collections.EMPTY_LIST;
	}
	
	@Override
	public Long getUserNotifyCount(String user) {
		return JpaUtil.linq(UserNotify.class)
			.equal("user", user)
			.isFalse("read")
			.count();
	}

	@Override
	public void read(String user, List<String> notifyIds) {
		JpaUtil.linu(UserNotify.class)
			.in("notifyIds", notifyIds)
			.set("read", true)
			.update();
	}

}
