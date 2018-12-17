package cn.com.ut.demo.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
@Table(name = "orders")
public class Order {

	@Id
	@Column(name = "order_id")
	protected String orderId;

	@Column(name = "account_id")
	protected String accountId;

	@Column(name = "order_amount")
	protected BigDecimal orderAmount;

	@Column(name = "goods_id")
	protected String goodsId;

	@Column(name = "goods_num")
	protected int goodsNum;

	@Column(name = "update_time")
	protected Date updateTime;

	@Column(name = "status")
	protected int status;

}
