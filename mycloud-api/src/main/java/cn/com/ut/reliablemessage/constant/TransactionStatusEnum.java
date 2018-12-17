package cn.com.ut.reliablemessage.constant;

/**
 * 消息状态
 *
 * @author stylefeng
 * @Date 2018/4/16 22:31
 */
public enum TransactionStatusEnum {

	COMMIT(1, "提交"), ROLLBACK(0, "回滚"), UNKNOWN(2, "未知");

	TransactionStatusEnum(int code, String desc) {

		this.code = code;
		this.desc = desc;
	}

	private Integer code;

	private String desc;

	public Integer getCode() {

		return code;
	}

	public void setCode(Integer code) {

		this.code = code;
	}

	public String getDesc() {

		return desc;
	}

	public void setDesc(String desc) {

		this.desc = desc;
	}

}
