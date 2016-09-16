package com.bstek.bdf3.autoconfigure.message;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.bstek.bdf3.message.MessageConfiguration;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年9月3日
 */
@Configuration
@AutoConfigureBefore({JpaRepositoriesAutoConfiguration.class})
@ConditionalOnClass(MessageConfiguration.class)
@Import(MessageConfiguration.class)
public class MessageAutoConfiguration {

}
