package com.zongs365.rocket;

public interface MessageSubscriber {
    /**
     * 订阅消息
     *
     * @param listener 监听器
     * @param topics   订阅的topic列表
     */
    void subscribe(MessageListener listener, String... topics);

    /**
     * 订阅消息
     *
     * @param listener 监听器
     * @param model    订阅模式
     * @param topics   订阅的topic列表
     */

    void subscribe(MessageListener listener, SubscribeModel model, String... topics);
}
