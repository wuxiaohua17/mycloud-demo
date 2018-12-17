package cn.com.ut.demo.controller.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.com.ut.demo.entity.Goods;


@FeignClient(name = "mycloud-goods")
public interface GoodsFeignClient {

    @GetMapping("/goods/getById")
    public Goods getById(@RequestParam(name = "goodsId") String goodsId);

}
