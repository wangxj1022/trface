package com.trendcote.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Description 同步配置类
 * @Date 2019/7/29 14:01
 * @Created by xym
 */
@Component
@PropertySource(value = "classpath:sync.properties")
public class SyncConfig {

    @Value("${sync.visitor.url}")
    private String syncVisitorUrl;   // 同步访客地址

    @Value("${spring.eoadecrypt.publickey}")
    private String eoaPublicKey;    // 解密公钥( EOA )

    public String getEoaPublicKey() {
        return eoaPublicKey;
    }

    public void setEoaPublicKey(String eoaPublicKey) {
        this.eoaPublicKey = eoaPublicKey;
    }

    public String getSyncVisitorUrl() {
        return syncVisitorUrl;
    }

    public void setSyncVisitorUrl(String syncVisitorUrl) {
        this.syncVisitorUrl = syncVisitorUrl;
    }

    @Override
    public String toString() {
        return "SyncConfig{" +
                "syncVisitorUrl='" + syncVisitorUrl + '\'' +
                ", eoaPublicKey='" + eoaPublicKey + '\'' +
                '}';
    }
}
