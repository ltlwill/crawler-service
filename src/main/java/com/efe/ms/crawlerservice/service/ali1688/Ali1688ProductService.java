package com.efe.ms.crawlerservice.service.ali1688;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.efe.ms.crawlerservice.model.ali1688.Ali1688Product;
import com.efe.ms.crawlerservice.model.common.crawlParams;

/**
 * ali 1688 产品业务接口
 * @author Tianlong Liu
 * @2020年4月16日 下午3:09:32
 */
public interface Ali1688ProductService {
	
	void crawlData(crawlParams params) throws Exception;
	
	Ali1688Product add(Ali1688Product product);
	
	Page<Ali1688Product> findAll(Ali1688Product product,PageRequest page);
	

}
