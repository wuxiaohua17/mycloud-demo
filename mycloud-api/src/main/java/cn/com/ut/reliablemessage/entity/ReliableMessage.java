package cn.com.ut.reliablemessage.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wuxiaohua
 * @since 2018/11/2
 */
@Getter
@Setter
@Entity
@Table(name = "reliable_message")
public class ReliableMessage {

	@Column(name = "\"update_time\"")
	private Date updateTime;

	@Column(name = "\"create_time\"", updatable = false)
	private Date createTime;

	@Id
	@Column(name = "\"message_id\"", updatable = false)
	private String messageId;

	@Column(name = "\"message_body\"", updatable = false)
	private String messageBody;

	@Column(name = "\"message_code\"", updatable = false)
	private String messageCode;

	@Column(name = "\"message_topic\"", updatable = false)
	private String messageTopic;

	@Column(name = "\"message_group\"", updatable = false)
	private String messageGroup = "0";

	@Column(name = "\"group_order\"", updatable = false)
	private Integer groupOrder = 1;

	@Column(name = "\"send_times\"")
	private Integer sendTimes;

	@Column(name = "\"next_send\"")
	private Date nextSend;

	@Column(name = "\"is_dead\"")
	private String isDead;

	@Column(name = "\"status\"")
	private String status;
	@Column(name = "\"remark\"", updatable = false)
	private String remark;
	@Column(name = "\"version\"", updatable = false)
	private Long version = 0L;
	@Column(name = "\"biz_key\"", updatable = false)
	private String bizKey;
	@Column(name = "\"message_source\"", updatable = false)
	private String messageSource;
}
