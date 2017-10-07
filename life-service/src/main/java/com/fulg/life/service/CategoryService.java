package com.fulg.life.service;

import com.fulg.life.model.entities.Category;

import java.util.List;

/**
 * Created by alessandro.fulgenzi on 30/06/16.
 */
public interface CategoryService extends ItemService {

    List<Category> getAllCategories();

    List<Category> getRootCategories();

    Category getCategoryByCode(String code);

    void delete(Category cat);

    Category save(Category cat);

    String getCategoryPath(Category cat);

    List<Category> getCategoriesHavingFrequency();
}
