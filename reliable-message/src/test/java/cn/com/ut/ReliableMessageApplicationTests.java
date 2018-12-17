package cn.com.ut;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.com.ut.reliablemessage.config.ReliableMessageConfig;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReliableMessageApplicationTests {

	@Autowired
	private ReliableMessageConfig reliableMessageConfig;

	@Test
	public void test() {

		System.out.println("==SendTimeInterval==" + reliableMessageConfig.getSendTimeInterval());
	}

}
