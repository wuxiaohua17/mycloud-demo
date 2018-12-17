package cn.com.ut.demo.controller;

import cn.com.ut.demo.entity.Order;
import cn.com.ut.demo.repository.OrderRepository;
import cn.com.ut.demo.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuxiaohua
 * @since 2018/8/24
 */
@RestController
@RequestMapping(value = "/order")
@Slf4j
public class OrderController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderRepository orderRepository;

	/**
	 * 可靠消息子系统最终事务一致性：下单
	 * 
	 * @param orderId
	 */
	@RequestMapping("/submitOrder")
	public void submitOrder(@RequestParam String orderId) {

		orderService.submitOrder(orderId);
	}

	/**
	 * 供消息子系统RPC回查订单状态
	 * 
	 * @param orderId
	 * @return
	 */
	@GetMapping(value = "/getByOrderId")
	public Order getByOrderId(@RequestParam String orderId) {

		return orderRepository.findOne(orderId);
	}

	/**
	 * 事务消息发送消费:下单
	 * 
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/addOrderBySendTxMsg")
	public Order addOrderBySendTxMsg(@RequestParam("orderId") String orderId) {

		return orderService.addOrderBySendTxMsg(orderId);
	}

	/**
	 * 普通消息发送消费：下单
	 * 
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/addOrderBySendMsg")
	public Order addOrderBySendMsg(@RequestParam("orderId") String orderId) {

		return orderService.addOrderBySendMsg(orderId);
	}
}
