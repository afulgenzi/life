package com.fulg.life.facade.impl;

import com.fulg.life.data.BankAccountMovementData;
import com.fulg.life.data.CategoryAlertData;
import com.fulg.life.data.CategoryData;
import com.fulg.life.data.FrequencyTypeData;
import com.fulg.life.data.tree.CategoryTreeData;
import com.fulg.life.data.tree.InOutCategoryTreeData;
import com.fulg.life.data.util.CurrencyUtils;
import com.fulg.life.data.util.DateUtils;
import com.fulg.life.facade.CategoryFacade;
import com.fulg.life.facade.converter.*;
import com.fulg.life.facade.converter.reverse.CategoryReverseConverter;
import com.fulg.life.model.entities.*;
import com.fulg.life.service.BankAccountMovementService;
import com.fulg.life.service.CategoryService;
import com.fulg.life.service.FrequencyTypeService;
import com.fulg.life.service.exception.UnauthorizedOperationException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by alessandro.fulgenzi on 02/07/16.
 */
public class CategoryFacadeImpl implements CategoryFacade {
    private static Logger LOG = LoggerFactory.getLogger(CurrencyFacadeImpl.class);

    private static final Double ZERO = Double.valueOf(0d);
    private static final DateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");

    @Resource
    CategoryService categoryService;
    @Resource
    BankAccountMovementService bankAccountMovementService;
    @Resource
    FrequencyTypeService frequencyTypeService;
    @Resource
    CategoryConverter categoryConverter;
    @Resource
    CurrencyConverter currencyConverter;
    @Resource
    CategoryTreeConverter categoryTreeConverter;
    @Resource
    CategoryReverseConverter categoryReverseConverter;
    @Resource
    BankAccountMovementConverter movementConverter;
    @Resource
    FrequencyTypeConverter frequencyTypeConverter;

    @Override
    public List<CategoryData> getAllCategories()
    {
        // get movements
        List<Category> categories = categoryService.getAllCategories();

        final List<CategoryData> categoryDataList = Lists.newArrayList();
        for (final Category cat : categories)
        {
            final CategoryData catData = categoryConverter.convert(cat);
            if (cat.getSupercategory() != null)
            {
                catData.setSupercategory(categoryConverter.convert(cat.getSupercategory()));
            }
            if (cat.getSubcategories() != null)
            {
                for (final Category child:cat.getSubcategories()){
                    catData.getSubcategories().add(categoryConverter.convert(child.getSupercategory()));
                }
            }
            categoryDataList.add(catData);
        }

        return categoryDataList;
    }

    @Override
    public CategoryData getCategoryByCode(final String code)
    {
        // get movements
        Category category = categoryService.getCategoryByCode(code);
        return categoryConverter.convert(category);
    }

    @Override
    public List<CategoryTreeData> getCategoryTree(final boolean selectOnlyLeaves, final String defaultSelectedCategoryCode)
    {
        // get movements
        List<Category> itemList = categoryService.getRootCategories();

        // prepare output
        return convertCategoryListToTree(itemList, selectOnlyLeaves, defaultSelectedCategoryCode);
    }

    @Override
    public List<InOutCategoryTreeData> getCategoryTreeByMonth(final List<Long> bankAccounts, final int year, final int month, final User user)
    {
        final List<BankAccountMovement> movementList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(bankAccounts))
        {
            for (Long bankAccountPk : bankAccounts)
            {
                movementList.addAll(bankAccountMovementService.getByMonth(bankAccountPk, year, month, user));
            }
        }

