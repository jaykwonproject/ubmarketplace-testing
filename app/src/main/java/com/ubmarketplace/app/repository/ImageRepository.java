package com.ubmarketplace.app.repository;

import com.mongodb.client.result.DeleteResult;
import com.ubmarketplace.app.dao.ImageDao;
import com.ubmarketplace.app.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ImageRepository implements ImageDao {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public ImageRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void insert(Image image) {
        mongoTemplate.insert(image);
    }

    @Override
    public DeleteResult remove(Image image) {
        return mongoTemplate.remove(image);
    }

    @Override
    public List<Image> findAll() {
        return mongoTemplate.findAll(Image.class); //Todo: Should be paginated
    }

    @Override
    public Image findById(String imageId) {
        return mongoTemplate.findById(imageId, Image.class);
    }
}
