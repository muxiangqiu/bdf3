package com.bstek.bdf3.message.service;

import java.util.List;

import com.bstek.bdf3.message.domain.Notify;
import com.bstek.bdf3.message.domain.SubscriptionConfig;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年9月6日
 */
public interface NotifyService {

	void createAnnounce(String content, String sender);
	
	void createRemind(String target, String targetType, String action, String sender, String content);
	
	void createMessage(String content, String sender, String receiver);
	
	List<Notify> pullAnnounce(String user);
	
	List<Notify> pullRemind(String user);
	
	void subscribe(String user, String target, String targetType, String reason);
	
	void cancelSubscription(String user, String target ,String targetType);
	
	List<SubscriptionConfig> getSubscriptionConfigs(String user);
		
	List<Notify> getUserNotify(String user);
	
	void read(String user, List<String> notifyIds);

	void removeSubscriptionConfig(String subscriptionConfigId);

	void addSubscriptionConfig(SubscriptionConfig subscriptionConfig);

	Long getUserNotifyCount(String user);
}
