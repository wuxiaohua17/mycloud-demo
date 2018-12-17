package cn.com.ut.demo.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "goods")
public class Goods {

	@Id
	@Column(name = "goods_id")
	protected String goodsId;

	@Column(name = "goods_name")
	protected String goodsName;

	@Column(name = "goods_price")
	protected BigDecimal goodsPrice;

	@Column(name = "goods_stock")
	protected int goodsStock;

	@Column(name = "goods_sale")
	protected Long goodsSale;

	@Column(name = "update_time")
	protected Date updateTime;

}
