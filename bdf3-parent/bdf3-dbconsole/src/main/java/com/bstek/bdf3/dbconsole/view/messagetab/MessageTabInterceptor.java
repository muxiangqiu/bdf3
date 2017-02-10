package com.bstek.bdf3.dbconsole.view.messagetab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import com.bstek.bdf3.dbconsole.service.DbService;
import com.bstek.dorado.view.View;
import com.bstek.dorado.view.widget.HtmlContainer;
import com.bstek.dorado.web.DoradoContext;

@Controller
public class MessageTabInterceptor {
	
	@Autowired
	@Qualifier(DbService.BEAN_ID)
	private DbService dbService;

	public void onInit(View view) throws Exception {
		HtmlContainer htmlContainer = (HtmlContainer) view.getViewElement("htmlContainerMessage");
		String dbInfoId = DoradoContext.getAttachedRequest().getParameter("dbInfoId");
		String sql = DoradoContext.getAttachedRequest().getParameter("sql");
		if (StringUtils.isEmpty(sql)) {
			htmlContainer.setContent("No Results");
			return;
		}
		String[] sqls = sql.split(";");
		int[] rs = null;
		long startTime = 0;
		long endTime = 0;
		try {
			startTime = System.nanoTime();
			rs = dbService.updateSql(dbInfoId, sqls);
			endTime = System.nanoTime();
		} catch (Exception e) {
			htmlContainer.setContent(this.formatString(e.getClass().toString() + " : " + e.getMessage()));
			e.printStackTrace();
		}
		if (!StringUtils.hasText(htmlContainer.getContent())) {
			int j = 0;
			for (int s : rs) {
				if (s == 1) {
					j++;
				}
			}
			long time = (endTime - startTime) / 1000000L;
			htmlContainer.setContent(this.formatString("Record:" + j + ",Time:" + time + "ms"));
		}
	}

	private String formatString(String s) {
		return "System Message  :<font color=\"red\">" + s + "</font>";
	}

}
