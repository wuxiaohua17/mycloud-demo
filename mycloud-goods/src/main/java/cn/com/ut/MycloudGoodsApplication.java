package cn.com.ut;

import cn.com.ut.message.MessageConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
@EnableBinding(MessageConfig.class)
public class MycloudGoodsApplication {

	public static void main(String[] args) {

		new SpringApplicationBuilder(MycloudGoodsApplication.class).web(true).run(args);
	}
}
