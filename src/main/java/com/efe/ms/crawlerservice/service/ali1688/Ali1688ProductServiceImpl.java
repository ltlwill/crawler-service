package com.efe.ms.crawlerservice.service.ali1688;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.efe.ms.crawlerservice.model.ali1688.Ali1688Product;
import com.efe.ms.crawlerservice.model.common.crawlParams;
import com.efe.ms.crawlerservice.mongorepo.ali1688.Ali1688ProductRepository;
import com.efe.ms.crawlerservice.webmagic.processor.ali1688.Ali1688PageProcessor;

/**
 * ali 1688产品业务实现类
 * 
 * @author Tianlong Liu
 * @2020年4月16日 下午3:10:42
 */
@Service
public class Ali1688ProductServiceImpl implements Ali1688ProductService {

	@Autowired
	private Ali1688ProductRepository productRepository;

	@Override
	public Ali1688Product add(Ali1688Product product) {
		if (product == null) {
			throw new RuntimeException("无效参数");
		}
		return productRepository.save(product);
	}
	
	@Override
	public Ali1688Product addWithDeduplication(Ali1688Product product) {
		Objects.requireNonNull(product,"无效参数");
		Ali1688Product aliProduct = new Ali1688Product();
		aliProduct.setProductId(product.getProductId());
		long count = productRepository.count(Example.of(aliProduct));
		if(count == 0) {
			add(product);
		}
		return product;
	}

	@Override
	public void crawlData(crawlParams params) throws Exception {
		new Ali1688PageProcessor(params).run();
	}

	@Override
	public Page<Ali1688Product> findAll(Ali1688Product product,PageRequest page) {
		Example<Ali1688Product> example = Example.of(product, ExampleMatcher.matching());
		PageRequest pageRequest = PageRequest.of(page.getPageNumber(), page.getPageSize(),
				Sort.by(Direction.DESC, "createTime"));
		return productRepository.findAll(example, pageRequest);
	}

	@Override
	public void delete(Ali1688Product product) {
		if (product == null) {
			throw new RuntimeException("无效参数");
		}
		productRepository.delete(product); 
	}

	@Override
	public void deleteAll() {
		productRepository.deleteAll();
	}

}
