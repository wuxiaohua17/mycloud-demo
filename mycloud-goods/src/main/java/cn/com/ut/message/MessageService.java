package cn.com.ut.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.com.ut.demo.service.GoodsService;
import cn.com.ut.reliablemessage.entity.ReliableMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wuxiaohua
 * @since 2018/10/23
 */
@Slf4j
@Service
public class MessageService {

	@Autowired
	private MessageConfig messageConfig;
	@Autowired
	private GoodsService goodsService;

	@StreamListener(value = "myGoodsFromReliable", condition = "headers['msgCode']=='updateGoodsSale'")
	public void reliableMessage(Message<ReliableMessage> reliableMessage) {

		log.info("==reliableMessage==");
		if (reliableMessage.getPayload() != null) {
			log.debug("==reliableMessage==" + JSON.toJSONString(reliableMessage.getPayload()));
		}
		goodsService.updateGoodsSale(reliableMessage.getPayload());
	}

	public void sendMessage(ReliableMessage reliableMessage) {

	}
}
