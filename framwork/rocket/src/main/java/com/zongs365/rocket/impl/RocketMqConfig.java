package com.zongs365.rocket.impl;

import org.springframework.stereotype.Component;

import java.nio.charset.Charset;


public class RocketMqConfig {
    public static final Charset CHARSET = Charset.forName("UTF-8");
    private String groupId;
    private String accessKey;
    private String secretKey;
    private String nameServer;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getNameServer() {
        return nameServer;
    }

    public void setNameServer(String nameServer) {
        this.nameServer = nameServer;
    }
}
