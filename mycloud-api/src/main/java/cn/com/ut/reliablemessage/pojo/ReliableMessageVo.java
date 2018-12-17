package cn.com.ut.reliablemessage.pojo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author wuxiaohua
 * @since 2018/11/2
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReliableMessageVo {

	private Date updateTime;
	private Date createTime;
	private String messageId;
	private String messageBody;
	private String messageCode;
	private String messageTopic;
	private Integer sendTimes;
	private String isDead;
	private String status;
	private String remark;
	private Long version;
	private String bizKey;
}
