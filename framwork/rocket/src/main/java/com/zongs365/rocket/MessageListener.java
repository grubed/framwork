package com.zongs365.rocket;

public interface MessageListener {
    /**
     * 消息到达
     *
     * @param topic
     * @param message
     */
    void onMessage(String topic, Message message);
}
