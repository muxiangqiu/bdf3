package com.bstek.bdf3.security.access.vote;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * 默认访问决策管理器
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年7月7日
 */
@Component
public class AccessDecisionManagerImpl extends AbstractAccessDecisionManager {

	@Value("${bdf3.allowIfAllAbstainDecisions:true}")
	private boolean allowIfAllAbstainDecisions;
	
	private List<AccessDecisionVoter<? extends Object>> decisionVoters;

	
	@Autowired
	public AccessDecisionManagerImpl(
			List<AccessDecisionVoter<? extends Object>> decisionVoters) {
		super(decisionVoters);
		this.decisionVoters = decisionVoters;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void decide(Authentication authentication, Object object,
			Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		int deny = 0;
		for (AccessDecisionVoter voter : getDecisionVoters()) {
			if (voter.supports(object.getClass())) {
				int result = voter.vote(authentication, object, configAttributes);
				if (logger.isDebugEnabled()) {
					logger.debug("Voter: " + voter + ", returned: " + result);
				}
				switch (result) {
				case AccessDecisionVoter.ACCESS_GRANTED:
					return;
				case AccessDecisionVoter.ACCESS_DENIED:
					deny++;
					break;
				default:
					break;
				}
			}
		}

		if (deny > 0) {
			throw new AccessDeniedException(messages.getMessage(
					"AbstractAccessDecisionManager.accessDenied", "Access is denied"));
		}

		setAllowIfAllAbstainDecisions(allowIfAllAbstainDecisions);
		checkAllowIfAllAbstainDecisions();

	}
	
	public boolean supports(Class<?> clazz) {
		for (AccessDecisionVoter<? extends Object> voter : this.decisionVoters) {
			if (voter.supports(clazz)) {
				return true;
			}
		}

		return false;
	}


}
