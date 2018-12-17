package cn.com.ut.message;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wuxiaohua
 * @since 2018/8/29
 */
@Configuration
@Slf4j
public class RocketmqConfig {

	@Bean(initMethod = "start", destroyMethod = "shutdown")
	public TransactionMQProducer producer(TransactionListener transactionListener) {

		TransactionMQProducer producer = new TransactionMQProducer("mycloud-order-tx");
		producer.setNamesrvAddr("192.168.105.80:9876");

		producer.setTransactionListener(transactionListener);
		return producer;
	}

	@Bean(initMethod = "start", destroyMethod = "shutdown")
	public DefaultMQProducer defaultMQProducer() {

		TransactionMQProducer producer = new TransactionMQProducer("mycloud-order");
		producer.setNamesrvAddr("192.168.105.80:9876");
		return producer;
	}

}
