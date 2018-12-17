package cn.com.ut.reliablemessage.service;

import cn.com.ut.reliablemessage.config.ReliableMessageConfig;
import cn.com.ut.reliablemessage.constant.MessageExceptionEnum;
import cn.com.ut.reliablemessage.constant.MessageStatusEnum;
import cn.com.ut.reliablemessage.constant.ReliableMessageConstant;
import cn.com.ut.reliablemessage.entity.ReliableMessage;
import cn.com.ut.reliablemessage.exception.MessageException;
import cn.com.ut.reliablemessage.pojo.MessageExistVo;
import cn.com.ut.reliablemessage.pojo.PageQuery;
import cn.com.ut.reliablemessage.repository.ReliableMessageRepository;
import cn.com.ut.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息服务提供接口的实现
 *
 */
@RestController
@Slf4j
public class ReliableMessageService {

	@Autowired
	private ReliableMessageRepository reliableMessageRepository;

	@Autowired
	private ReliableMessageConfig reliableMessageConfig;
	@Autowired
	private MessageSendService messageSendService;

	public MessageExistVo messageExist(String messageId) {

		MessageExistVo messageExistVo = new MessageExistVo();
		messageExistVo.setExist(reliableMessageRepository.existsByMessageId(messageId));
		return messageExistVo;
	}

	public ReliableMessage preSaveMessage(ReliableMessage reliableMessage) {

		// 检查消息数据的完整性
		messageValidate(reliableMessage);

		// 设置状态为待确认
		reliableMessage.setStatus(MessageStatusEnum.CONFIRMING.name());

		// 标记未死亡
		reliableMessage.setIsDead(ReliableMessageConstant.FLAG_NO);
		reliableMessage.setSendTimes(0);

		Date time = new Date();
		reliableMessage.setCreateTime(time);
		reliableMessage.setUpdateTime(time);
		reliableMessageRepository.save(reliableMessage);
		return reliableMessage;
	}

	public void confirmAndSendMessage(String messageId) {

		ReliableMessage reliableMessage = reliableMessageRepository.getByMessageId(messageId);

		if (reliableMessage == null) {
			throw new MessageException(MessageExceptionEnum.MESSAGE_NOT_FOUND.getMessage());
		}

		reliableMessage.setStatus(MessageStatusEnum.SENDING.name());

		// 下次发送时间点
		Date time = new Date();
		int sendTimeInterval = reliableMessageConfig.getSendTimeInterval()
				.get(reliableMessage.getSendTimes() == 0 ? 0 : reliableMessage.getSendTimes() - 1);
		long nextSendTimestamp = time.getTime() + sendTimeInterval * 60 * 1000;
		reliableMessage.setNextSend(new Date(nextSendTimestamp));
		reliableMessage.setUpdateTime(time);
		reliableMessageRepository.save(reliableMessage);

		// 发送消息
		messageSendService.sendMessage(reliableMessage);
	}

	public void saveAndSendMessage(ReliableMessage reliableMessage) {

		// 检查消息数据的完整性
		messageValidate(reliableMessage);

		reliableMessage.setStatus(MessageStatusEnum.SENDING.name());
		reliableMessage.setIsDead(ReliableMessageConstant.FLAG_NO);
		reliableMessage.setSendTimes(0);

		Date time = new Date();
		reliableMessage.setCreateTime(time);
		reliableMessage.setUpdateTime(time);
		reliableMessageRepository.save(reliableMessage);

		// 发送消息
		messageSendService.sendMessage(reliableMessage);
	}

	public void directSendMessage(ReliableMessage reliableMessage) {

		// 检查消息数据的完整性
		messageValidate(reliableMessage);

		// 发送消息
		messageSendService.sendMessage(reliableMessage);
	}

