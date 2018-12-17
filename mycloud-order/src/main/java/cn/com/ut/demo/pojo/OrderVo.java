package cn.com.ut.demo.pojo;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author wuxiaohua
 * @since 2018/10/19
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderVo {

	protected String orderId;

	protected String accountId;

	protected BigDecimal orderAmount;

	protected String goodsId;

	protected int goodsNum;

	protected Date updateTime;
}
