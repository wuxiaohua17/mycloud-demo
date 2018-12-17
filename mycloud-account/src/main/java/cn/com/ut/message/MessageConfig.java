package cn.com.ut.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author wuxiaohua
 * @since 2018/8/22
 */
public interface MessageConfig {

    @Output("orderIn")
    MessageChannel accountOutOrderIn();

    @Input("accountIn")
    SubscribableChannel accountIn();

}
