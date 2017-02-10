package com.bstek.bdf3.dbconsole.manager.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.bstek.bdf3.dbconsole.model.DbInfo;
import com.bstek.dorado.util.TempFileUtils;

/**
 * 提供读取dbconsole.xml 配置文件
 * 
 */
public class DbInfoConfig {
	private static String FILE_PATH;
	private static final String FILE_NAME = "bdf3-dbconsole.xml";
	protected static final Logger log = Logger.getLogger(DbInfoConfig.class);
	
	static {
		try {
			FILE_PATH = TempFileUtils.getTempDir().getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取用户目录下的数据库配置文件信息
	 * 
	 * @return 返回DbInfo的集合
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Vector<DbInfo> readConfig() {
		Vector<DbInfo> listDbInfo = new Vector<DbInfo>();
		SAXReader reader = new SAXReader();
		File file = new File(FILE_PATH, FILE_NAME);
		if (file.exists()) {
			Document document = null;
			try {
				document = reader.read(file);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			Element root = document.getRootElement();
			List elements = root.elements("dbinfo");
			DbInfo dbInfo = null;
			for (Iterator it = elements.iterator(); it.hasNext();) {
				Element element = (Element) it.next();
				dbInfo = new DbInfo();
				String dbInfoId = element.attributeValue("id");
				dbInfo.setId(dbInfoId);
				dbInfo.setName(element.elementText("name"));
				dbInfo.setCreateUser(element.elementText("createUser"));
				dbInfo.setDbType(element.elementText("dbType"));
				dbInfo.setDriverClass(element.elementText("driverClass"));
				dbInfo.setUrl(element.elementText("url"));
				dbInfo.setUsername(element.elementText("username"));
				dbInfo.setPassword(element.elementText("password"));
				listDbInfo.add(dbInfo);
			}
		}

		return listDbInfo;
	}

	/**
	 * 对数据库文件信息写入用户目录
	 * 
	 * @param dbInfos
	 * @throws Exception
	 */
	public static void writeConfig(Vector<DbInfo> dbInfos) {
		XMLWriter writer = null;
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("gbk");
		File file = new File(FILE_PATH, FILE_NAME);
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("dbconfig");
		for (DbInfo dbInfo : dbInfos) {
			log.debug("dbinfo name:" + dbInfo.getName());
			Element dbinfo = root.addElement("dbinfo");
			dbinfo.addAttribute("id", dbInfo.getId());
			Element name = dbinfo.addElement("name");
			name.setText(dbInfo.getName());
			Element createUser = dbinfo.addElement("createUser");
			createUser.setText(dbInfo.getCreateUser());
			Element dbType = dbinfo.addElement("dbType");
			dbType.setText(dbInfo.getDbType());
			Element driverClass = dbinfo.addElement("driverClass");
			driverClass.setText(dbInfo.getDriverClass());
			Element url = dbinfo.addElement("url");
			url.setText(dbInfo.getUrl());
			Element userName = dbinfo.addElement("username");
			userName.setText(dbInfo.getUsername());
			Element password = dbinfo.addElement("password");
			password.setText(dbInfo.getPassword() != null ? dbInfo.getPassword() : "");
		}
		try {
			writer = new XMLWriter(new FileWriter(file.getPath()), format);
			writer.write(document);
			log.debug("create dbconfig success,filepath: " + file.getPath());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
