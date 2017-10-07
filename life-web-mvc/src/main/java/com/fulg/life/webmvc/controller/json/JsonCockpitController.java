package com.fulg.life.webmvc.controller.json;

import com.fulg.life.data.BankAccountMovementData;
import com.fulg.life.facade.BankAccountMovementFacade;
import com.fulg.life.facade.DateTimeFacade;
import com.fulg.life.facade.ImportBankAccountMovementFacade;
import com.fulg.life.facade.data.YearlyMatchesCockpitData;
import com.fulg.life.webmvc.controller.AbstractBaseController;
import com.fulg.life.webmvc.data.ImportForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping(value = "/cockpit/json")
public class JsonCockpitController extends AbstractBaseController {
    private static final Logger LOG = LoggerFactory.getLogger(JsonCockpitController.class);

    @Resource
    BankAccountMovementFacade bankAccountMovementFacade;
    @Resource
    DateTimeFacade dateTimeFacade;
    @Resource
    ImportBankAccountMovementFacade importBankAccountMovementFacade;

    @RequestMapping(value = "/checkMatchingImport", method = RequestMethod.GET)
    @ResponseBody
    public YearlyMatchesCockpitData checkMatchingImport(HttpServletRequest request
            , @RequestParam long bankAccount
            , @RequestParam int year
            , HttpServletResponse response) throws IOException
    {
        Assert.notNull(bankAccount, "bankAccount cannot be null");
        Assert.notNull(year, "year cannot be null");

        final YearlyMatchesCockpitData data = importBankAccountMovementFacade.getYearlyMatchesCockpitData(bankAccount, year, getCurrentUser(request));

        return data;
    }

    @RequestMapping(value = "/getMovementKey", method = RequestMethod.POST)
    @ResponseBody
    public String getMovementKey(HttpServletRequest request
            , @RequestBody BankAccountMovementData mov) throws IOException
    {
        Assert.notNull(mov, "movement cannot be null");

        return importBankAccountMovementFacade.getMovementKey(mov);
    }

    @RequestMapping(value = "/checkMatchingImport", method = RequestMethod.POST)
    @ResponseBody
    public YearlyMatchesCockpitData checkMatchingImport(HttpServletRequest request
            , @RequestBody ImportForm importForm
            , HttpServletResponse response) throws IOException
    {
        Assert.notNull(importForm.getBankAccount(), "bankAccount cannot be null");
        Assert.notNull(importForm.getYear(), "year cannot be null");

        final YearlyMatchesCockpitData data = importBankAccountMovementFacade.getYearlyMatchesCockpitData(importForm.getBankAccount().getPk(), importForm.getYear(), getCurrentUser(request));

        return data;
    }

    @RequestMapping(value = "/checkMatchingImportByMonth", method = RequestMethod.GET)
    @ResponseBody
    public YearlyMatchesCockpitData.MonthlyMatchesCockpitData checkMatchingImportByMonth(HttpServletRequest request
            , @RequestParam long bankAccount
            , @RequestParam int year
            , @RequestParam int month
            , HttpServletResponse response) throws IOException
    {
        Assert.notNull(bankAccount, "bankAccount cannot be null");
        Assert.notNull(year, "year cannot be null");
        Assert.notNull(month, "month cannot be null");

        final YearlyMatchesCockpitData.MonthlyMatchesCockpitData data = importBankAccountMovementFacade.getMonthlyMatchesCockpitData(bankAccount, year, month, getCurrentUser(request));

        return data;
    }

}