	public void reSendMessage(ReliableMessage reliableMessage) {

		// 检查消息数据的完整性
		messageValidate(reliableMessage);

		// 更新消息发送次数
		reliableMessage.setSendTimes(reliableMessage.getSendTimes() + 1);

		// 下次发送时间点
		Date time = new Date();
		int sendTimeInterval = reliableMessageConfig.getSendTimeInterval()
				.get(reliableMessage.getSendTimes() == 0 ? 0 : reliableMessage.getSendTimes() - 1);
		long nextSendTimestamp = time.getTime() + sendTimeInterval * 60 * 1000;

		reliableMessage.setNextSend(new Date(nextSendTimestamp));
		reliableMessage.setUpdateTime(time);
		reliableMessageRepository.save(reliableMessage);

		// 发送消息
		messageSendService.sendMessage(reliableMessage);
	}

	public void reSendMessageByMessageId(String messageId) {

		if (CommonUtil.isEmpty(messageId)) {
			throw new MessageException(MessageExceptionEnum.MESSAGE_ID_CANT_BE_BLANK.getMessage());
		}

		ReliableMessage reliableMessage = getMessageByMessageId(messageId);

		reliableMessage.setSendTimes(reliableMessage.getSendTimes() + 1);
		reliableMessage.setUpdateTime(new Date());
		reliableMessageRepository.save(reliableMessage);

		// 发送消息
		messageSendService.sendMessage(reliableMessage);
	}

	public void setMessageToIsDead(String messageId) {

		if (CommonUtil.isEmpty(messageId)) {
			throw new MessageException(MessageExceptionEnum.MESSAGE_ID_CANT_BE_BLANK.getMessage());
		}

		ReliableMessage reliableMessage = getMessageByMessageId(messageId);
		reliableMessage.setIsDead(ReliableMessageConstant.FLAG_YES);
		reliableMessage.setUpdateTime(new Date());

		reliableMessageRepository.save(reliableMessage);

		// 发送消息
		messageSendService.sendMessage(reliableMessage);
	}

	public ReliableMessage getMessageByMessageId(String messageId) {

		if (CommonUtil.isEmpty(messageId)) {
			throw new MessageException(MessageExceptionEnum.MESSAGE_ID_CANT_BE_BLANK.getMessage());
		}

		List<ReliableMessage> reliableMessages = reliableMessageRepository
				.findByMessageId(messageId);
		if (reliableMessages == null || reliableMessages.isEmpty()) {
			throw new MessageException(MessageExceptionEnum.MESSAGE_NOT_FOUND.getMessage());
		} else if (reliableMessages.size() > 1) {
			throw new MessageException(MessageExceptionEnum.MESSAGE_NOT_UNIQUE.getMessage());
		} else {
			return reliableMessages.get(0);
		}
	}

	@Transactional
	public void deleteMessageByMessageId(String messageId) {

		if (CommonUtil.isEmpty(messageId)) {
			throw new MessageException(MessageExceptionEnum.MESSAGE_ID_CANT_BE_BLANK.getMessage());
		}

		reliableMessageRepository.deleteByMessageId(messageId);
	}

	public void deleteMessageByBizKey(String bizKey) {

		if (CommonUtil.isEmpty(bizKey)) {
			throw new MessageException(
					MessageExceptionEnum.MESSAGE_BIZKEY_CANT_BE_BLANK.getMessage());
		}

		reliableMessageRepository.deleteByBizKey(bizKey);
	}

