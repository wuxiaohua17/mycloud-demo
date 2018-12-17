package cn.com.ut.reliablemessage.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import cn.com.ut.reliablemessage.entity.ReliableMessage;

/**
 * @author wuxiaohua
 * @since 2018/11/2
 */
public interface ReliableMessageRepository extends JpaRepository<ReliableMessage, String> {

	ReliableMessage getByMessageId(String messageId);

	List<ReliableMessage> findByMessageId(String messageId);

	int deleteByMessageId(String messageId);

	int deleteByBizKey(String bizId);

	Page<ReliableMessage> findByIsDeadOrderByCreateTimeAsc(Pageable pageable, String isDead);

	Page<ReliableMessage> findByStatusAndCreateTimeBeforeOrderByCreateTimeAsc(Pageable pageable,
			String status, Date createTimeBefore);

	Page<ReliableMessage> findByStatusAndCreateTimeBeforeAndIsDeadOrderByCreateTimeAsc(
			Pageable pageable, String status, Date createTimeBefore, String isDead);

	Page<ReliableMessage> findByStatusAndIsDeadAndNextSendNotNullAndNextSendBeforeAndCreateTimeBeforeOrderByCreateTimeAsc(
			Pageable pageable, String status, String isDead, Date nextSendBefore,
			Date createTimeBefore);

	Page<ReliableMessage> findByMessageGroupIsNotOrderByMessageGroupAscGroupOrderAsc(
			Pageable pageable, String messageGroup);

	boolean existsByMessageId(String messageId);
}
