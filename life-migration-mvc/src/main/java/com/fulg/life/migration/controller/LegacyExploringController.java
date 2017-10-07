package com.fulg.life.migration.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fulg.life.migration.dto.LegacyTable;
import com.fulg.life.migration.facade.LegacyFacade;
import com.fulg.life.model.repository.GenericItemRepository;
import com.fulg.life.msaccess.explorer.DatabaseExplorer;

@Controller
@RequestMapping(value = "/explore/legacy")
public class LegacyExploringController extends AbstractBaseController {
    private static final Logger LOG = LoggerFactory.getLogger(LegacyExploringController.class);

    @Autowired
    GenericItemRepository genericItemRepository;

    @Resource(name = "legacyFacade")
    LegacyFacade legacyFacade;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView explore(HttpServletResponse response) throws IOException {
        ModelAndView model = new ModelAndView("exploreLegacy");

        model.addObject("legacyTables", getLegacyTableList());

        return model;
    }

    @RequestMapping(value = "/showTable", method = RequestMethod.GET)
    public ModelAndView showTable(HttpServletResponse response, @RequestParam("tableName") String tableName)
            throws IOException {
        ModelAndView model = new ModelAndView("exploreLegacy");

        model.addObject("legacyTables", getLegacyTableList());
        model.addObject("selectedTable", legacyFacade.getTableInfo(tableName, true));

        return model;
    }

    @RequestMapping(value = "/tableList", produces = "application/json")
    @ResponseBody
    public List<LegacyTable> getTableList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return getLegacyTableList();
    }

    protected List<LegacyTable> getLegacyTableList() {
        List<LegacyTable> list = new ArrayList<LegacyTable>();

        Set<String> tableList = getMSAccessTableList();
        if (tableList != null) {
            for (String tableName : tableList) {
                LegacyTable table = legacyFacade.getTableInfo(tableName, false);
                list.add(table);
            }
        }
        return list;
    }

    private Set<String> getMSAccessTableList() {
        Set<String> tableList = DatabaseExplorer.getTableNames();
        if (tableList == null) {
            tableList = new HashSet<String>();
        }
        return tableList;
    }

}
