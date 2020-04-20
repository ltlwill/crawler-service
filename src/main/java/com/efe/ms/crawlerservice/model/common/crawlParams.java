package com.efe.ms.crawlerservice.model.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class crawlParams extends SerializationEntity{

	private String keywords;
	
	private int pageCount;
	
	private int threadCount;
}
