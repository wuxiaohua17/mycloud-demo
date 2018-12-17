package cn.com.ut.message;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import cn.com.ut.demo.entity.Order;
import cn.com.ut.demo.service.OrderService;

@Component
@Slf4j
public class TransactionListenerImpl implements TransactionListener {

	@Autowired
	private OrderService orderService;

	@Override
	public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {

		String key = msg.getKeys();
		if (!key.startsWith("order"))
			return LocalTransactionState.COMMIT_MESSAGE;

		byte[] bs = msg.getBody();
		Order order = JSON.parseObject(bs, Order.class);

		log.info("executeLocalTransaction==" + order);

		try {
			orderService.save(order);
		} catch (Exception ex) {
			ex.printStackTrace();
			return LocalTransactionState.ROLLBACK_MESSAGE;
		}

		return LocalTransactionState.COMMIT_MESSAGE;
		// return LocalTransactionState.UNKNOW;
	}

	@Override
	public LocalTransactionState checkLocalTransaction(MessageExt msg) {

		String key = msg.getKeys();
		if (!key.startsWith("order"))
			return LocalTransactionState.ROLLBACK_MESSAGE;

		byte[] bs = msg.getBody();
		Order order = JSON.parseObject(bs, Order.class);

		log.info("checkLocalTransaction==" + order);

		Order orderPO = orderService.getOrder(order.getOrderId());
		if (orderPO == null) {
			return LocalTransactionState.ROLLBACK_MESSAGE;
		} else {
			return LocalTransactionState.COMMIT_MESSAGE;
		}
	}
}
