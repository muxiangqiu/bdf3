package com.bstek.bdf3.export.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.bstek.bdf3.export.utils.ExportUtils;
import com.bstek.dorado.core.Configure;
import com.bstek.dorado.web.resolver.AbstractResolver;

public class DownloadReportResolver extends AbstractResolver {

	public String extensionFileType = Configure.getString("bdf3.export.extension.fileType");

	@Override
	protected ModelAndView doHandleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		doDownloadReport(request, response);
		return null;
	}

	private void doDownloadReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String loginUserSessionKey = Configure.getString("bdf3.export.loginUserSessionKey");
		if (StringUtils.isNotEmpty(loginUserSessionKey)) {
			Object key = request.getSession().getAttribute(loginUserSessionKey);
			if (key == null) {
				this.processError(request, response, "request error,please login!");
				return;
			}
		}
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		if (!StringUtils.isNotEmpty(id) || !id.matches("[a-z0-9-]+")) {
			this.processError(request, response, "request parameter id error");
			return;
		}
		String type = "";
		if (StringUtils.isNotEmpty(extensionFileType)) {
			type = "|" + extensionFileType;
		}
		if (!StringUtils.isNotEmpty(name) || !name.matches("[^/\\\\?<>*:\"|]+(\\.(xls|xlsx|pdf|csv" + type + "))$")) {
			this.processError(request, response, "request parameter name error");
			return;
		}
		String fileName = id + "_" + name;
		String location = ExportUtils.getFileStorePath();
		File file = new File(location, fileName);
		if (!file.exists() || !file.isFile()) {
			this.processError(request, response, "find not found " + file);
			return;
		}
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Server", "http://www.bstek.com");
		response.setContentType("application/octet-stream;charset=utf-8");
		response.setHeader("Connection", "close");
		response.setHeader("Accept-Ranges", "bytes");
		name = URLEncoder.encode(name, "UTF-8");
		name = name.replaceAll("\\+", "%20");
		response.setHeader("Content-Disposition", "attachment;filename=\"" + name + "\";filename*=utf-8''" + name + "");
		FileInputStream input = null;
		OutputStream out = null;
		try {
			input = new FileInputStream(file);
			out = response.getOutputStream();
			IOUtils.copy(input, out);
			out.flush();
		} finally {
			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(out);
			if (file != null) {
				deletePreviousDayTempFile();
			}
		}
	}

	private void deletePreviousDayTempFile() throws IOException {
		String location = ExportUtils.getFileStorePath();
		File file = new File(location);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File f : files) {
				long time = f.lastModified();
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(time);
				Calendar last = Calendar.getInstance();
				last.add(Calendar.DAY_OF_MONTH, -1);
				if (cal.before(last)) {
					f.delete();
				}
			}
		}
	}

	private void processError(HttpServletRequest request, HttpServletResponse response, String message) throws IOException {
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.write(message);
		out.flush();
		out.close();
	}

}
