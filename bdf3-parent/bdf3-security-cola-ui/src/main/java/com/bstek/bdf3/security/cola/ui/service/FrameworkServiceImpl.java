package com.bstek.bdf3.security.cola.ui.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.bstek.bdf3.message.domain.Notify;
import com.bstek.bdf3.message.service.NotifyService;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月15日
 */
@Service
public class FrameworkServiceImpl implements FrameworkService {
	
	@Autowired
	private NotifyService notifyService;
	
	@Value("${bdf3.security.loginSuccessPath:main}")
	private String loginSuccessPath;
	
	@Override
	public String getHomePage() {
		return "redirect:" + loginSuccessPath;
	}

	@Override
	public String getLoginPage() {
		return "frame/login";
	}

	@Override
	public String getMainPage() {
		return "frame/main";
	}

	@Override
	public String getUserPage() {
		return "bdf3/user";
	}

	@Override
	public String getUrlPage() {
		return "bdf3/url";
	}

	@Override
	public String getRolePage() {
		return "bdf3/role";
	}

	@Override
	public String getComponentPage() {
		return "bdf3/component";
	}

	@Override
	public UserDetails getLoginUserInfo() {
		return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
	}

	@Override
	public Long getMessageTotal(String username) {
		notifyService.pullAnnounce(username);
		notifyService.pullRemind(username);
		return notifyService.getUserNotifyCount(username);
	}

	@Override
	public List<Notify> getMessages(String username) {
		return notifyService.getUserNotify(username);
	}

}
