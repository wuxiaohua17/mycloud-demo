package cn.com.ut.reliablemessage.checker;

import cn.com.ut.reliablemessage.constant.TransactionStatusEnum;
import cn.com.ut.reliablemessage.entity.ReliableMessage;

/**
 * 事务状态回查确认
 */
public interface TransactionStatusCheckerApi {

	default TransactionStatusEnum checkMessage(ReliableMessage reliableMessage) {

		return TransactionStatusEnum.UNKNOWN;
	}
}
