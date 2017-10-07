package com.fulg.life.service.impl;

import com.fulg.life.model.entities.Category;
import com.fulg.life.model.repository.CategoryRepository;
import com.fulg.life.service.CategoryService;
import com.fulg.life.service.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Properties;

/**
 * Created by alessandro.fulgenzi on 30/06/16.
 */
@Service
public class DefaultCategoryService implements CategoryService {
    @SuppressWarnings("unused")
    private static Logger LOG = LoggerFactory.getLogger(DefaultCategoryService.class);

    private static final String PATH_DELIMITER = " -> ";

    @Resource
    private CategoryRepository categoryRepository;
    @Resource
    private SessionService sessionService;
    @Resource(name = "lifeServiceProperties")
    private Properties properties;

    @Override
    public Category getOne(Long pk)
    {
        return categoryRepository.findOne(pk);
    }

    @Override
    public List<Category> getAllCategories()
    {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> getRootCategories()
    {
        return categoryRepository.findRootCategories();
    }

    public Category getCategoryByCode(String code)
    {
        return categoryRepository.findByCode(code);
    }


    @Override
    public Category save(Category eu)
    {
        categoryRepository.save(eu);
        return eu;
    }

    @Override
    public String getCategoryPath(Category cat)
    {
        String path = "";
        if (cat != null)
        {
            path = cat.getTitle();
            cat = cat.getSupercategory();
            while (cat != null)
            {
                path = cat.getTitle() + PATH_DELIMITER + path;
                cat = cat.getSupercategory();
            }
        }
        return path;
    }

    @Override
    public List<Category> getCategoriesHavingFrequency()
    {
        return categoryRepository.findWithFrequency();
    }

    @Override
    public void delete(Category item)
    {
        categoryRepository.delete(item);
    }

}
