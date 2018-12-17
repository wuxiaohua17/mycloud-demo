package cn.com.ut.reliablemessage.timer;

import cn.com.ut.reliablemessage.checker.ConfirmingMessageChecker;
import cn.com.ut.reliablemessage.checker.SendingMessageChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.com.ut.reliablemessage.constant.ReliableMessageConstant;

/**
 * 消息定时检查计划任务
 *
 */
@Component
public class MessageCheckerTimerTask {

	@Autowired
	private SendingMessageChecker sendingMessageChecker;

	@Autowired
	private ConfirmingMessageChecker confirmingMessageChecker;

	/**
	 * 定时检查“待确认”但已超时的消息
	 *
	 */
	@Scheduled(initialDelay = ReliableMessageConstant.SCHEDULE_INITIAL_DELAY, fixedRate = ReliableMessageConstant.SCHEDULE_FIXED_RATE)
	public void checkWaitingConfirmTimeOutMessages() {

		confirmingMessageChecker.checkMessages();

	}

	/**
	 * 定时检查“发送中”但超时没有被成功消费确认的消息
	 *
	 */
	@Scheduled(initialDelay = ReliableMessageConstant.SCHEDULE_INITIAL_DELAY, fixedRate = ReliableMessageConstant.SCHEDULE_FIXED_RATE)
	public void checkSendingTimeOutMessage() {

		sendingMessageChecker.checkMessages();

	}
}
