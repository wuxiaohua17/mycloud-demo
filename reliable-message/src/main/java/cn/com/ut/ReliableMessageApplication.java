package cn.com.ut;

import cn.com.ut.reliablemessage.config.MessageConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableBinding(MessageConfig.class)
@EnableAsync
@EnableScheduling
@Slf4j
public class ReliableMessageApplication {

	public static void main(String[] args) {

		new SpringApplicationBuilder(ReliableMessageApplication.class).web(true).run(args);
	}

}
