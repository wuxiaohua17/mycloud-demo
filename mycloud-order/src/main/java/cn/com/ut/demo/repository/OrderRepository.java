package cn.com.ut.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.com.ut.demo.entity.Order;

public interface OrderRepository extends JpaRepository<Order, String> {

	Order getByOrderId(String id);

	Order getByAccountId(String accountId);

	Order findFirstByAccountId(String accountId);

	Order getByGoodsId(String goodsId);



}
