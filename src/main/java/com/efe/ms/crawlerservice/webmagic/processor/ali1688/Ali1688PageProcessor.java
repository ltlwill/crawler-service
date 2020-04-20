package com.efe.ms.crawlerservice.webmagic.processor.ali1688;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.efe.ms.crawlerservice.constant.Constants;
import com.efe.ms.crawlerservice.model.common.Product;
import com.efe.ms.crawlerservice.model.common.crawlParams;
import com.efe.ms.crawlerservice.webmagic.parser.ali1688.Ali1688PageParser;
import com.efe.ms.crawlerservice.webmagic.parser.common.ParserBuilder;
import com.efe.ms.crawlerservice.webmagic.pipeline.ali1688.Ali1688ProductsPipeline;
import com.efe.ms.crawlerservice.webmagic.processor.common.BasePageProcessor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.Selectable;

/**
 * ali 1688页面处理
 * 
 * @author Tianlong Liu
 * @2020年4月10日 上午10:05:14
 */
public class Ali1688PageProcessor extends BasePageProcessor {

	private static final String DEFUALT_CHARSET = "GBK";
	private static final String ENTRANCE_URL = "https://p4psearch.1688.com/p4p114/p4psearch/offer.htm"; // 抓取数据入口地址
	private static final String KEYWORDS = "修身连衣裙"; // 要抓取的关键词
	private static final int PAGE_COUNT = 10; // 要抓取多少页的数据
	private static final int THREAD_COUNT = 5; // 要开启的线程数
	private static final int PAGE_JSONP_REQUEST_COUNT = 6; // 每一页中的数据需要发送的jsonp 请求的次数

	private crawlParams params;
	private String taskNo = System.nanoTime() + "";
	private int total = 0;
	private int errorCount = 0;

	public Ali1688PageProcessor() {
		this(new crawlParams(KEYWORDS, PAGE_COUNT, THREAD_COUNT));
	}

	public Ali1688PageProcessor(crawlParams params) {
		this.params = params;
	}

	@Override
	public void process(Page page) {
		Selectable url = page.getUrl();
		if (url.regex("(.*)offer.htm(.*)").match()) { // 列表页
//			setTaskNo(page);
			addJsonpTargetUrls(page, this.params.getPageCount());
		} else if (url.regex("(.*)get_premium_offer_list.json(.*)").match()) { // jsonp 的ajax请求
			JSONObject json = extraDataFromJsonp(page.getRawText());
			addProductDetailTargetUrls(page, json);
		} else if (url.regex("(.*)dj.1688.com/ci_bb(.*)").match()) { // 详情页
			try {
				this.total ++;
				Product product = ParserBuilder.build(Ali1688PageParser.class).parse(page); // 解析页面
				product.setTaskNo(this.taskNo);
				product.setSearchKeyword(this.params.getKeywords());
				page.putField(Constants.PRODUCT_FIELD_NAME, product);
//				System.out.println(JSON.toJSONString(product));
			} catch (Exception e) {
				this.errorCount ++;
				logger.error("解析页面异常，url=" + url.toString(), e);
			}
			System.out.println("---总：" + total + "；失败：" + errorCount);
		} else {
			logger.debug("未知类型的页面");
		}
		
	}

	private void addProductDetailTargetUrls(Page page, JSONObject json) {
		if (json == null) {
			return;
		}
		Object result = JSONPath.eval(json, "$.data.content.offerResult[*].eurl");
		if (result == null) {
			return;
		}
		List<String> detailUrls = JSON.parseArray(result.toString()).stream().map(itm -> itm.toString())
				.collect(Collectors.toList());
		page.addTargetRequests(detailUrls);
	}

	/**
	 * 将请求列表页的jsonp的ajax请求添加到请求队列
	 * 
	 * @param page
	 * @param pageCount 要请求的前多少页
	 */
	private void addJsonpTargetUrls(Page page, int pageCount) {
		for (int pageNo = 1; pageNo <= pageCount; pageNo++) {
			for (int reqNo = 1; reqNo <= PAGE_JSONP_REQUEST_COUNT; reqNo++) {
				page.addTargetRequest(constructJsonpRequestUrl(pageNo, reqNo));
			}
		}
	}

	/**
	 * 构造请求列表页的jsonp请求URL
	 * 
	 * @param pageNo 第几页
	 * @param reqNo  请求页（每一页的列表数据由6次jsonp请求的数据构成）
	 * @return
	 */
	private String constructJsonpRequestUrl(int pageNo, int reqNo) {
		String urlTpl = "https://data.p4psearch.1688.com/data/ajax/get_premium_offer_list.json?beginpage=${pageNo}&asyncreq=${reqNo}&keywords=${keywords}&sortType=&descendOrder=&province=&city=&priceStart=&priceEnd=&dis=&spm=a2609.11209760.it2i6j8a.36.44292de1DQtbim&cosite=baidujj_pz&trackid=%7Btrackid%7D&location=re&pageid=70026a48k6fyjO&p4pid=6ce81c12ad9c49eda85a7c7c77725e9a&callback=jsonp_${random}_${random2}&_=${random}";
		urlTpl = urlTpl.replaceAll("\\$\\{pageNo\\}", pageNo + "");
		urlTpl = urlTpl.replaceAll("\\$\\{reqNo\\}", reqNo + "");
		urlTpl = urlTpl.replaceAll("\\$\\{keywords\\}", this.params.getKeywords());
		urlTpl = urlTpl.replaceAll("\\$\\{random\\}", generateCallbackRandomNo());
		urlTpl = urlTpl.replaceAll("\\$\\{random2\\}", generateRandomNo());
		return urlTpl;
	}
	
	private String generateCallbackRandomNo() {
		return (System.nanoTime() + Math.abs(new Random().nextInt(100))) + "";
	}
	
	private String generateRandomNo() {
		return (Math.abs(new Random().nextInt(10000))) + "";
	}

	@Override
	protected void setHeaders(Site site) {
		super.setHeaders(site);
		site.addHeader("referer",
				"https://p4psearch.1688.com/p4p114/p4psearch/offer.htm?spm=a2609.11209760.it2i6j8a.36.44292de1nHNgKW&cosite=baidujj_pz&keywords=%E4%BF%AE%E8%BA%AB%E8%BF%9E%E8%A1%A3%E8%A3%99&trackid={trackid}&location=re");
	}

	@Override
	protected String defaultCharset() {
		return DEFUALT_CHARSET;
	}

	/**
	 * 设置当前的任务编号，编号为当前执行时的时间戳
	 * 
	 * @param page
	 */
	@SuppressWarnings("unused")
	private void setTaskNo(Page page) {
		this.taskNo = Constants.TASK_NO_FIELD_NAME + "";
	}

	public static void main(String[] args) throws Exception {
		new Ali1688PageProcessor().run();
	}

	public void run() throws Exception {
		buildSpider(this).addUrl(ENTRANCE_URL).addPipeline(new Ali1688ProductsPipeline())
				.thread(this.params.getThreadCount()).run();
	}

	public void run(crawlParams params) throws Exception {
		buildSpider(this).addUrl(ENTRANCE_URL).addPipeline(new Ali1688ProductsPipeline())
				.thread(this.params.getThreadCount()).run();
	}
}
