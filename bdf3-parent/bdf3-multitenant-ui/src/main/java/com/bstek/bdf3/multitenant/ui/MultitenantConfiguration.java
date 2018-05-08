package com.bstek.bdf3.multitenant.ui;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Kevin Yang (mailto:muxiangqiu@gmail.com)
 * @since 2017年11月24日
 */
@Configuration
@ComponentScan
@Import({org.malagu.multitenant.MultitenantConfiguration.class})
public class MultitenantConfiguration {
	
}
