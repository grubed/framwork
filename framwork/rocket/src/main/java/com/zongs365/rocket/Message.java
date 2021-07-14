package com.zongs365.rocket;

import com.zongs365.util.resource.JsonUtil;

public class Message {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 消息ID
     */
    private String id;
    /**
     * 消息的key
     */
    private String key;
    /**
     * 消息内容,必填项
     */
    private String content;
    /**
     * 消息标签
     */
    private String tag = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}
