package cn.com.ut.reliablemessage.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import cn.com.ut.demo.entity.Order;
import cn.com.ut.reliablemessage.constant.ReliableMessageConstant;
import cn.com.ut.reliablemessage.constant.TransactionStatusEnum;
import cn.com.ut.reliablemessage.entity.ReliableMessage;

@FeignClient(value = "mycloud-order")
public interface MyOrderClient {

	@GetMapping(value = "/order/getByOrderId")
	public Order getByOrderId(@RequestParam(value = "orderId") String orderId);

	@PostMapping(value = ReliableMessageConstant.TRANSACTION_STATUS_CHECKER_API)
	public TransactionStatusEnum checkMessage(@RequestBody ReliableMessage reliableMessage);

}
