package com.fulg.life.migration.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fulg.life.migration.dto.NewModelTable;
import com.fulg.life.migration.exception.BusinessException;
import com.fulg.life.migration.facade.NewModelFacade;

@Controller
@RequestMapping(value = "/explore/newModel")
public class NewModelExploringController extends AbstractBaseController {
    private static final Logger LOG = LoggerFactory.getLogger(NewModelExploringController.class);

    @Resource(name = "newModelFacade")
    NewModelFacade newModelFacade;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView explore(HttpServletResponse response) throws IOException, BusinessException {
        ModelAndView model = new ModelAndView("exploreNewModel");

        model.addObject("NewModelTables", getNewModelTableList());

        return model;
    }

    @RequestMapping(value = "/showTable", method = RequestMethod.GET)
    public ModelAndView showTable(HttpServletResponse response, @RequestParam("tableName") String tableName)
            throws IOException, BusinessException {
        ModelAndView model = new ModelAndView("exploreNewModel");

        model.addObject("NewModelTables", getNewModelTableList());
        model.addObject("selectedTable", newModelFacade.getTableInfo(tableName, true));

        return model;
    }

    @RequestMapping(value = "/tableList", produces = "application/json")
    @ResponseBody
    public List<NewModelTable> getTableList(HttpServletRequest request, HttpServletResponse response)
            throws IOException, BusinessException {
        return getNewModelTableList();
    }

    private List<NewModelTable> getNewModelTableList() throws BusinessException {
        List<NewModelTable> list = new ArrayList<NewModelTable>();

        List<String> tableList = newModelFacade.getEntityNames();
        if (tableList != null) {
            for (String tableName : tableList) {
                NewModelTable table = newModelFacade.getTableInfo(tableName, false);
                list.add(table);
            }
        }
        return list;
    }

}
