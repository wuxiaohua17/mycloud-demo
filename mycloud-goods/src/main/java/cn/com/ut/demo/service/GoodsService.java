package cn.com.ut.demo.service;

import cn.com.ut.demo.entity.Goods;
import cn.com.ut.demo.entity.Order;
import cn.com.ut.demo.repository.GoodsRepository;
import cn.com.ut.reliablemessage.client.ReliableMessageClient;
import cn.com.ut.reliablemessage.entity.ReliableMessage;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

;

/**
 * @author wuxiaohua
 * @since 2018/8/24
 */
@Service
@Slf4j
public class GoodsService {

	@Autowired
	private GoodsRepository goodsRepository;
	@Autowired
	private ReliableMessageClient reliableMessageClient;

	public Goods getById(String goodsId) {

		return goodsRepository.findOne(goodsId);
	}

	/**
	 * 根据RocketMQ发送消息，更新商品销量
	 * 
	 * @param order
	 */
	@Transactional
	public void updateGoodsSale(Order order) {

		Goods goods = goodsRepository.findOne(order.getGoodsId());
		goods.setUpdateTime(new Date());
		goods.setGoodsSale(goods.getGoodsSale() + order.getGoodsNum());
		goodsRepository.save(goods);
	}

	/**
	 * 根据可靠消息子系统发送消息，更新商品销量
	 * 
	 * @param reliableMessage
	 */
	@Transactional
	public void updateGoodsSale(ReliableMessage reliableMessage) {

		if (!reliableMessageClient.messageExist(reliableMessage.getMessageId()).isExist())
			return;

		Order order = JSON.parseObject(reliableMessage.getMessageBody(), Order.class);
		Goods goods = goodsRepository.findOne(order.getGoodsId());
		goods.setUpdateTime(new Date());
		goods.setGoodsSale(goods.getGoodsSale() + order.getGoodsNum());
		goodsRepository.save(goods);
		reliableMessageClient.deleteMessageByMessageId(reliableMessage.getMessageId());
	}

}
