package com.diegonogueira.urlshortener.repository;

import com.diegonogueira.urlshortener.entities.UrlEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Urlrepository extends MongoRepository<UrlEntity, String> {
}
