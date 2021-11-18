package com.ubmarketplace.app.dao;

import com.mongodb.client.result.DeleteResult;

import java.util.List;

public interface BaseDao<T> {
    void insert(T t);

    DeleteResult remove(T t);

    List<T> findAll();

    T findById(String id);
}
