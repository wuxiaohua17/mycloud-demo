package cn.com.ut.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.com.ut.demo.entity.Goods;

public interface GoodsRepository extends JpaRepository<Goods, String> {

    
}
