package com.efe.ms.crawlerservice.web.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.efe.ms.crawlerservice.util.RedisUtil;

@RestController
@RequestMapping
public class IndexController {
	
	@GetMapping
	public String index(){
		return "welcom to crawler service";
	}
	
	@GetMapping("/redis")
	public String redis(){
//		RedisUtil.set("test", "这是一个redis测试");
		return RedisUtil.get("test");
	}
}
