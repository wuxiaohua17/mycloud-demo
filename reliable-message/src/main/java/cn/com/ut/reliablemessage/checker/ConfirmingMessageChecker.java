package cn.com.ut.reliablemessage.checker;

import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import cn.com.ut.reliablemessage.client.MyOrderClient;
import cn.com.ut.reliablemessage.config.ReliableMessageConfig;
import cn.com.ut.reliablemessage.constant.ReliableMessageConstant;
import cn.com.ut.reliablemessage.constant.TransactionStatusEnum;
import cn.com.ut.reliablemessage.entity.ReliableMessage;
import cn.com.ut.reliablemessage.pojo.PageQuery;
import cn.com.ut.reliablemessage.service.ReliableMessageService;
import lombok.extern.slf4j.Slf4j;

/**
 * 处理状态为“待确认”但已超时的消息
 *
 */
@Service
@Slf4j
public class ConfirmingMessageChecker extends MessageChecker implements ApplicationContextAware {

	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private ReliableMessageService reliableMessageService;
	@Autowired
	private MyOrderClient myOrderClient;
	@Autowired
	private ReliableMessageConfig reliableMessageConfig;

	@Override
	protected void processMessage(Map<String, ReliableMessage> reliableMessages) {

		reliableMessages.values().forEach(this::processMessage);
	}

	@Override
	protected void processMessage(ReliableMessage reliableMessage) {

		try {

			if (reliableMessage.getBizKey() == null) {

				// 删掉没用的消息
				reliableMessageService.deleteMessageByMessageId(reliableMessage.getMessageId());
				return;
			}

			String checkerName = reliableMessageConfig.getTransactionStatusChecker()
					.get(reliableMessage.getMessageSource());
			Class<?> checkerClass = ClassUtils.getClass(checkerName);
			Object checkerObj = applicationContext.getBean(checkerClass);
			Method method = MethodUtils.getAccessibleMethod(checkerClass,
					ReliableMessageConstant.TRANSACTION_STATUS_CHECKER_METHOD,
					ReliableMessage.class);

			TransactionStatusEnum transactionStatusEnum = (TransactionStatusEnum) method
					.invoke(checkerObj, reliableMessage);

			// 如果事务提交，则确认消息并发送
			if (transactionStatusEnum.equals(TransactionStatusEnum.COMMIT)) {
				reliableMessageService.confirmAndSendMessage(reliableMessage.getMessageId());
			}
			// 如果事务回滚，则删掉没用的消息
			else if (transactionStatusEnum.equals(TransactionStatusEnum.ROLLBACK)) {

				reliableMessageService.deleteMessageByMessageId(reliableMessage.getMessageId());
			}
			// 如果事务状态未知，待处理
			else {
				// TODO
			}

		} catch (Exception e) {
			log.error("处理待确认消息异常！messageId=" + reliableMessage.getMessageId(), e);
		}
	}

	@Override
	protected Page<ReliableMessage> getPageResult(PageQuery pageQuery) {

		return reliableMessageService.queryConfirmingOvertimeMessages(pageQuery);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

		this.applicationContext = applicationContext;
	}
}
