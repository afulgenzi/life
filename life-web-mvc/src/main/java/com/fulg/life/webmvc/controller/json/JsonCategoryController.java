package com.fulg.life.webmvc.controller.json;

import com.fulg.life.data.BankAccountMovementData;
import com.fulg.life.data.CategoryAlertData;
import com.fulg.life.data.CategoryData;
import com.fulg.life.data.FrequencyTypeData;
import com.fulg.life.data.tree.CategoryTreeData;
import com.fulg.life.data.tree.InOutCategoryTreeData;
import com.fulg.life.facade.CategoryFacade;
import com.fulg.life.service.exception.UnauthorizedOperationException;
import com.fulg.life.webmvc.controller.AbstractBaseController;
import com.fulg.life.webmvc.controller.json.data.JsonError;
import com.fulg.life.webmvc.controller.json.data.JsonResult;
import com.fulg.life.webmvc.controller.json.data.JsonSuccess;
import com.fulg.life.webmvc.data.CategoryForm;
import com.fulg.life.webmvc.data.MultiAccountSearchByMonthForm;
import com.fulg.life.webmvc.data.MultiAccountSearchForm;
import com.google.common.base.Function;
import com.google.common.collect.Ordering;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by alessandro.fulgenzi on 02/07/16.
 */
@Controller
@RequestMapping(value = "/categories/json")
public class JsonCategoryController extends AbstractBaseController {
    private static final Logger LOG = LoggerFactory.getLogger(JsonCategoryController.class);

    @Resource
    CategoryFacade categoryFacade;

    @RequestMapping(value = "/getAllCategories", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<CategoryData> getAllCategories(HttpServletResponse response) throws IOException
    {
        final List<CategoryData> allCategories = categoryFacade.getAllCategories();
        final List<CategoryData> sortedCategories = Ordering.natural().onResultOf(new Function<CategoryData, String>() {
            @Override
            public String apply(CategoryData cat)
            {
                return cat.getPath();
            }
        }).sortedCopy(allCategories);
        return sortedCategories;
    }

    @RequestMapping(value = "/getCategoryByCode", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public CategoryData getCategoryByCode(HttpServletResponse response,
                                          @RequestParam(value = "categoryCode") final String categoryCode) throws IOException
    {
        return categoryFacade.getCategoryByCode(categoryCode);
    }

    @RequestMapping(value = "/getCategoryTree", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<CategoryTreeData> getCategoryTree(HttpServletRequest request, HttpServletResponse response,
                                                  @RequestParam Boolean selectOnlyLeaves,
                                                  @RequestParam(required = false) String defaultSelectedCategoryCode) throws IOException
    {
        LOG.info("getCategoryTree, QueryString [{}]", request.getQueryString());
        LOG.info("getCategoryTree, selectOnlyLeaves [{}]", selectOnlyLeaves);
        LOG.info("getCategoryTree, defaultSelectedCategoryCode [{}]", defaultSelectedCategoryCode);
        return categoryFacade.getCategoryTree(selectOnlyLeaves != null && selectOnlyLeaves.booleanValue(),
                defaultSelectedCategoryCode);
    }

    @RequestMapping(value = "/getCategoryTreeByMonth", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public List<InOutCategoryTreeData> getCategoryTreeByMonth_POST(HttpServletRequest request,
                                                                   @RequestBody final MultiAccountSearchByMonthForm searchForm) throws IOException
    {
        return categoryFacade.getCategoryTreeByMonth(searchForm.getBankAccounts(), searchForm.getYear(),
                searchForm.getMonth(), getCurrentUser(request));
    }

    @RequestMapping(value = "/getCategoryTreeByText", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public List<InOutCategoryTreeData> getCategoryTreeByText(HttpServletRequest request, HttpServletResponse response,
                                                             @RequestBody final MultiAccountSearchForm searchForm)
            throws IOException
    {
        List<InOutCategoryTreeData> tree = categoryFacade.getCategoryTreeByText(
                searchForm.getBankAccounts(),
                searchForm.getDescription(),
                searchForm.getCategory(),
                searchForm.getStartDate(),
                searchForm.getEndDate(),
                searchForm.getInOut(),
                getCurrentUser(request));

        return tree;
    }

    @RequestMapping(value = "/getAllFrequencyTypes", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<FrequencyTypeData> getFrequencyTypes(HttpServletRequest request) throws IOException
    {
        return categoryFacade.getAllFrequencyTypes();
    }

    @RequestMapping(value = "/insertUpdateCategory", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public JsonResult<CategoryData> insertUpdateCategory(HttpServletRequest request,
                                                         @RequestBody final CategoryForm categoryForm) throws UnauthorizedOperationException
    {
        final CategoryData existingCategory = categoryFacade.getCategoryByCode(categoryForm.getCategory().getCode());
        if (categoryForm.getCategory().getPk() != null)
        {
            if (existingCategory != null && (!existingCategory.getPk().equals(categoryForm.getCategory().getPk())))
            {
                final String errMsg = "Cannot update Category. Another Category with code [" + categoryForm.getCategory().getCode() + "] already exists";
                LOG.info(errMsg);
                return new JsonError<CategoryData>(errMsg);
            }
            LOG.info("Updating Category [{}] under [{}]", categoryForm.getCategory().getCode(),
                    categoryForm.getCategory().getSupercategory());
        } else
        {
            if (existingCategory != null)
            {
                final String errMsg = "Cannot create Category. Another Category with code [" + categoryForm.getCategory().getCode() + "] already exists";
                LOG.info(errMsg);
                return new JsonError<CategoryData>(errMsg);
            }
            LOG.info("Creating Category [{}] under [{}]", categoryForm.getCategory().getCode(),
                    categoryForm.getCategory().getSupercategory());
        }
        final CategoryData categoryData = categoryForm.getCategory();
        LOG.info("Saving category [{}]", categoryData.getCode());
        return new JsonSuccess<CategoryData>(categoryFacade.save(categoryData));
    }

    @RequestMapping(value = "/deleteCategory", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public CategoryData deleteCategory(HttpServletRequest request,
                                       @RequestBody final CategoryForm categoryForm) throws UnauthorizedOperationException
    {
        final CategoryData categoryData = categoryForm.getCategory();
        if (categoryData.getPk() != null)
        {
            LOG.info("Deleting category [{}]", categoryData.getCode());
            categoryFacade.delete(categoryData);
        } else
        {
            LOG.info("Cannot delete unexisting category [{}]", categoryForm.getCategory().getCode());
        }
        return categoryData;
    }

    @RequestMapping(value = "/getCategoryAlerts", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<CategoryAlertData> getCategoryAlerts(final HttpServletRequest request,
                                                     final HttpServletResponse response) throws IOException
    {
        final List<CategoryAlertData> categories = categoryFacade.getCategoryAlerts(getCurrentUser(request));
//        final List<CategoryData> sortedCategories = Ordering.natural().onResultOf(new Function<CategoryData, String>() {
//            @Override
//            public String apply(CategoryData cat)
//            {
//                return cat.getPath();
//            }
//        }).sortedCopy(allCategories);
        return categories;
    }
}
