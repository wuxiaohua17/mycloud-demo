package cn.com.ut.message;

import cn.com.ut.demo.entity.Order;
import cn.com.ut.demo.service.GoodsService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author wuxiaohua
 * @since 2018/8/29
 */
@Configuration
@Slf4j
public class RocketmqConfig {

	@Autowired
	private GoodsService goodsService;

	@Bean(initMethod = "start", destroyMethod = "shutdown")
	public MQPushConsumer defaultMQPushConsumer() {

		DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer();
		defaultMQPushConsumer.setNamesrvAddr("192.168.105.80:9876");
		defaultMQPushConsumer.setConsumerGroup("mycloud-goods");
		defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
		try {
			defaultMQPushConsumer.subscribe("orderTopic", "*");
		} catch (MQClientException e) {
			e.printStackTrace();
		}

		defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
			@Override
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
					ConsumeConcurrentlyContext context) {

				for (MessageExt msg : msgs) {
					byte[] bs = msg.getBody();
					if (msg.getKeys().startsWith("order")) {
						Order order = JSON.parseObject(bs, Order.class);
						goodsService.updateGoodsSale(order);
						log.info("consumeMessage==" + order);
					} else {
						log.info("consumeMessage==" + msg);
					}
				}

				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
		});

		return defaultMQPushConsumer;
	}

}
