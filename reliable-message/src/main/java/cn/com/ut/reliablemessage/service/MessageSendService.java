package cn.com.ut.reliablemessage.service;

import java.lang.reflect.Method;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.com.ut.reliablemessage.config.MessageConfig;
import cn.com.ut.reliablemessage.constant.ReliableMessageConstant;
import cn.com.ut.reliablemessage.entity.ReliableMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wuxiaohua
 * @since 2018/10/23
 */
@Slf4j
@Service
public class MessageSendService {

	@Autowired
	private MessageConfig messageConfig;

	@StreamListener(value = "reliableMessageFromAll", condition = "headers['msgCode']=='reliableMessage'")
	public void reliableMessage(Message<ReliableMessage> reliableMessage) {

		log.info("==reliableMessage==");
		if (reliableMessage.getPayload() != null) {
			log.debug("==reliableMessage==" + JSON.toJSONString(reliableMessage.getPayload()));
		}
	}

	public void sendMessage(ReliableMessage reliableMessage) {

		Message<ReliableMessage> message = MessageBuilder.withPayload(reliableMessage)
				.setHeader(ReliableMessageConstant.MESSAGE_HEADER_MSG_CODE,
						reliableMessage.getMessageCode())
				.build();

		try {
			Method method = MethodUtils.getAccessibleMethod(MessageConfig.class,
					reliableMessage.getMessageTopic());
			MessageChannel messageChannel = (MessageChannel) method.invoke(messageConfig);
			messageChannel.send(message);
		} catch (Exception e) {
			log.error("==发送消息失败==" + e.getMessage());
		}

	}
}
