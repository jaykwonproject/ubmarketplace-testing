package com.ubmarketplace.app.repository;

import com.mongodb.client.result.DeleteResult;
import com.ubmarketplace.app.dao.ItemDao;
import com.ubmarketplace.app.model.Item;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Objects;

@Repository
public class ItemRepository implements ItemDao {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public ItemRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void insert(@NonNull Item item) {
        mongoTemplate.insert(item);
    }

    @Override
    public DeleteResult remove(@NonNull Item item) {
        return mongoTemplate.remove(item);
    }

    @Override
    public List<Item> findAll() {
        return mongoTemplate.findAll(Item.class); //Todo: Should be paginated
    }

    @Override
    public Item findById(String itemId) {
        return mongoTemplate.findById(itemId, Item.class);
    }

    public void update(@NonNull String itemId, @NonNull String name, @NonNull String category,
                       @NonNull String description, @NonNull Double price, @NonNull List<String> images,
                       String meetingPlace, @NonNull String contactPhoneNumber) {
        Item item = findById(itemId);
        if (item == null) {
            throw new InvalidParameterException("Invalid itemId");
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(itemId));
        Update update = new Update();
        if(!name.equals(item.getName())){
            update.set("name", name);
        }
        if(!category.equals(item.getCategory())){
            update.set("category", category);
        }
        if(!description.equals(item.getDescription())){
            update.set("description", description);
        }
        if(!price.equals(item.getPrice())){
            update.set("price", price);
        }
        if(!meetingPlace.equals(item.getMeetingPlace())){
            update.set("meetingPlace", meetingPlace);
        }

        // You should format contact phone number before use this method
        if (!contactPhoneNumber.equals(item.getContactPhoneNumber())){
            update.set("contactPhoneNumber", contactPhoneNumber);
        }

        // You should check if all imageId are valid before use this method
        if(images.size() != item.getImages().size()){
            update.set("images", images);
        } else {
            int i = 0;
            for(String imageId : images){
                if (!Objects.equals(imageId, item.getImages().get(i))){
                    update.set("images", images);
                    break;
                }
            }
        }

        update.set("images", images);
        mongoTemplate.updateFirst(query, update, Item.class);
    }


    public List<Item> getCategoryItem(String category, String userId, String location, String pricing){
        Criteria criteria = new Criteria();
        if (userId.isEmpty() && category.isEmpty() && location.isEmpty() && pricing.isEmpty()){
            return mongoTemplate.findAll(Item.class);
        }

        if (!userId.isEmpty()){
            criteria.and("userId").is(userId);
        }
        if (!category.isEmpty()){
            criteria = criteria.and("category").is(category);
        }
        if (!location.isEmpty()){
            criteria = criteria.and("meetingPlace").is(location);
        }

        Query query = new Query(criteria);

        if (!pricing.isEmpty()){
            if (pricing.equals("descend")){
                query = query.with(Sort.by(Sort.Order.desc("price")));
            }
            else if (pricing.equals("ascend")){
                query = query.with(Sort.by(Sort.Order.asc("price")));
            }
        }

        return mongoTemplate.find(query, Item.class);
    }
}
