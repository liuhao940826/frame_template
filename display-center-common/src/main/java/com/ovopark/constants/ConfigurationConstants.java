package com.ovopark.constants;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Classname ConfigurationConstants
 * @Description TODO
 * @Date 2021/4/22 下午10:31
 * @Created by liuhao
 */
@Component
@ConfigurationProperties(prefix = "config")
public class ConfigurationConstants {

    private Long expiredTime;

    public Long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Long expiredTime) {
        this.expiredTime = expiredTime;
    }
}
