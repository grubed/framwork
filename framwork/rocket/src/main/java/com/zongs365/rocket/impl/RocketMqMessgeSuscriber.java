package com.zongs365.rocket.impl;

import com.aliyun.openservices.ons.api.*;
import com.zongs365.rocket.MessageSubscriber;
import com.zongs365.rocket.SubscribeModel;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class RocketMqMessgeSuscriber implements MessageSubscriber, Closeable {
    // private final Logger logger = Logger.getLogger(RocketMqMessgeSuscriber.class);
    private Map<String, Consumer> consumers = new ConcurrentHashMap<>();
    private RocketMqConfig mqConfig;

    public void setMqConfig(RocketMqConfig mqConfig) {
        this.mqConfig = mqConfig;
    }

    @Override
    public void subscribe(com.zongs365.rocket.MessageListener listener, String... topics) {
        subscribe(listener, SubscribeModel.GROUP, topics);
    }

    @Override
    public void subscribe(com.zongs365.rocket.MessageListener listener, SubscribeModel model, String... topics) {
        if (listener == null || topics == null || topics.length == 0) {
            return;
        }
        Consumer consumer = null;
        for (String topic : topics) {
            String consumerKey = buildConsumerKey(listener, topic);
            if (!consumers.containsKey(consumerKey)) {
                if (consumer == null) {
                    consumer = createConsumer(model);
                }
                consumers.put(consumerKey, consumer);
                ListenerWrapper wrapper = new ListenerWrapper(listener);
                consumer.subscribe(topic, "*", wrapper);
                // logger.info(listener + " subscribe topic " + topic);
            }
        }
        if (consumer != null) {
            consumer.start();
        }
    }

    private String buildConsumerKey(com.zongs365.rocket.MessageListener listener, String topic) {
        return listener.toString() + ":" + topic;
    }

    private Consumer createConsumer(SubscribeModel model) {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.GROUP_ID, mqConfig.getGroupId());
        properties.put(PropertyKeyConst.AccessKey, mqConfig.getAccessKey());
        properties.put(PropertyKeyConst.SecretKey, mqConfig.getSecretKey());
        properties.put(PropertyKeyConst.NAMESRV_ADDR, mqConfig.getNameServer());
        if (model == SubscribeModel.BROADCAST) {
            properties.put(PropertyKeyConst.MessageModel, PropertyValueConst.BROADCASTING);
        }
        Consumer consumer = ONSFactory.createConsumer(properties);
        return consumer;
    }

    private class ListenerWrapper implements com.aliyun.openservices.ons.api.MessageListener {
        private final com.zongs365.rocket.MessageListener listener;

        public ListenerWrapper(com.zongs365.rocket.MessageListener listener) {
            this.listener = listener;
        }

        @Override
        public Action consume(Message message, ConsumeContext context) {
//            if (logger.isDebugEnabled()) {
//                logger.debug(message);
//            }
            String topic = message.getTopic();
            String msgId = message.getMsgID();
            String key = message.getKey();
            String tag = message.getTag();
            byte[] body = message.getBody();
            com.zongs365.rocket.Message msg = new com.zongs365.rocket.Message();
            msg.setId(msgId);
            msg.setKey(key);
            msg.setTag(tag);
            msg.setContent(new String(body, RocketMqConfig.CHARSET));
            try {
                listener.onMessage(topic, msg);
                return Action.CommitMessage;
            } catch (Throwable e) {
                // logger.warn("fail to notify message to " + listener.getClass() + ",message:" + msg, e);
                return Action.ReconsumeLater;
            }
        }
    }

    @Override
    public void close() throws IOException {
        for (Consumer consumer : consumers.values()) {
            consumer.shutdown();
        }
    }
}
