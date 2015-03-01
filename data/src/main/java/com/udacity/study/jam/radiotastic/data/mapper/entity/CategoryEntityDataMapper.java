/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.data.mapper.entity;

import com.udacity.study.jam.radiotastic.Category;
import com.udacity.study.jam.radiotastic.data.entity.CategoryEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CategoryEntityDataMapper {

    public Category transform(CategoryEntity categoryEntity) {
        Category category = new Category();
        category.setId(categoryEntity.getId());
        category.setDescription(categoryEntity.getDescription());
        category.setName(categoryEntity.getName());
        return category;
    }

    public Collection<Category> transform(Collection<CategoryEntity> userEntityCollection) {
        List<Category> userList = new ArrayList<Category>(20);
        Category user;
        for (CategoryEntity categoryEntity : userEntityCollection) {
            user = transform(categoryEntity);
            if (user != null) {
                userList.add(user);
            }
        }
        return userList;
    }

}
