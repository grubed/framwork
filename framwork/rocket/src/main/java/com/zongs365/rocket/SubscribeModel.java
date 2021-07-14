package com.zongs365.rocket;

public enum SubscribeModel {
    /**
     * 分组模式,该模式下，同一条消息,同一个分组下的消费者只会有一个接收到
     */
    GROUP,
    /**
     * 广播模式,该模式下,同一条消息,同一个分组下的所有消费者都会接收到
     */
    BROADCAST;
}
