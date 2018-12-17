package cn.com.ut;

import cn.com.ut.message.MessageConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EnableBinding(MessageConfig.class)
public class MycloudOrderApplication {

    public static void main(String[] args) {

        new SpringApplicationBuilder(MycloudOrderApplication.class).web(true).run(args);
    }
}
