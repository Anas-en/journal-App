package com.anas.journalApp.repository;

import com.anas.journalApp.entity.AppConfig;
import com.anas.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AppConfigRepository extends MongoRepository<AppConfig, ObjectId> {

}