	@Transactional(rollbackFor = Exception.class)
	public void reSendAllDeadMessageByTopic(String topic) {

		// 起始页
		Integer pageNo = 0;

		// 存放查询到的所有死亡消息，避免重复消息，使用message_id作为key
		Map<String, ReliableMessage> allDeadMessages = new LinkedHashMap<>();

		Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "createTime"));
		PageRequest pageRequest = new PageRequest(pageNo, ReliableMessageConstant.MAX_PAGE_SIZE,
				sort);

		// 循环查询所有结构（分页）
		Page<ReliableMessage> pageResult = reliableMessageRepository
				.findByIsDeadOrderByCreateTimeAsc(pageRequest, ReliableMessageConstant.FLAG_YES);

		if (pageResult == null || CommonUtil.isEmptyCollection(pageResult.getContent())) {
			return;
		} else {
			pageResult.getContent().forEach(e -> allDeadMessages.put(e.getMessageId(), e));
		}

		// 循环查出剩下的还有多少,并且都放入集合
		long pages = pageResult.getTotalPages();
		for (++pageNo; pageNo < pages; pageNo++) {

			pageRequest = new PageRequest(pageNo, ReliableMessageConstant.MAX_PAGE_SIZE, sort);
			Page<ReliableMessage> secondPageResult = reliableMessageRepository
					.findByIsDeadOrderByCreateTimeAsc(pageRequest,
							ReliableMessageConstant.FLAG_YES);
			if (secondPageResult != null
					&& !CommonUtil.isEmptyCollection(secondPageResult.getContent())) {
				pageResult.getContent().forEach(e -> allDeadMessages.put(e.getMessageId(), e));
			}
		}

		// 重新发送死亡消息
		allDeadMessages.values().forEach(e -> {
			e.setUpdateTime(new Date());
			e.setSendTimes(e.getSendTimes() + 1);
			reliableMessageRepository.save(e);
			messageSendService.sendMessage(e);
		});

	}

	public Page<ReliableMessage> queryGroupMessage(PageQuery pageQuery) {

		PageRequest pageRequest = new PageRequest(pageQuery.getPageno(), pageQuery.getPagesize());
		return reliableMessageRepository.findByMessageGroupIsNotOrderByMessageGroupAscGroupOrderAsc(
				pageRequest, ReliableMessageConstant.MESSAGE_GROUP_DEFAULT);
	}

	public Page<ReliableMessage> queryConfirmingOvertimeMessages(PageQuery pageQuery) {

		PageRequest pageRequest = new PageRequest(pageQuery.getPageno(), pageQuery.getPagesize());
		Page<ReliableMessage> reliableMessagePage = reliableMessageRepository
				.findByStatusAndCreateTimeBeforeOrderByCreateTimeAsc(pageRequest,
						MessageStatusEnum.CONFIRMING.name(), CommonUtil.addSeconds(new Date(),
								-reliableMessageConfig.getCheckInterval()));

		return reliableMessagePage;
	}

	public Page<ReliableMessage> querySendingOvertimeMessages(PageQuery pageQuery) {

		PageRequest pageRequest = new PageRequest(pageQuery.getPageno(), pageQuery.getPagesize());
		Page<ReliableMessage> reliableMessagePage = reliableMessageRepository
				.findByStatusAndIsDeadAndNextSendNotNullAndNextSendBeforeAndCreateTimeBeforeOrderByCreateTimeAsc(
						pageRequest, MessageStatusEnum.SENDING.name(),
						ReliableMessageConstant.FLAG_NO, new Date(), CommonUtil
								.addSeconds(new Date(), -reliableMessageConfig.getCheckInterval()));

		return reliableMessagePage;
	}

	/**
	 * 检查消息参数是否为空
	 *
	 */
	private void messageValidate(ReliableMessage reliableMessage) {

		if (reliableMessage == null) {
			throw new MessageException(MessageExceptionEnum.MESSAGE_BODY_CANT_BE_NULL.getMessage());
		}
		if (CommonUtil.isEmpty(reliableMessage.getMessageId())) {

			throw new MessageException(MessageExceptionEnum.MESSAGE_ID_CANT_BE_BLANK.getMessage());
		}
		if (CommonUtil.isEmpty(reliableMessage.getMessageBody())) {
			throw new MessageException(MessageExceptionEnum.MESSAGE_BODY_CANT_BE_NULL.getMessage());
		}
		if (CommonUtil.isEmpty(reliableMessage.getMessageTopic())) {

			throw new MessageException(
					MessageExceptionEnum.MESSAGE_TOPIC_CANT_BE_BLANK.getMessage());
		}
	}

}
