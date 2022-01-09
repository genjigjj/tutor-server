/**
 *
 */
package com.wrc.tutor.pay.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 总配置项
 */
@ConfigurationProperties(prefix = "wrc.pay")
@Data
public class PayProperties {

    private AliPayProperties aliPay = new AliPayProperties();

}

