package cn.com.ut.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuxiaohua
 * @since 2018/8/24
 */
@RestController
@RequestMapping("/account")
public class AccountController {

	@GetMapping(value = "/accounttest")
	public Map<String, Object> accounttest() {

		Map<String, Object> map = new HashMap<>();
		map.put("name", "wu");
		map.put("age", 20);
		return map;
	}
}
