package com.bstek.bdf3.autoconfigure.profile;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.bstek.bdf3.autoconfigure.security.SecurityAutoConfiguration;
import com.bstek.bdf3.profile.ProfileConfiguration;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年7月18日
 */
@Configuration
@ConditionalOnClass(ProfileConfiguration.class)
@AutoConfigureBefore({JpaRepositoriesAutoConfiguration.class})
@AutoConfigureAfter(SecurityAutoConfiguration.class)
@Import(ProfileConfiguration.class)
public class ProfileAutoConfiguration {
	
}
