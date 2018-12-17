package cn.com.ut.demo.dto;

import lombok.Data;

/**
 * @author wuxiaohua
 * @since 2018/8/24
 */
@Data
public class GoodsDTO {

    private String goodsId;
    private String goodsName;
    private OrderDTO orderDTO;
}
