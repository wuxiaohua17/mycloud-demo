package cn.com.ut.reliablemessage.checker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cn.com.ut.reliablemessage.constant.ReliableMessageConstant;
import cn.com.ut.reliablemessage.constant.TransactionStatusEnum;
import cn.com.ut.reliablemessage.entity.ReliableMessage;

/**
 * @author wuxiaohua
 * @since 2018/11/12
 */
@RestController
public class TransactionStatusCheckerController {

	@Autowired(required = false)
	private TransactionStatusCheckerApi transactionStatusCheckerApi;

	@PostMapping(value = ReliableMessageConstant.TRANSACTION_STATUS_CHECKER_API)
	public TransactionStatusEnum checkMessage(@RequestBody ReliableMessage reliableMessage) {

		return transactionStatusCheckerApi.checkMessage(reliableMessage);
	}
}