        return getCategoryTree(bankAccounts, groupMovementsByCategory(movementList));
    }

    /**
     * Get By Text
     */
    @Override
    public List<InOutCategoryTreeData> getCategoryTreeByText(final List<Long> bankAccounts, final String text,
                                                             final CategoryData category, final Date startDate,
                                                             final Date endDate, String inOut, final User user)
    {
        final List<BankAccountMovement> movementList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(bankAccounts))
        {
            for (Long bankAccountPk : bankAccounts)
            {
                final Date actualStartDate = startDate != null ? startDate : DateUtils.getDefaultStartDate();
                final Date actualEndDate = endDate != null ? endDate : DateUtils.getDefaultEndDate();
                final String actualText = text == null ? "" : text;
                final Long categoryPk = category == null ? null : category.getPk();
                // get movements
                movementList.addAll(bankAccountMovementService.getByDescription(bankAccountPk,
                        actualText,
                        categoryPk, actualStartDate, actualEndDate, inOut, user));
            }
        }

        return getCategoryTree(bankAccounts, groupMovementsByCategory(movementList));
    }

    @Override
    public List<FrequencyTypeData> getAllFrequencyTypes()
    {
        // get movements
        List<FrequencyType> frequencyTypes = frequencyTypeService.getAll();

        return frequencyTypeConverter.convertAll(frequencyTypes);
    }

    protected Map<Long, List<BankAccountMovement>> groupMovementsByCategory(final List<BankAccountMovement> movements)
    {
        final Map<Long, List<BankAccountMovement>> map = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(movements))
        {
            for (final BankAccountMovement mov : movements)
            {
                if (mov.getCategory() != null)
                {
                    final Long key = mov.getCategory().getPk();
                    if (map.get(key) == null)
                    {
                        map.put(key, Lists.<BankAccountMovement>newArrayList());
                    }
                    map.get(key).add(mov);
                } else
                {
                    LOG.warn("Skipping uncategorised movement [{}], [{}]", mov.getPk(), mov.getDescription());
                }
            }
        }
        return map;
    }

    public List<InOutCategoryTreeData> getCategoryTree(final List<Long> bankAccounts, final Map<Long, List<BankAccountMovement>> movMap)
    {
        // get movements
        List<Category> itemList = categoryService.getRootCategories();

        // prepare output
        return convertCategoryListToTree(itemList, movMap);
    }

    @Override
    public CategoryData save(CategoryData categoryData)
            throws UnauthorizedOperationException
    {
        final Category superCategory = categoryData.getSupercategory() == null ? null : categoryReverseConverter.convert(categoryData.getSupercategory());
        Category item = categoryReverseConverter.convert(categoryData);
        item.setSupercategory(superCategory);
        item = categoryService.save(item);
        return categoryConverter.convert(item);
    }

    @Override
    public void delete(CategoryData categoryData) throws UnauthorizedOperationException
    {
        Category cat = categoryService.getCategoryByCode(categoryData.getCode());
        categoryService.delete(cat);
        cat = categoryService.getCategoryByCode(categoryData.getCode());
        LOG.info("Category after deletion [{}]", cat);
    }

    @Override
    public List<CategoryAlertData> getCategoryAlerts(final User user)
    {
        final List<CategoryAlertData> alerts = Lists.newArrayList();
        final List<Category> categoriwHavingFrequency = categoryService.getCategoriesHavingFrequency();
        if (CollectionUtils.isNotEmpty(categoriwHavingFrequency))
        {
            for (Category cat : categoriwHavingFrequency)
            {
                final CategoryData categoryData = categoryConverter.convert(cat);
                final BankAccountMovementData latestTransactionByCategoryData = movementConverter.convert(bankAccountMovementService.getLatestByCategory(cat, user));
                final CategoryAlertData alert = new CategoryAlertData();
                alert.setCategory(categoryData);
                alert.setLatestMovement(latestTransactionByCategoryData);
                alert.setAlertLevel(calculateCategoryLevel(categoryData, latestTransactionByCategoryData));
                alerts.add(alert);
            }
        }
        return alerts;
    }

    private int calculateCategoryLevel(final CategoryData category,
                                       BankAccountMovementData latestTransactionByCategoryData)
    {
        if (isCategoryComplete(category, latestTransactionByCategoryData))
        {
            return 5;
        } else
        {
            final int differenceInMonths = calculateDifferenceInMonths(category, latestTransactionByCategoryData);
            if (differenceInMonths <= 0)
            {
                return 0;
            } else if (differenceInMonths < 3)
            {
                return 1;
            } else if (differenceInMonths < 6)
            {
                return 2;
            } else if (differenceInMonths < 12)
            {
                return 3;
            } else
            {
                return 4;
            }
        }
    }

    private boolean isCategoryComplete(final CategoryData category,
                                       BankAccountMovementData latestTransactionByCategoryData)
    {
        if (latestTransactionByCategoryData != null && category.getFrequency().getFrequencyEndDate() != null)
        {
            final Date nextDate = DateUtils.calculateNextDate(latestTransactionByCategoryData.getDate(),
                    category.getFrequency(), category.getFrequency().getFrequencyEndDate());
            if (nextDate == null)
            {
                return true;
            }
        }
        return false;
    }

    private int calculateDifferenceInMonths(final CategoryData category, BankAccountMovementData transaction)
    {
        if (transaction == null)
        {
            return 0;
        } else
        {
            final Date now = new Date();

            if (now.after(transaction.getDate()))
            {
                return 0;
            } else
            {
                final long differenceInMilliseconds = transaction.getDate().getTime() - now.getTime();

                // User 1 month ~ 30 days
                final long differenceInMonths = differenceInMilliseconds / 1000 / 60 / 60 / 24 / 30;
                return (int) differenceInMonths;
            }
        }
    }

    protected List<CategoryTreeData> convertCategoryListToTree(List<Category> inputItems,
                                                               final boolean selectOnlyLeaves,
                                                               final String defaultSelectedCategoryCode)
    {
        final List<CategoryTreeData> rootCategories = Lists.newArrayList();

        if (CollectionUtils.isNotEmpty(inputItems))
        {
            for (Category cat : inputItems)
            {
                rootCategories.add(convertCategoryToTree(cat, selectOnlyLeaves, defaultSelectedCategoryCode));
            }

            // sort
            Collections.sort(rootCategories);
        }
        return rootCategories;
    }

    protected CategoryTreeData convertCategoryToTree(final Category category, final boolean selectOnlyLeaves, final String defaultSelectedCategoryCode)
    {
        final CategoryTreeData categoryTreeData = categoryTreeConverter.convert(category);
        if (defaultSelectedCategoryCode != null && defaultSelectedCategoryCode.equals(category.getCode()))
        {
            if (categoryTreeData.getState()==null){
                categoryTreeData.setState(new CategoryTreeData.CategoryState());
            }
            categoryTreeData.getState().setSelected(true);
        }
        if (CollectionUtils.isNotEmpty(category.getSubcategories()))
        {
            if (selectOnlyLeaves)
            {
                categoryTreeData.setSelectable(false);
            }
            categoryTreeData.setNodes(Lists.<CategoryTreeData>newArrayList());
            for (Category child : category.getSubcategories())
            {
                categoryTreeData.getNodes().add(convertCategoryToTree(child, selectOnlyLeaves, defaultSelectedCategoryCode));
            }
        }
        return categoryTreeData;
    }

    protected List<InOutCategoryTreeData> convertCategoryListToTree(List<Category> inputItems, final Map<Long, List<BankAccountMovement>> movMap)
    {
        List<InOutCategoryTreeData> items = Lists.newArrayList();

        if (CollectionUtils.isNotEmpty(inputItems))
        {
            for (Category cat : inputItems)
            {
                items.add(convertCategoryToTree(cat, movMap));
            }

            // sort
            Collections.sort(items);
        }

        return items;
    }

    protected InOutCategoryTreeData convertCategoryToTree(final Category category, final Map<Long, List<BankAccountMovement>> movMap)
    {
        // Convert Category
        final InOutCategoryTreeData categoryTreeData = new InOutCategoryTreeData();
        categoryTreeConverter.convert(category, categoryTreeData);

        // Children
        if (CollectionUtils.isNotEmpty(category.getSubcategories()))
        {
            categoryTreeData.setNodes(Lists.<CategoryTreeData>newArrayList());
            for (Category child : category.getSubcategories())
            {
                final InOutCategoryTreeData subCat = convertCategoryToTree(child, movMap);
                categoryTreeData.getNodes().add(subCat);
                // Totale
                categoryTreeData.setTotIn(categoryTreeData.getTotIn() + subCat.getTotIn());
                categoryTreeData.setTotOut(categoryTreeData.getTotOut() + subCat.getTotOut());
                if (subCat.getCurrency() != null)
                {
                    categoryTreeData.setCurrency(subCat.getCurrency());
                }
            }
        }

        // Totals
        addMovementsToCategory(categoryTreeData, movMap);
        if (!ZERO.equals(categoryTreeData.getTotIn()) || !ZERO.equals(categoryTreeData.getTotOut()))
        {
            setTags(categoryTreeData);
            if (CollectionUtils.isNotEmpty(categoryTreeData.getNodes()))
            {
                for (final CategoryTreeData node : categoryTreeData.getNodes())
                {
                    if (node instanceof InOutCategoryTreeData)
                    {
                        final InOutCategoryTreeData inOutNode = (InOutCategoryTreeData) node;
                        setTags(inOutNode);
                    }
                }
            }
        }

        return categoryTreeData;
    }

    protected void addMovementsToCategory(final InOutCategoryTreeData categoryTreeData, final Map<Long, List<BankAccountMovement>> movMap)
    {
        if (movMap.get(categoryTreeData.getPk()) != null)
        {
            for (final BankAccountMovement mov : movMap.get(categoryTreeData.getPk()))
            {
                addMovementToCategory(categoryTreeData, mov);
            }
        }
    }

    protected void addMovementToCategory(final InOutCategoryTreeData categoryTreeData, final BankAccountMovement mov)
    {
        LOG.info("Adding movement [{}], to Category [{}]", mov.getDescription(), categoryTreeData.getText());
        InOutCategoryTreeData movementBankAccountTree = null;
        if (CollectionUtils.isNotEmpty(categoryTreeData.getNodes()))
        {
            int ind = 0;
            while (ind < categoryTreeData.getNodes().size() && movementBankAccountTree == null)
            {
                final CategoryTreeData node = categoryTreeData.getNodes().get(ind);
                if (node instanceof InOutCategoryTreeData)
                {
                    final InOutCategoryTreeData baTree = (InOutCategoryTreeData) node;
                    if (baTree.getPk().equals(mov.getBankAccount().getPk()))
                    {
                        movementBankAccountTree = baTree;
                    }
                }
                ind++;
            }
        }
        if (movementBankAccountTree == null)
        {
            movementBankAccountTree = buildInOutCategoryTreeDataForBankAccount(mov.getBankAccount());
            addChild(categoryTreeData, movementBankAccountTree);
        }
        InOutCategoryTreeData inOutMov = buildInOutCategoryTreeDataForMovement(mov);
        addChild(movementBankAccountTree, inOutMov);
        if ("E".equals(mov.getEu()))
        {
            // IN
            movementBankAccountTree.setTotIn(movementBankAccountTree.getTotIn() + mov.getAmount());
            categoryTreeData.setTotIn(categoryTreeData.getTotIn() + mov.getAmount());
        } else
        {
            // OUT
            movementBankAccountTree.setTotOut(movementBankAccountTree.getTotOut() + mov.getAmount());
            categoryTreeData.setTotOut(categoryTreeData.getTotOut() + mov.getAmount());
        }
        movementBankAccountTree.setCurrency(currencyConverter.convert(mov.getBankAccount().getCurrency()));
        categoryTreeData.setCurrency(currencyConverter.convert(mov.getBankAccount().getCurrency()));
    }

    private void setTags(InOutCategoryTreeData node)
    {
        if (CollectionUtils.isEmpty(node.getTags()))
        {
            if (!ZERO.equals(node.getTotIn()) || !ZERO.equals(node.getTotOut()))
            {
                node.getTags().add("Out " + CurrencyUtils.formatDouble(node.getTotOut(), node.getCurrency()));
                node.getTags().add("In " + CurrencyUtils.formatDouble(node.getTotIn(), node.getCurrency()));
            }
        }
    }

    protected void addChild(final CategoryTreeData parent, final CategoryTreeData child)
    {
        if (parent.getNodes() == null)
        {
            parent.setNodes(Lists.<CategoryTreeData>newArrayList());
        }
        parent.getNodes().add(child);
    }

    protected InOutCategoryTreeData buildInOutCategoryTreeDataForBankAccount(final BankAccount ba)
    {
        final InOutCategoryTreeData node = new InOutCategoryTreeData();
        node.setPk(ba.getPk());
        node.setText("Bank Account --> [" + ba.getDisplayName() + "]");
        node.setCurrency(currencyConverter.convert(ba.getCurrency()));
        return node;
    }


    protected InOutCategoryTreeData buildInOutCategoryTreeDataForMovement(final BankAccountMovement mov)
    {
        final InOutCategoryTreeData node = new InOutCategoryTreeData();
        node.setPk(mov.getPk());
        node.setText("Transaction --> [" + mov.getDescription() + "], [" + dateFormatter.format(mov.getDate()) + "]");
        if ("E".equals(mov.getEu()))
        {
            // IN
            node.setTotIn(mov.getAmount());
        } else
        {
            // OUT
            node.setTotOut(mov.getAmount());
        }
        node.setCurrency(currencyConverter.convert(mov.getBankAccount().getCurrency()));
        setTags(node);
        return node;
    }
}
