package cn.com.ut;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import cn.com.ut.demo.entity.Order;
import cn.com.ut.demo.repository.OrderRepository;

import java.math.BigDecimal;

//import org.hibernate.query.NativeQuery;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    private Environment environment;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    public void testEnv() {

//		String s = environment.getProperty("server.port");
//		System.out.println("==" + s);

        System.out.println(new BigDecimal(0.01).multiply(new BigDecimal(0.8)));
    }

    @Test
    public void testJpa() {

        Order order1 = orderRepository.getOne("31");
        order1.setGoodsNum(2);

        Order order2 = orderRepository.getByOrderId("68");
        order2.setGoodsNum(3);

        System.out.println("=======");

    }
}
