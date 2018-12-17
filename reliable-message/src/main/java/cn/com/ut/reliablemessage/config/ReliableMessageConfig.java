package cn.com.ut.reliablemessage.config;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

/**
 * 消息服务的配置
 */
@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "reliable")
public class ReliableMessageConfig {

	/**
	 * 消息检查的时间段,时间单位秒
	 */
	private Integer checkInterval = 5;

	/**
	 * 消息重投的次数和间隔，时间单位分钟
	 */
	List<Integer> sendTimeInterval;

	Map<String, String> transactionStatusChecker;

}
