package com.wrc.tutor.pay.common.config;

import com.wrc.tutor.pay.common.properties.PayProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
@EnableConfigurationProperties(PayProperties.class)
public class PayCoreConfig {

}
