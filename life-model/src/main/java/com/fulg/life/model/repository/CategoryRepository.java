package com.fulg.life.model.repository;

import com.fulg.life.model.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by alessandro.fulgenzi on 30/06/16.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT category FROM Category category WHERE category.supercategory is null")
    List<Category> findRootCategories();

    @Query("SELECT category FROM Category category WHERE category.supercategory.pk = :parentPk")
    List<Category> findByParent(@Param("parentPk") Long parentPk);

    @Query("SELECT category FROM Category category WHERE category.code = :code")
    Category findByCode(@Param("code") String code);

    @Query("SELECT category FROM Category category WHERE category.frequencyType is not null")
    List<Category> findWithFrequency();

}
