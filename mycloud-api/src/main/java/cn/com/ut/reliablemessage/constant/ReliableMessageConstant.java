package cn.com.ut.reliablemessage.constant;

/**
 * @author wuxiaohua
 * @since 2018/11/12
 */
public class ReliableMessageConstant {

	public static final String MESSAGE_GROUP_DEFAULT = "0";

	public static final long SCHEDULE_INITIAL_DELAY = 20000L;

	public static final long SCHEDULE_FIXED_RATE = 10000L;

	public static final int MAX_PAGE_SIZE = 1000;

	public static final int MAX_PAGE_COUNT = 3;
	public static final int START_PAGE_NO = 0;

	public static final String TRANSACTION_STATUS_CHECKER_METHOD = "checkMessage";
	public static final String TRANSACTION_STATUS_CHECKER_API = "/public/checkMessage";

	public static final String MESSAGE_HEADER_MSG_CODE = "msgCode";

	public static final String FLAG_YES = "Y";
	public static final String FLAG_NO = "N";
}
