package com.bstek.bdf3.autoconfigure.notice;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.bstek.bdf3.notice.NoticeConfiguration;


/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年9月11日
 */
@Configuration
@AutoConfigureBefore({JpaRepositoriesAutoConfiguration.class})
@ConditionalOnClass(NoticeConfiguration.class)
@Import(NoticeConfiguration.class)
public class NoticeAutoConfiguration {

}
