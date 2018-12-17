package cn.com.ut.demo.service;

import cn.com.ut.demo.entity.Order;
import cn.com.ut.demo.repository.OrderRepository;
import cn.com.ut.reliablemessage.client.ReliableMessageClient;
import cn.com.ut.reliablemessage.entity.ReliableMessage;
import cn.com.ut.seq.GIDGenerator;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

//import org.springframework.messaging.Message;

/**
 * @author wuxiaohua
 * @since 2018/8/24
 */
@Service
@Slf4j
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ReliableMessageClient reliableMessageClient;

	@Autowired
	private TransactionMQProducer producer;

	@Autowired
	private DefaultMQProducer defaultMQProducer;

	public void submitOrder(String orderId) {

		Order order = new Order();
		order.setOrderId(orderId);
		order.setGoodsNum(3);
		order.setGoodsId("1");
		order.setAccountId("zhangsan");
		order.setOrderAmount(
				BigDecimal.valueOf(10).multiply(BigDecimal.valueOf(order.getGoodsNum())));
		order.setUpdateTime(new Date());

		ReliableMessage reliableMessage = createMessage(order);
		reliableMessageClient.preSaveMessage(reliableMessage);
		save(order);
		reliableMessageClient.confirmAndSendMessage(reliableMessage.getMessageId());

	}

	public Order getOrder(String orderId) {

		return orderRepository.getByOrderId(orderId);
	}

	private ReliableMessage createMessage(Order order) {

		String messageId = String.valueOf(GIDGenerator.getInstance().nextId());
		String messageBody = JSON.toJSONString(order);
		ReliableMessage reliableMessage = new ReliableMessage();
		reliableMessage.setMessageId(messageId);
		reliableMessage.setMessageBody(messageBody);
		reliableMessage.setMessageTopic("myGoodsFromReliable");
		reliableMessage.setBizKey(order.getOrderId());
		reliableMessage.setCreateTime(new Date());
		reliableMessage.setUpdateTime(reliableMessage.getCreateTime());
		reliableMessage.setMessageCode("updateGoodsSale");
		reliableMessage.setMessageSource("mycloud-order");

		return reliableMessage;
	}

	@Transactional
	public void save(Order order) {

		orderRepository.save(order);
	}

	public Order addOrderBySendTxMsg(String orderId) {

		Order order = new Order();
		order.setOrderId(orderId);
		order.setAccountId("zhangsan");
		order.setGoodsId("1");
		order.setGoodsNum(2);
		order.setOrderAmount(
				BigDecimal.valueOf(10).multiply(BigDecimal.valueOf(order.getGoodsNum())));
		order.setUpdateTime(new Date());

		Message msg = new Message("orderTopic", "addOrder", "order" + orderId,
				JSON.toJSONBytes(order));

		try {
			SendResult sendResult = producer.sendMessageInTransaction(msg, null);
		} catch (MQClientException e) {
			e.printStackTrace();
		}

		log.info("addOrderBySendTxnMsg==" + order);

		return order;

	}

	@Transactional
	public Order addOrderBySendMsg(String orderId) {

		Order order = new Order();
		order.setOrderId(orderId);
		order.setAccountId("zhangsan");
		order.setGoodsId("1");
		order.setGoodsNum(1);
		order.setOrderAmount(
				BigDecimal.valueOf(10).multiply(BigDecimal.valueOf(order.getGoodsNum())));
		order.setUpdateTime(new Date());

		orderRepository.save(order);

		Message msg = new Message("orderTopic", "addOrder", "order" + orderId,
				JSON.toJSONBytes(order));

		try {
			SendResult sendResult = defaultMQProducer.send(msg);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

		log.info("addOrderBySendMsg==" + order);

		return order;

	}

}
