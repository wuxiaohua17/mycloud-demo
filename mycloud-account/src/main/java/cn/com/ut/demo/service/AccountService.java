package cn.com.ut.demo.service;

import cn.com.ut.demo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wuxiaohua
 * @since 2018/8/24
 */
@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
}
