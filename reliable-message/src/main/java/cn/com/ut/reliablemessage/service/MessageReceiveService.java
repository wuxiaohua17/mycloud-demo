package cn.com.ut.reliablemessage.service;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wuxiaohua
 * @since 2018/11/6
 */
@Slf4j
@Service
public class MessageReceiveService {

	@StreamListener(value = "reliableMessageFromAll", condition = "headers['msgCode']=='ack'")
	public void reliableMessageFromAll(Message<?> message) {

		log.info("==reliableMessageFromAll==");
	}
}
