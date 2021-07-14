package com.zongs365.rocket.impl;



import com.zongs365.rocket.MessageListener;
import com.zongs365.rocket.SubscribeModel;
import org.springframework.stereotype.Component;


import java.util.List;


public class RewriteRocketMqMessgeSuscriber extends RocketMqMessgeSuscriber {

    private MessageListener messageListener;

    private List<String> topic;
    private int threadSize = 1;
    private SubscribeModel subscribeModel = SubscribeModel.GROUP;

    public void setMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    public void init() {
        this.subscribe(messageListener, subscribeModel, this.topic.toArray(new String[]{}));
    }


    public void setThreadSize(Integer threadSize) {
        this.threadSize = threadSize;
    }

    public void setTopic(List<String> topic) {
        this.topic = topic;
    }

    public SubscribeModel getSubscribeModel() {
        return subscribeModel;
    }

    public void setSubscribeModel(SubscribeModel subscribeModel) {
        this.subscribeModel = subscribeModel;
    }
}
