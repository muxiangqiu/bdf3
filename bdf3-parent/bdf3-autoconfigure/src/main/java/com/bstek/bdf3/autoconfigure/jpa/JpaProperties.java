/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bstek.bdf3.autoconfigure.jpa;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 预制七个Jpa实体类包扫描属性，如果包含多个包，用逗号分割
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年1月24日
 */
@ConfigurationProperties(prefix = "bdf3.jpa")
public class JpaProperties {

	private String packagesToScan;
	
	private String packagesToScan1 = "NONE";
	
	private String packagesToScan2 = "NONE";
	
	private String packagesToScan3 = "NONE";
	
	private String packagesToScan4 = "NONE";
	
	private String packagesToScan5 = "NONE";
	
	private String packagesToScan6 = "NONE";

	public String getPackagesToScan() {
		return packagesToScan;
	}

	public void setPackagesToScan(String packagesToScan) {
		this.packagesToScan = packagesToScan;
	}

	public String getPackagesToScan1() {
		return packagesToScan1;
	}

	public void setPackagesToScan1(String packagesToScan1) {
		this.packagesToScan1 = packagesToScan1;
	}

	public String getPackagesToScan2() {
		return packagesToScan2;
	}

	public void setPackagesToScan2(String packagesToScan2) {
		this.packagesToScan2 = packagesToScan2;
	}

	public String getPackagesToScan3() {
		return packagesToScan3;
	}

	public void setPackagesToScan3(String packagesToScan3) {
		this.packagesToScan3 = packagesToScan3;
	}

	public String getPackagesToScan4() {
		return packagesToScan4;
	}

	public void setPackagesToScan4(String packagesToScan4) {
		this.packagesToScan4 = packagesToScan4;
	}

	public String getPackagesToScan5() {
		return packagesToScan5;
	}

	public void setPackagesToScan5(String packagesToScan5) {
		this.packagesToScan5 = packagesToScan5;
	}

	public String getPackagesToScan6() {
		return packagesToScan6;
	}

	public void setPackagesToScan6(String packagesToScan6) {
		this.packagesToScan6 = packagesToScan6;
	}
	
	

}
