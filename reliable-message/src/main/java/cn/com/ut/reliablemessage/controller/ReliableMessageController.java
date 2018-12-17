package cn.com.ut.reliablemessage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import cn.com.ut.reliablemessage.client.ReliableMessageClient;
import cn.com.ut.reliablemessage.entity.ReliableMessage;
import cn.com.ut.reliablemessage.pojo.MessageExistVo;
import cn.com.ut.reliablemessage.pojo.PageQuery;
import cn.com.ut.reliablemessage.service.ReliableMessageService;

/**
 * @author wuxiaohua
 * @since 2018/11/5
 */
@RestController
@RequestMapping(value = "/message")
public class ReliableMessageController implements ReliableMessageClient {

	@Autowired
	private ReliableMessageService reliableMessageService;

	/**
	 * 根据消息ID判断消息是否存在
	 */
	@Override
	@RequestMapping("/messageExist")
	public MessageExistVo messageExist(@RequestParam("messageId") String messageId) {

		return reliableMessageService.messageExist(messageId);
	}

	/**
	 * 存储待发送消息
	 */
	@Override
	@RequestMapping(value = "/preSaveMessage", method = RequestMethod.POST)
	public ReliableMessage preSaveMessage(@RequestBody ReliableMessage reliableMessage) {

		return reliableMessageService.preSaveMessage(reliableMessage);
	}

	/**
	 * 确认并发送消息
	 */
	@Override
	@RequestMapping("/confirmAndSendMessage")
	public void confirmAndSendMessage(@RequestParam("messageId") String messageId) {

		reliableMessageService.confirmAndSendMessage(messageId);
	}

	/**
	 * 存储并发送消息
	 */
	@Override
	@RequestMapping("/saveAndSendMessage")
	public void saveAndSendMessage(@RequestBody ReliableMessage reliableMessage) {

		reliableMessageService.saveAndSendMessage(reliableMessage);
	}

	/**
	 * 直接发送消息
	 */
	@Override
	@RequestMapping("/directSendMessage")
	public void directSendMessage(@RequestBody ReliableMessage reliableMessage) {

		reliableMessageService.directSendMessage(reliableMessage);
	}

	/**
	 * 重发消息
	 */
	@Override
	@RequestMapping("/reSendMessage")
	public void reSendMessage(@RequestBody ReliableMessage reliableMessage) {

		reliableMessageService.reSendMessage(reliableMessage);
	}

	/**
	 * 根据消息ID重发某条消息
	 */
	@Override
	@RequestMapping("/reSendMessageByMessageId")
	public void reSendMessageByMessageId(@RequestParam("messageId") String messageId) {

		reliableMessageService.reSendMessageByMessageId(messageId);
	}

	/**
	 * 将消息标记为死亡消息
	 */
	@Override
	@RequestMapping("/setMessageToIsDead")
	public void setMessageToIsDead(@RequestParam("messageId") String messageId) {

		reliableMessageService.setMessageToIsDead(messageId);
	}

	/**
	 * 根据消息ID获取消息
	 */
	@Override
	@RequestMapping("/getMessageByMessageId")
	public ReliableMessage getMessageByMessageId(@RequestParam("messageId") String messageId) {

		return reliableMessageService.getMessageByMessageId(messageId);
	}

	/**
	 * 根据消息ID删除消息
	 */
	@Override
	@RequestMapping("/deleteMessageByMessageId")
	public void deleteMessageByMessageId(@RequestParam("messageId") String messageId) {

		reliableMessageService.deleteMessageByMessageId(messageId);
	}

	/**
	 * 根据业务主键删除消息
	 */
	@Override
	@RequestMapping("/deleteMessageByBizKey")
	public void deleteMessageByBizKey(@RequestParam("bizKey") String bizKey) {

		reliableMessageService.deleteMessageByBizKey(bizKey);
	}

	/**
	 * 重发某个消息主题中的全部已死亡的消息
	 */
	@Override
	@RequestMapping("/reSendAllDeadMessageByTopic")
	public void reSendAllDeadMessageByTopic(@RequestParam("topic") String topic) {

		reliableMessageService.reSendAllDeadMessageByTopic(topic);
	}

	/**
	 * 分页获取待发送超时的消息
	 */
	@Override
	@RequestMapping("/queryConfirmingOvertimeMessages")
	public Page<ReliableMessage> queryConfirmingOvertimeMessages(@RequestBody PageQuery pageQuery) {

		return reliableMessageService.queryConfirmingOvertimeMessages(pageQuery);

	}

	/**
	 * 分页获取发送中超时的消息
	 */
	@Override
	@RequestMapping("/querySendingOvertimeMessages")
	public Page<ReliableMessage> querySendingOvertimeMessages(@RequestBody PageQuery pageQuery) {

		return reliableMessageService.querySendingOvertimeMessages(pageQuery);
	}
}
