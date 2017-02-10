package com.bstek.bdf3.export.extension;

import java.io.OutputStream;

import com.bstek.bdf3.export.model.ReportGrid;

/**
 * 自定义生成报表接口
 * 
 */
public interface ReportBuilder {

	/**
	 * 生成报表
	 * 
	 * @param out
	 *            输出流
	 * @param reportGrid
	 *            grid数据模型
	 * @throws Exception
	 */
	public void execute(OutputStream out, ReportGrid reportGrid) throws Exception;

	/**
	 * 是否支持当前的文件类型
	 * 
	 * @param extension
	 *            文件类型
	 * @return
	 */
	public boolean support(String extension);

}
