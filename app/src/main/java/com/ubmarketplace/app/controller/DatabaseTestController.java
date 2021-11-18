package com.ubmarketplace.app.controller;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoIterable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DatabaseTestController {

    @Autowired
    private MongoClient mongoClient;

    @RequestMapping("/databasetest")
    @ResponseBody
    public String index() {
        StringBuilder databaseNameBuilder = new StringBuilder();
        MongoIterable<String> listDatabaseNames = mongoClient.listDatabaseNames();
        MongoIterable<String> listCollectionNames = mongoClient.getDatabase("UB_Marketplace_Database").listCollectionNames();

        for (String database : listDatabaseNames) {
            databaseNameBuilder.append(" | ").append(database);
        }
        for (String collection : listCollectionNames){
            databaseNameBuilder.append(" | ").append(collection);
        }
        return String.format("List Database Names: %s", databaseNameBuilder);
    }
}
