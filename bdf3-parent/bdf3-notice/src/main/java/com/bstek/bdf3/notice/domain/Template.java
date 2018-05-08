package com.bstek.bdf3.notice.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * @author Kevin Yang (mailto:muxiangqiu@gmail.com)
 * @since 2018年3月26日
 */
@Entity
@Table(name = "BDF3_TEMPLATE")
public class Template {
	
	@Id
	@Column(name = "ID_", length = 64)
	private String id;
	
	@Column(name = "NAME_")
	private String name;
	
	@Column(name = "ICON_")
	private String icon;
	
	@Column(name = "URL_", length = 512)
	private String url;
	
	@Lob
	@Column(name = "CSS_")
	private String css;
	
	@Lob
	@Column(name = "JAVASCRIPT_")
	private String javascript;
	
	@Lob
	@Column(name = "HTML_")
	private String html;
	
	@Column(name = "GLOBAL_")
	private boolean global;
	
	@Column(name = "OFFLINE_")
	private boolean offline;
	
	@Column(name = "MICRO_PROGRAM_")
	private boolean microProgram;
	
	@Column(name = "DISPLAYABLE_")
	private boolean displayable;
	
	@Column(name = "DESCRIPTION_")
	private String description;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public String getJavascript() {
		return javascript;
	}

	public void setJavascript(String javascript) {
		this.javascript = javascript;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public boolean isGlobal() {
		return global;
	}

	public void setGlobal(boolean global) {
		this.global = global;
	}

	public boolean isOffline() {
		return offline;
	}

	public void setOffline(boolean offline) {
		this.offline = offline;
	}

	public boolean isMicroProgram() {
		return microProgram;
	}

	public void setMicroProgram(boolean microProgram) {
		this.microProgram = microProgram;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isDisplayable() {
		return displayable;
	}

	public void setDisplayable(boolean displayable) {
		this.displayable = displayable;
	}
	
	
	
	
	
	
	

}
