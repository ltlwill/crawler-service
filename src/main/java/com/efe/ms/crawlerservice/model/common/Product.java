package com.efe.ms.crawlerservice.model.common;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *     产品
 * @author Tianlong Liu
 * @2020年4月15日 上午9:47:48
 */
@SuppressWarnings("serial")
@Getter
@Setter
@NoArgsConstructor
public class Product extends BizModel {
	
	// 任务编号
	private String taskNo;
	// 搜索产品的关键词
	private String searchKeyword;
	// 产品ID
	protected String productId; 
	// 产品名称
	protected String productName;
	// 产品图片地址
	protected String productImageUrl;
	// 产品链接地址
	protected String productLinkUrl;
	// 产品类目ID
	protected String cateId;
	// 产品类目名称
	protected String cateName;
	// 创建时间
//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date createTime;
	// 状态
	protected Integer status;
	
	protected Seller seller;
	
	public static final class Status{
		public static final int INVALID = 0; // 无效
		public static final int VALID = 1;   // 有效
	}

}