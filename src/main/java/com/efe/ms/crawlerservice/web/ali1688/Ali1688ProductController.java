package com.efe.ms.crawlerservice.web.ali1688;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.efe.ms.crawlerservice.model.ali1688.Ali1688Product;
import com.efe.ms.crawlerservice.model.common.crawlParams;
import com.efe.ms.crawlerservice.service.ali1688.Ali1688ProductService;
import com.efe.ms.crawlerservice.vo.BusinessResult;
import com.efe.ms.crawlerservice.web.common.BaseController;

/**
 * ali 1688产品控制器
 * @author Tianlong Liu
 * @2020年4月16日 下午3:19:18
 */
@RestController
@RequestMapping("/ali1688/products")
public class Ali1688ProductController extends BaseController {
	
	@Autowired
	private Ali1688ProductService productService;
	
	@RequestMapping
	public BusinessResult findProducts(Ali1688Product product,int pageNo,int pageSize) {
		return BusinessResult.success(productService.findAll(product,PageRequest.of(pageNo - 1, pageSize)));
	}
	
	
	@RequestMapping("/crawl")
	public BusinessResult crawlData(crawlParams params) {
		BusinessResult res = BusinessResult.success();
		try {
			productService.crawlData(params);
		}catch (Exception e) {
			logger.error("start crawl ali 1688 product error",e);
			res = BusinessResult.fail();
		}
		return res;
	}

}