package com.efe.ms.crawlerservice.mongorepo.ali1688;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.efe.ms.crawlerservice.model.ali1688.Ali1688Product;

public interface Ali1688ProductRepository extends MongoRepository<Ali1688Product, String> {

}
