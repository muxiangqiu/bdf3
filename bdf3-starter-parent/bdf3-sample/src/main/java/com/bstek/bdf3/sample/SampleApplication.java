package com.bstek.bdf3.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
<<<<<<< HEAD:bdf3-starter-parent/bdf3-sample/src/main/java/com/bstek/bdf3/sample/SampleApplication.java
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.bind.annotation.RestController;

=======
import org.springframework.web.bind.annotation.RestController;


>>>>>>> master:bdf3-starters/bdf3-starter-parent/bdf3-sample/src/main/java/com/bstek/bdf3/sample/SampleApplication.java
/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年12月10日
 */
@SpringBootApplication
@EnableCaching
@RestController
public class SampleApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SampleApplication.class, args);
	}
<<<<<<< HEAD:bdf3-starter-parent/bdf3-sample/src/main/java/com/bstek/bdf3/sample/SampleApplication.java

	@Bean
	public HttpFirewall looseHttpFirewall() {
		StrictHttpFirewall firewall = new StrictHttpFirewall();
		// 允许路径中带双斜杠("//"), 解决Doradao IDE在线更新失败的问题
		firewall.setAllowUrlEncodedDoubleSlash(true);
		return firewall;
	}

=======
	
>>>>>>> master:bdf3-starters/bdf3-starter-parent/bdf3-sample/src/main/java/com/bstek/bdf3/sample/SampleApplication.java
}
