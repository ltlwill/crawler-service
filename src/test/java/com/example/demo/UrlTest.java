package com.example.demo;

import org.junit.Test;

import com.efe.ms.crawlerservice.util.SimpleRequestUtil;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.SimpleHttpClient;

public class UrlTest {
	
	String url = "https://desc.alicdn.com/i6/610/071/614079536202/TB1hBDJzQL0gK0jSZFA8qwA9pla.desc%7Cvar%5Edesc%3Blang%5Egbk%3Bsign%5E1bf97cb931b2a9ca387f39037040d9a3%3Bt%5E1585276658";

	@Test
	public void test1() {
		SimpleHttpClient c = new SimpleHttpClient();
		Page page = c.get(url);
		System.out.println(page.getRawText());
	}
	
	@Test
	public void test2() throws Exception{
		String str = SimpleRequestUtil.doGetAsString(url, "gbk");
		System.out.println(str); 
	}
}
