package cn.com.ut.reliablemessage.checker;

import cn.com.ut.reliablemessage.constant.ReliableMessageConstant;
import cn.com.ut.reliablemessage.entity.ReliableMessage;
import cn.com.ut.reliablemessage.pojo.PageQuery;
import cn.com.ut.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 抽象消息校验重发服务
 *
 */
@Slf4j
public abstract class MessageChecker {

	public void checkMessages() {

		try {
			// 存放查询结果，避免重复消息，使用message_id作为key
			Map<String, ReliableMessage> reliableMessages = new LinkedHashMap<>();

			int currentPage = ReliableMessageConstant.START_PAGE_NO; // 当前处理页

			PageQuery pageQuery = new PageQuery();
			pageQuery.setPageno(currentPage);
			pageQuery.setPagesize(ReliableMessageConstant.MAX_PAGE_SIZE);
			Page<ReliableMessage> pageResult = getPageResult(pageQuery);
			if (pageResult != null && !CommonUtil.isEmptyCollection(pageResult.getContent())) {
				pageResult.getContent().forEach(e -> reliableMessages.put(e.getMessageId(), e));
			} else {
				return;
			}

			long totalPage = pageResult.getTotalPages();
			if (totalPage > ReliableMessageConstant.MAX_PAGE_COUNT) {
				totalPage = ReliableMessageConstant.MAX_PAGE_COUNT;
			}

			for (++currentPage; currentPage < totalPage; currentPage++) {

				pageQuery.setPageno(currentPage);
				pageResult = getPageResult(pageQuery);
				if (pageResult != null && !CommonUtil.isEmptyCollection(pageResult.getContent())) {
					pageResult.getContent().forEach(e -> reliableMessages.put(e.getMessageId(), e));
				}
			}

			// 开始处理
			processMessage(reliableMessages);

		} catch (Exception e) {
			log.error("处理待发送状态的消息异常！", e);
		}
	}

	protected abstract void processMessage(ReliableMessage reliableMessage);

	protected abstract void processMessage(Map<String, ReliableMessage> reliableMessages);

	protected abstract Page<ReliableMessage> getPageResult(PageQuery pageQuery);
}
