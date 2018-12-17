package cn.com.ut.demo.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author wuxiaohua
 * @since 2018/8/24
 */
@Data
public class OrderDTO {

    @NotNull
    protected String orderId;

    protected String accountId;

    protected BigDecimal orderAmount;

    protected String goodsId;

    protected int goodsNum;

    protected Date updateTime;
}
