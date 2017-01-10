package com.bstek.bdf3.importer.controller;

import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import com.bstek.bdf3.importer.policy.Context;
import com.bstek.bdf3.importer.policy.ExcelPolicy;
import com.bstek.dorado.uploader.UploadFile;
import com.bstek.dorado.uploader.annotation.FileResolver;
@SuppressWarnings({"rawtypes", "unchecked"})
public class ExcelUploadFileProcessor implements ApplicationContextAware{

	
	private Collection<ExcelPolicy> excelPolicies;
	
	@FileResolver
	public String upload(UploadFile file, Map<String, Object> parameter) throws Exception {
		MultipartFile multipartFile = file.getMultipartFile();
		String name = multipartFile.getOriginalFilename();
		String importerSolutionId = (String) parameter.get("importerSolutionId");
		int startRow = 1;
		if (parameter.get("startRow") != null) {
			startRow = Integer.parseInt(parameter.get("startRow").toString());
		}
		Assert.hasLength(importerSolutionId, "Excel导入方案编码必须配置。");
		
		InputStream inpuStream = multipartFile.getInputStream();
		try {			
			for (ExcelPolicy excelPolicy : excelPolicies) {
				if (excelPolicy.support(name)) {
					Context context = excelPolicy.createContext();
					context.setInpuStream(inpuStream);
					context.setStartRow(startRow);
					context.setFileName(name);
					context.setFileSize(file.getSize());
					context.setImporterSolutionId(importerSolutionId);
					excelPolicy.apply(context);
					break;
				}
			}
		} finally {
			IOUtils.closeQuietly(inpuStream);
		}
		return null;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		excelPolicies = applicationContext.getBeansOfType(ExcelPolicy.class).values();
	}
}