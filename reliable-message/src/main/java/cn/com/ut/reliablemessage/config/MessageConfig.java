package cn.com.ut.reliablemessage.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author wuxiaohua
 * @since 2018/8/22
 */
public interface MessageConfig {

	@Input("reliableMessageFromAll")
	SubscribableChannel reliableMessageFromAll();

	@Output("myOrderFromReliable")
	MessageChannel myOrderFromReliable();

	@Output("myGoodsFromReliable")
	MessageChannel myGoodsFromReliable();

}
