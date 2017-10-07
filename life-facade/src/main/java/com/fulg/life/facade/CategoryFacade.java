package com.fulg.life.facade;

import com.fulg.life.data.CategoryAlertData;
import com.fulg.life.data.CategoryData;
import com.fulg.life.data.FrequencyTypeData;
import com.fulg.life.data.tree.CategoryTreeData;
import com.fulg.life.data.tree.InOutCategoryTreeData;
import com.fulg.life.model.entities.User;
import com.fulg.life.service.exception.UnauthorizedOperationException;

import java.util.Date;
import java.util.List;

/**
 * Created by alessandro.fulgenzi on 02/07/16.
 */
public interface CategoryFacade {

    List<CategoryData> getAllCategories();

    CategoryData getCategoryByCode(String code);

    List<CategoryTreeData> getCategoryTree(boolean selectOnlyLeaves, String defaultSelectedCategoryCode);

    List<InOutCategoryTreeData> getCategoryTreeByMonth(final List<Long> bankAccounts, final int year, final int month, final User user);

    List<InOutCategoryTreeData> getCategoryTreeByText(final List<Long> bankAccounts, final String text,
                                                      final CategoryData category, final Date startDate,
                                                      final Date endDate, String inOut, final User user);

    List<FrequencyTypeData> getAllFrequencyTypes();

    CategoryData save(CategoryData movement)
            throws UnauthorizedOperationException;

    void delete(CategoryData movement) throws UnauthorizedOperationException;

    List<CategoryAlertData> getCategoryAlerts(User user);
}
