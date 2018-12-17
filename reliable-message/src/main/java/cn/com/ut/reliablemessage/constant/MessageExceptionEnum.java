package cn.com.ut.reliablemessage.constant;

/**
 * 消息服务异常集合
 */
public enum MessageExceptionEnum {

	MESSAGE_TOPIC_CANT_BE_BLANK(20, "消息主题不能为空"), MESSAGE_ID_CANT_BE_BLANK(21,
			"消息ID不能为空"), MESSAGE_BODY_CANT_BE_NULL(22, "消息内容不能为空"), MESSAGE_NOT_FOUND(23,
					"消息不存在"), MESSAGE_NOT_UNIQUE(24, "消息不唯一"), MESSAGE_CODE_CANT_BE_BLANK(25,
							"消息数据类型不能为空"), MESSAGE_BIZKEY_CANT_BE_BLANK(26, "消息业务主键不能为空");

	MessageExceptionEnum(int code, String message) {

		this.code = code;
		this.message = message;
	}

	private Integer code;

	private String message;

	public Integer getCode() {

		return code;
	}

	public void setCode(Integer code) {

		this.code = code;
	}

	public String getMessage() {

		return message;
	}

	public void setMessage(String message) {

		this.message = message;
	}
}
