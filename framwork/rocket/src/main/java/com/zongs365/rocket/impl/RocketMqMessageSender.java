package com.zongs365.rocket.impl;

import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.SendResult;
import com.zongs365.rocket.Message;
import com.zongs365.rocket.MessageSender;
import com.zongs365.rocket.impl.RocketMqConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Closeable;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

@DependsOn("rocketMqConfig")
@Component
public class RocketMqMessageSender implements MessageSender, Closeable {
    //private static final Logger logger = Logger.getLogger(RocketMqMessageSender.class);
    private Producer producer;
    private long sendTimeoutMillis;

    @Autowired
    private RocketMqConfig mqConfig;


    public void setMqConfig(RocketMqConfig mqConfig) {

        this.mqConfig = mqConfig;
    }

    public void setSendTimeoutMillis(long sendTimeoutMillis) {
        this.sendTimeoutMillis = sendTimeoutMillis;
    }


    @Order(2)
    @PostConstruct
    public void init() {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.GROUP_ID, mqConfig.getGroupId());
        properties.put(PropertyKeyConst.AccessKey, mqConfig.getAccessKey());
        properties.put(PropertyKeyConst.SecretKey, mqConfig.getSecretKey());
        if (sendTimeoutMillis > 0) {
            properties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, String.valueOf(sendTimeoutMillis));
        }
        properties.put(PropertyKeyConst.NAMESRV_ADDR, mqConfig.getNameServer());
        producer = ONSFactory.createProducer(properties);
        producer.start();
    }

    @Override
    public void sendMessage(String topic, Message message) {
        sendMessageOn(topic, message, null);
    }

    @Override
    public void close() throws IOException {
        if (producer != null) {
            producer.shutdown();
        }
    }

    /**
     * 注意,阿里云对延迟时长有限制：最大7天,超过7天的需要自己实现延迟控制
     */
    @Override
    public void sendMessage(String topic, Message message, long delaySeconds) {
        if (delaySeconds <= 0) {
            sendMessageOn(topic, message, null);
        } else {
            Date sendDate = new Date(System.currentTimeMillis() + delaySeconds * 1000);
            sendMessageOn(topic, message, sendDate);
        }
    }

    @Override
    public void sendMessageOn(String topic, Message message, Date sendDate) {
        String content = message.getContent();
        if (content == null || "".equals(content)) {// 不发送空消息
            return;
        }
        String msgId = message.getId();
        String msgKey = message.getKey();
        String tag = message.getTag();
        com.aliyun.openservices.ons.api.Message msg = new com.aliyun.openservices.ons.api.Message(topic, tag, msgKey,
                content.getBytes(RocketMqConfig.CHARSET));
        msg.setMsgID(msgId);
        if (sendDate != null) {
            msg.setStartDeliverTime(sendDate.getTime());
        }
        SendResult result = producer.send(msg);
//        if (logger.isDebugEnabled()) {
//            logger.debug("sent msg,topic=" + result.getTopic() + ",id=" + result.getMessageId());
//        }
    }
}
