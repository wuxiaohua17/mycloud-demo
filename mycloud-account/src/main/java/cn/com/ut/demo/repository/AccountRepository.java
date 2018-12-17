package cn.com.ut.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.com.ut.demo.entity.Account;

/**
 * @author wuxiaohua
 * @since 2018/8/24
 */
public interface AccountRepository extends JpaRepository<Account, String> {
}
