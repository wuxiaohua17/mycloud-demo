package cn.com.ut.reliablemessage.checker;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import cn.com.ut.reliablemessage.config.ReliableMessageConfig;
import cn.com.ut.reliablemessage.entity.ReliableMessage;
import cn.com.ut.reliablemessage.pojo.PageQuery;
import cn.com.ut.reliablemessage.service.ReliableMessageService;
import lombok.extern.slf4j.Slf4j;

/**
 * 处理状态为“发送中”但超时没有被成功消费确认的消息
 *
 */
@Service
@Slf4j
public class SendingMessageChecker extends MessageChecker {

	@Autowired
	private ReliableMessageConfig reliableMessageConfig;
	@Autowired
	private ReliableMessageService reliableMessageService;

	@Override
	protected void processMessage(Map<String, ReliableMessage> reliableMessages) {

		reliableMessages.values().forEach(this::processMessage);

	}

	@Override
	protected void processMessage(ReliableMessage reliableMessage) {

		try {
			// 判断发送次数
			int maxTimes = reliableMessageConfig.getSendTimeInterval().size();

			// 如果超过最大发送次数直接退出
			if (reliableMessage.getSendTimes() > maxTimes) {

				// 标记为死亡
				reliableMessageService.setMessageToIsDead(reliableMessage.getMessageId());
				return;
			}

			// 判断是否达到发送消息的时间间隔条件
			int sendTimeInterval = reliableMessageConfig.getSendTimeInterval().get(
					reliableMessage.getSendTimes() == 0 ? 0 : reliableMessage.getSendTimes() - 1);
			long nextSendTimestamp = reliableMessage.getUpdateTime().getTime()
					+ sendTimeInterval * 60 * 1000;

			// 判断是否达到了可以再次发送的时间条件
			if (nextSendTimestamp > System.currentTimeMillis()) {
				return;
			}

			// 重新发送消息
			reliableMessageService.reSendMessage(reliableMessage);

		} catch (Exception e) {
			log.error("处理[SENDING]消息ID为[" + reliableMessage.getMessageId() + "]的消息异常：", e);
		}
	}

	@Override
	protected Page<ReliableMessage> getPageResult(PageQuery pageQuery) {

		return reliableMessageService.querySendingOvertimeMessages(pageQuery);
	}

}
