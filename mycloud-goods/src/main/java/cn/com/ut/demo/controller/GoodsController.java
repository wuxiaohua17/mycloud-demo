package cn.com.ut.demo.controller;

import cn.com.ut.demo.entity.Goods;
import cn.com.ut.demo.repository.GoodsRepository;
import cn.com.ut.demo.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author wuxiaohua
 * @since 2018/10/22
 */
@RestController
@RequestMapping("/goods")
@Slf4j
public class GoodsController {

	@Autowired
	private GoodsRepository goodsRepository;

	@Autowired
	private GoodsService goodsService;

	/**
	 * 添加商品
	 * 
	 * @param goodsId
	 * @param goodsName
	 */
	@RequestMapping("/addGoods")
	public void addGoods(@RequestParam(name = "goodsId") String goodsId,
			@RequestParam(name = "goodsName") String goodsName) {

		Goods goods = new Goods();
		goods.setGoodsId(goodsId);
		goods.setGoodsName(goodsName);
		goods.setGoodsPrice(new BigDecimal("55.50"));
		goods.setGoodsStock(1000);
		goodsRepository.save(goods);
	}

	/**
	 * 供消息子系统RPC回查商品状态
	 * 
	 * @param goodsId
	 * @return
	 */
	@GetMapping("/getById")
	public Goods getById(@RequestParam(name = "goodsId") String goodsId) {

		return goodsService.getById(goodsId);
	}

}
