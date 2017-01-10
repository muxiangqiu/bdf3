package com.bstek.bdf3.log.view;



import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf3.dorado.jpa.JpaUtil;
import com.bstek.bdf3.log.model.LogInfo;
import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;



/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年3月6日
 */
@Controller
public class LogInfoController {
	
	@DataProvider
	@Transactional(readOnly = true)
	public void load(Page<LogInfo> page, Criteria criteria) {
		JpaUtil.linq(LogInfo.class).where(criteria).paging(page);
	}
	

}
