package com.zongs365.rocket;

import java.util.Date;

public interface MessageSender {
    /**
     * 发送一条消息
     *
     * @param topic   消息所属topic
     * @param message 消息
     */
    void sendMessage(String topic, Message message);

    /**
     * 延迟一段实现发送消息
     *
     * @param topic        消息所属topic
     * @param message      消息
     * @param delaySeconds 延迟秒数
     */
    void sendMessage(String topic, Message message, long delaySeconds);

    /**
     * 在特定时间点发送消息
     *
     * @param topic    消息所属topic
     * @param message  消息
     * @param sendDate 发送日期
     */
    void sendMessageOn(String topic, Message message, Date sendDate);
}
