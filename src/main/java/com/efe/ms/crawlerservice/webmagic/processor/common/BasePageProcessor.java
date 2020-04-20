package com.efe.ms.crawlerservice.webmagic.processor.common;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.efe.ms.crawlerservice.extension.HttpClientDownloader;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

/**
 * 基础页面处理器
 * 
 * @author Tianlong Liu
 * @2020年4月10日 上午9:53:44
 */
public abstract class BasePageProcessor implements PageProcessor {

	protected static final Logger logger = LoggerFactory.getLogger(BasePageProcessor.class);
	
	private static final String DEFAULT_CHAR_SET_STR = "UTF-8";

	@Override
	public abstract void process(Page page);

	@Override
	public Site getSite() {
		return createSite();
	}

	protected Site createSite() {
		Site site = Site.me().setRetryTimes(3).setSleepTime(200);
		site.setCharset(defaultCharset());
		setHeaders(site);
		return site;
	}

	protected void setHeaders(Site site) {
		site.addHeader("user-agent",
				"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36");
	}
	
	protected String defaultCharset() {
		return DEFAULT_CHAR_SET_STR;
	}
	
	protected static Downloader createDownloader() {
		return new HttpClientDownloader();
	}
	
	protected JSONObject extraDataFromJsonp(String jsonp) {
		if(StringUtils.isBlank(jsonp)) {
			return new JSONObject();
		}
		int firstIdx = jsonp.indexOf("("),
			lastIndx = jsonp.indexOf(")");
		if(firstIdx < 0 || lastIndx < -1) {
			return new JSONObject();
		}
		String str =  jsonp.substring(firstIdx + 1, lastIndx);
		return StringUtils.isBlank(str) ? new JSONObject() : JSON.parseObject(str);
	}
	
	protected String getStringValue(Selectable selectable) {
		if(selectable == null) {
			return "";
		}
		String val = selectable.toString();
		return StringUtils.isBlank(val) ? "" : val.trim();
	}

	public static Spider buildSpider(PageProcessor processor) {
		return Spider.create(processor).setDownloader(createDownloader());
	}

	public static Spider buildSpider(Class<? extends PageProcessor> clazz) throws Exception {
		return Spider.create(clazz.newInstance()).setDownloader(createDownloader());
	}

}
