package com.ubmarketplace.app.manager;

import com.google.inject.Singleton;
import com.mongodb.client.result.DeleteResult;
import com.ubmarketplace.app.model.Item;
import com.ubmarketplace.app.repository.ItemRepository;
import lombok.NonNull;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Objects;

import static com.ubmarketplace.app.Utils.formatPhoneNumber;

@Singleton
@Component
@Log
public class ItemManager {
    final ItemRepository itemRepository;

    @Autowired
    public ItemManager(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item addItem(@NonNull String name,
                        @NonNull String userId,
                        @NonNull String category,
                        @NonNull String description,
                        @NonNull Double price,
                        @NonNull List<String> imageIds,
                        @NonNull String meetingPlace,
                        @NonNull String contactPhoneNumber,
                        @NonNull ImageManager imageManager) {
        if (!imageManager.isValidImageIds(imageIds)) {
            throw new InvalidParameterException("Invalid ImageId");
        }

        Item item = Item.builder()
                .name(name)
                .userId(userId)
                .category(category)
                .description(description)
                .price(Math.round(price * 100) / 100.0) // convert to only two decimal
                .images(imageIds)
                .meetingPlace(meetingPlace)
                .build();

        if (contactPhoneNumber.matches("^\\d{10}$")) {
            contactPhoneNumber = formatPhoneNumber(contactPhoneNumber);
        }
        item.setContactPhoneNumber(contactPhoneNumber);

        try {
            itemRepository.insert(item);
        } catch (DuplicateKeyException e) {
            log.warning(String.format("Failed to insert itemId %s, such image already exist", item.getItemId()));
            throw new InvalidParameterException("Failed to insert item");
            // Not the best exception, could update to a custom exception
        }

        return item;
    }

    public List<Item> getAllItem() {
        return itemRepository.findAll();
    }


    public Boolean deleteItem(@NonNull String itemID) {
        Item find = itemRepository.findById(itemID);

        if (find == null) {
            throw new InvalidParameterException("No such item");
        }

        DeleteResult result = itemRepository.remove(find);

        return result.wasAcknowledged();
    }

    public Item getItemById(@NonNull String itemId) {
        if (itemId.isEmpty()) {
            log.info("Empty itemId when getItemById");
            throw new InvalidParameterException("Empty itemId");
        }

        Item item = itemRepository.findById(itemId);

        if (item == null) {
            log.warning(String.format("Failed to find item %s, no such image exist", item));
            throw new InvalidParameterException("Failed to find item");
        }

        return item;
    }

    public void editItem(@NonNull String itemId, @NonNull String name, @NonNull String category,
                         @NonNull String description, @NonNull Double price, @NonNull List<String> images,
                         String meetingPlace, @NonNull String contactPhoneNumber, @NonNull String editByUserId,
                         @NonNull UserManager userManager, @NonNull ImageManager imageManager) {
        if (itemId.isEmpty()) {
            log.info("Empty itemId when editItem");
            throw new InvalidParameterException("Empty itemId");
        }
        if (editByUserId.isEmpty()) {
            log.info("Empty editByUserId when editItem");
            throw new InvalidParameterException("Empty editByUserId");
        }

        if (!Objects.equals(getItemById(itemId).getUserId(), editByUserId) && !userManager.isAdmin(editByUserId)) {
            log.warning(String.format("User %s is trying to edit itemId %s, but not an owner or admin",
                    editByUserId, itemId));
            throw new InvalidParameterException("No permission to edit user");
        }

        if (!imageManager.isValidImageIds(images)) {
            throw new InvalidParameterException("Invalid ImageId");
        }

        itemRepository.update(itemId, name, category, description, price, images, meetingPlace, contactPhoneNumber);
    }

    public List<Item> getCategoryItem(String category, String userId, String location, String pricing) {
        return itemRepository.getCategoryItem(category, userId, location, pricing);
    }
}
