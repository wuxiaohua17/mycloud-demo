package cn.com.ut.reliablemessage.checker;

import cn.com.ut.reliablemessage.constant.MessageStatusEnum;
import cn.com.ut.reliablemessage.constant.ReliableMessageConstant;
import cn.com.ut.reliablemessage.entity.ReliableMessage;
import cn.com.ut.reliablemessage.pojo.PageQuery;
import cn.com.ut.reliablemessage.service.ReliableMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wuxiaohua
 * @since 2018/11/8
 */
@Service
@Slf4j
public class GroupMessageChecker extends MessageChecker {

	@Autowired
	private ReliableMessageService reliableMessageService;
	@Autowired
	private SendingMessageChecker sendingMessageChecker;
	@Autowired
	private ConfirmingMessageChecker confirmingMessageChecker;

	@Override
	protected void processMessage(ReliableMessage reliableMessage) {

		if (reliableMessage.getStatus().equals(MessageStatusEnum.CONFIRMING.name())) {

			confirmingMessageChecker.processMessage(reliableMessage);

		} else if (reliableMessage.getStatus().equals(MessageStatusEnum.SENDING.name())
				&& !ReliableMessageConstant.FLAG_YES.equals(reliableMessage.getIsDead())) {
			sendingMessageChecker.processMessage(reliableMessage);
		}
	}

	@Override
	protected void processMessage(Map<String, ReliableMessage> reliableMessages) {

		Map<String, List<ReliableMessage>> reliableMessageGroup = reliableMessages.values().stream()
				.collect(Collectors.groupingBy(ReliableMessage::getMessageGroup));
		reliableMessageGroup.forEach((k, v) -> processMessage(v.get(0)));
	}

	@Override
	protected Page<ReliableMessage> getPageResult(PageQuery pageQuery) {

		return reliableMessageService.queryGroupMessage(pageQuery);
	}
}
