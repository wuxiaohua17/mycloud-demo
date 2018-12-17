package cn.com.ut.reliablemessage.exception;

/**
 * @author wuxiaohua
 * @since 2018/11/6
 */
public class MessageException extends RuntimeException {

	public MessageException() {

		super();
	}

	public MessageException(String message) {

		super(message);
	}

	public MessageException(String message, Throwable cause) {

		super(message, cause);
	}

	public MessageException(Throwable cause) {

		super(cause);
	}
}
