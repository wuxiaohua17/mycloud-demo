package cn.com.ut.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.ut.demo.entity.Order;
import cn.com.ut.demo.repository.OrderRepository;
import cn.com.ut.reliablemessage.checker.TransactionStatusCheckerApi;
import cn.com.ut.reliablemessage.constant.TransactionStatusEnum;
import cn.com.ut.reliablemessage.entity.ReliableMessage;
import lombok.extern.slf4j.Slf4j;

//import org.springframework.messaging.Message;

/**
 * @author wuxiaohua
 * @since 2018/8/24
 */
@Service
@Slf4j
public class TransactionStatusChecker implements TransactionStatusCheckerApi {

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public TransactionStatusEnum checkMessage(ReliableMessage reliableMessage) {

		Order order = orderRepository.findOne(reliableMessage.getBizKey());
		if (order != null && order.getStatus() == 3) {
			return TransactionStatusEnum.COMMIT;
		} else {
			return TransactionStatusEnum.ROLLBACK;
		}

	}
}
