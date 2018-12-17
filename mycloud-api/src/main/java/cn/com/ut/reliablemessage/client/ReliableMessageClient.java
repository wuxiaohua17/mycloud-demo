package cn.com.ut.reliablemessage.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.com.ut.reliablemessage.entity.ReliableMessage;
import cn.com.ut.reliablemessage.pojo.MessageExistVo;
import cn.com.ut.reliablemessage.pojo.PageQuery;

@FeignClient(value = "reliable-message", path = "/message")
public interface ReliableMessageClient {

	/**
	 * 根据消息ID判断消息是否存在
	 */
	@RequestMapping("/messageExist")
	MessageExistVo messageExist(@RequestParam("messageId") String messageId);

	/**
	 * 预存储消息
	 */
	@PostMapping(value = "/preSaveMessage")
	ReliableMessage preSaveMessage(@RequestBody ReliableMessage reliableMessage);

	/**
	 * 确认并发送消息
	 */
	@RequestMapping("/confirmAndSendMessage")
	void confirmAndSendMessage(@RequestParam("messageId") String messageId);

	/**
	 * 存储并发送消息
	 */
	@RequestMapping("/saveAndSendMessage")
	void saveAndSendMessage(@RequestBody ReliableMessage reliableMessage);

	/**
	 * 直接发送消息
	 */
	@PostMapping("/directSendMessage")
	void directSendMessage(@RequestBody ReliableMessage reliableMessage);

	/**
	 * 重发消息
	 */
	@PostMapping("/reSendMessage")
	void reSendMessage(@RequestBody ReliableMessage reliableMessage);

	/**
	 * 根据messageId重发某条消息
	 */
	@RequestMapping("/reSendMessageByMessageId")
	void reSendMessageByMessageId(@RequestParam("messageId") String messageId);

	/**
	 * 将消息标记为死亡消息
	 */
	@RequestMapping("/setMessageToIsDead")
	void setMessageToIsDead(@RequestParam("messageId") String messageId);

	/**
	 * 根据消息ID获取消息
	 */
	@RequestMapping("/getMessageByMessageId")
	ReliableMessage getMessageByMessageId(@RequestParam("messageId") String messageId);

	/**
	 * 根据消息ID删除消息
	 */
	@RequestMapping("/deleteMessageByMessageId")
	void deleteMessageByMessageId(@RequestParam("messageId") String messageId);

	/**
	 * 根据业务id删除消息
	 */
	@RequestMapping("/deleteMessageByBizKey")
	void deleteMessageByBizKey(@RequestParam("bizKey") String bizKey);

	/**
	 * 重发某个消息队列中的全部已死亡的消息.
	 */
	@RequestMapping("/reSendAllDeadMessageByTopic")
	void reSendAllDeadMessageByTopic(@RequestParam("topic") String topic);

	/**
	 * 分页获取待发送超时的数据
	 */
	@PostMapping("/queryConfirmingOvertimeMessages")
	Page<ReliableMessage> queryConfirmingOvertimeMessages(@RequestBody PageQuery pageQuery);

	/**
	 * 分页获取发送中超时的数据
	 */
	@PostMapping("/querySendingOvertimeMessages")
	Page<ReliableMessage> querySendingOvertimeMessages(@RequestBody PageQuery pageQuery);
}
