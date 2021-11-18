package com.ubmarketplace.app.repository;

import com.mongodb.client.result.DeleteResult;
import com.ubmarketplace.app.dao.UserDao;
import com.ubmarketplace.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository implements UserDao {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public UserRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void insert(User user) {
        mongoTemplate.insert(user);
    }

    @Override
    public DeleteResult remove(User user) {
        return mongoTemplate.remove(user);
    }

    @Override
    public List<User> findAll() {
        return mongoTemplate.findAll(User.class);
    }

    @Override
    public User findById(String id) {
        return mongoTemplate.findById(id, User.class);
    }
}
