package com.anas.journalApp.repository;

import com.anas.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUsersForSA(){
        Query query = new Query();

        query.addCriteria(Criteria.where("username").ne(""));
        query.addCriteria(Criteria.where("email").regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$").ne(null).ne("")); //ne - > null and empty
        query.addCriteria(Criteria.where("sentimentAnalyis").is(true));


        //using Or and AND querying
//        Criteria criteria  = new Criteria();
//
//        query.addCriteria(criteria.orOperator(
//                Criteria.where("username").is("atif"),
//                Criteria.where("email").ne(null)));


//        query.addCriteria(Criteria.where("email").ne(null));


        //querying query , on User Class
        List<User> users = mongoTemplate.find(query,User.class);
        return users;
    }
}
