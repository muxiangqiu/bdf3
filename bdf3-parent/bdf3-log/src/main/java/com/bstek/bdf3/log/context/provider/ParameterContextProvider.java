package com.bstek.bdf3.log.context.provider;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

import com.bstek.bdf3.log.annotation.LogDefinition;

/**
 *@author Kevin.yang
 *@since 2015年7月20日
 */
@Component
public class ParameterContextProvider extends AbstractContextProvider {

	private static final Pattern PATTERN = Pattern.compile("^arg\\(([ha0-9]|[1-9][0-9]+)\\)$");
	@Override
	public Object getContext() {
		JoinPoint joinPoint = (JoinPoint) contextHandler.get(JOIN_POINT);
		LogDefinition logDefinition = (LogDefinition) contextHandler.get(LOG_DEFINITION);
		Matcher matcher = PATTERN.matcher(logDefinition.getContext());
		int index = 0;
		if (matcher.find()) {
			index = Integer.parseInt(matcher.group(1));
		}
		return getRealContext(joinPoint.getArgs()[index], logDefinition.getDataPath());
	}

	@Override
	public boolean support(LogDefinition logDefinition) {
		return PATTERN.matcher(logDefinition.getContext()).find();
	}

}
