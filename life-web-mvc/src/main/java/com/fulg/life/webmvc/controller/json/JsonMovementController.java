package com.fulg.life.webmvc.controller.json;

import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fulg.life.data.*;
import com.fulg.life.data.util.CurrencyUtils;
import com.fulg.life.webmvc.controller.json.data.JsonError;
import com.fulg.life.webmvc.controller.json.data.JsonResult;
import com.fulg.life.webmvc.controller.json.data.JsonSuccess;
import com.fulg.life.webmvc.data.*;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fulg.life.data.ImportBankMovementData;
import com.fulg.life.facade.BankAccountMovementFacade;
import com.fulg.life.facade.DateTimeFacade;
import com.fulg.life.facade.ImportBankAccountMovementFacade;
import com.fulg.life.service.exception.UnauthorizedOperationException;
import com.fulg.life.webmvc.controller.AbstractBaseController;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Controller
@RequestMapping(value = "/movements/json")
public class JsonMovementController extends AbstractBaseController {
    private static final Logger LOG = LoggerFactory.getLogger(JsonMovementController.class);

    @Resource
    BankAccountMovementFacade bankAccountMovementFacade;
    @Resource
    DateTimeFacade dateTimeFacade;
    @Resource
    ImportBankAccountMovementFacade importBankAccountMovementFacade;

    @RequestMapping(value = "/searchAll", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public SearchResult searchAll(HttpServletRequest request, HttpServletResponse response,
                                  @RequestParam(value = "bankAccount") final Long bankAccountPk) throws IOException
    {
        List<BankAccountMovementData> movements = bankAccountMovementFacade.getAll(
                bankAccountPk,
                getCurrentUser(request));
        LOG.info("Search for all movements. Found " + movements.size() + " movements.");

        return buildSearchResult(movements);
    }

    @RequestMapping(value = "/searchByMonth", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public SearchResult search(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam(value = "bankAccount") final Long bankAccountPk,
                               @RequestParam(value = "month") final Integer month, @RequestParam(value = "year") final Integer year)
            throws IOException
    {
        List<BankAccountMovementData> movements = bankAccountMovementFacade.getByMonth(
                bankAccountPk,
                year,
                month,
                getCurrentUser(request));
        LOG.info("Search for movements for [" + month + "/" + year + "], Account [" + bankAccountPk + "]. Found " + movements.size() + " movements.");

        return buildSearchResult(movements);
    }

    @RequestMapping(value = "/searchByText", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public SearchResult searchByText(HttpServletRequest request, HttpServletResponse response,
                                     @RequestBody final SearchForm searchForm)
            throws IOException
    {
        // The Search by Text on Movement page is supposed to pass only
        List<BankAccountMovementData> movements = bankAccountMovementFacade.getByText(
                searchForm.getBankAccount().getPk(),
                searchForm.getDescription(),
                searchForm.getCategory(),
                searchForm.getStartDate(),
                searchForm.getEndDate(),
                searchForm.getInOut(),
                searchForm.getUncategorised(),
                getCurrentUser(request));

        return buildSearchResult(movements);
    }

    @RequestMapping(value = "/searchByTransfer", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public SearchResult searchByTransfer(HttpServletRequest request, HttpServletResponse response,
                                         @RequestBody final SearchTransferForm searchForm)
            throws IOException
    {
        List<BankAccountMovementData> movements =
                bankAccountMovementFacade.getByTransfer(
                        searchForm.getFromTo(),
                        searchForm.getBankAccount(),
                        searchForm.getOtherBankAccount(),
                        searchForm.getStartDate(),
                        searchForm.getEndDate(),
                        getCurrentUser(request)
                );
        LOG.info("Search for Transfers. Found [{}] movements.", movements.size());

        return buildSearchResult(movements);
    }

    private SearchResult buildSearchResult(final List<BankAccountMovementData> movements)
    {
        final SearchResult searchResult = new SearchResult();
        searchResult.setMovements(movements);
        if (CollectionUtils.isNotEmpty(movements))
        {
            final Map<Long, AccountTotals> map = Maps.newHashMap();
            for (final BankAccountMovementData mov : movements)
            {
                if (mov.getBankTransfer() == null)
                {
                    AccountTotals totals = map.get(mov.getBankAccount().getPk());
                    if (totals == null)
                    {
                        totals = new AccountTotals();
                        totals.setAccount(mov.getBankAccount());
                        totals.setFormattedAmountIn(CurrencyUtils.formatDouble(totals.getAmountIn().doubleValue(), mov.getBankAccount().getCurrency()));
                        totals.setFormattedAmountOut(CurrencyUtils.formatDouble(totals.getAmountOut().doubleValue(), mov.getBankAccount().getCurrency()));
                        map.put(mov.getBankAccount().getPk(), totals);
                    }
                    if (mov.getEu().equalsIgnoreCase("E"))
                    {
                        totals.setAmountIn(totals.getAmountIn() + mov.getAmount());
                        totals.setFormattedAmountIn(CurrencyUtils.formatDouble(totals.getAmountIn().doubleValue(), mov.getBankAccount().getCurrency()));
                    } else
                    {
                        totals.setAmountOut(totals.getAmountOut() + mov.getAmount());
                        totals.setFormattedAmountOut(CurrencyUtils.formatDouble(totals.getAmountOut().doubleValue(), mov.getBankAccount().getCurrency()));
                    }
                }
            }
            searchResult.setAccountTotals(Lists.<AccountTotals>newArrayList(map.values()));
        }
        return searchResult;
    }

    @RequestMapping(value = "/addMovement", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<List<BankAccountMovementData>> addMovement(HttpServletRequest request, HttpServletResponse response,
                                                           @RequestBody final MovementForm movementForm) throws IOException
    {
        final FrequencyData freq = movementForm.getFrequency();
        boolean untilDateSet = (movementForm.getUntilDate() != null);
        boolean freqIntervalSet = (freq != null && freq.getFrequencyInterval() > 0);
        boolean freqTypeSet = (freq != null && freq.getFrequencyType() != null);

        try
        {
            if (untilDateSet || freqIntervalSet || freqTypeSet)
            {
                if (!untilDateSet)
                {
                    return new JsonError<List<BankAccountMovementData>>("Choose frequency Interval and Type");
                } else if (!freqIntervalSet)
                {
                    return new JsonError<List<BankAccountMovementData>>("Choose frequency Interval");
                } else if (!freqTypeSet)
                {
                    return new JsonError<List<BankAccountMovementData>>("Choose frequency Type");
                }
                // Frequency
                final List<BankAccountMovementData> movs = bankAccountMovementFacade.insertAll(movementForm.getMovement(), movementForm.getFrequency(), movementForm.getFromDate(), movementForm.getUntilDate(), movementForm.isSkipFirstDate(), getCurrentUser(request));
                return new JsonSuccess<List<BankAccountMovementData>>(movs);
            } else
            {
                // NO Frequency
                final BankAccountMovementData mov = bankAccountMovementFacade.insert(movementForm.getMovement(), getCurrentUser(request));
                return new JsonSuccess<List<BankAccountMovementData>>(Collections.singletonList(mov));
            }
        } catch (UnauthorizedOperationException e)
        {
            throw new IOException(e);
        }
    }

    @RequestMapping(value = "/addMovements", method = RequestMethod.POST)
    @ResponseBody
    public List<BankAccountMovementData> addMovements(HttpServletRequest request, HttpServletResponse response,
                                                      @RequestBody final BankAccountMovements movements) throws IOException
    {
        try
        {
            LOG.info("CREATING [{}] MOVEMENTS", movements.getMovements().size());
            return bankAccountMovementFacade.insertAll(movements.getMovements(), getCurrentUser(request));
        } catch (UnauthorizedOperationException e)
        {
            throw new IOException(e);
        }
    }

    @RequestMapping(value = "/saveMovement", method = RequestMethod.POST)
    @ResponseBody
    public BankAccountMovementData saveMovement(HttpServletRequest request, HttpServletResponse response,
                                                @RequestBody final BankAccountMovementData movement) throws IOException
    {
        try
        {
            LOG.info("UPDATING MOVEMENT [{}]", movement.getDescription());
            BankAccountMovementData returnMovement = bankAccountMovementFacade
                    .update(movement, getCurrentUser(request));
            return returnMovement;
        } catch (UnauthorizedOperationException e)
        {
            throw new IOException(e);
        }
    }

    @RequestMapping(value = "/saveMovements", method = RequestMethod.POST)
    @ResponseBody
    public List<BankAccountMovementData> saveMovements(HttpServletRequest request, HttpServletResponse response,
                                                       @RequestBody final BankAccountMovements movements) throws IOException
    {
        try
        {
            return bankAccountMovementFacade.updateAll(movements.getMovements(), getCurrentUser(request));
        } catch (UnauthorizedOperationException e)
        {
            throw new IOException(e);
        }
    }

    @RequestMapping(value = "/deleteMovement", method = RequestMethod.POST)
    public void deleteMovement(HttpServletRequest request, HttpServletResponse response, @RequestBody final BankAccountMovementData movement)
            throws IOException
    {
        try
        {
            LOG.info("DELETING MOVEMENT [{}]", movement.getDescription());
            bankAccountMovementFacade.delete(movement, getCurrentUser(request));
            return;
        } catch (UnauthorizedOperationException e)
        {
            throw new IOException(e);
        }
    }

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public ImportBankAccountMovementResult submitImport(HttpServletRequest request
            , @RequestBody ImportForm importForm
            , HttpServletResponse response) throws IOException
    {
        Assert.notNull(importForm, "ImportForm cannot be null");
        Assert.notNull(importForm.getBankAccount(), "bankAccount cannot be null");
        Assert.notNull(importForm.getPayload(), "Payload cannot be null");

        List<ImportBankMovementData> movements = importBankAccountMovementFacade.importMovementsFromText(
                importForm.getBankAccount().getPk(),
                importForm.getPayload(), getCurrentUser(request));

        final ImportBankAccountMovementResult result = new ImportBankAccountMovementResult();
        result.setImportMovements(movements);
        result.setExistingMovements(Lists.newArrayList(extractExistingMovements(movements)));

        return result;
    }

    @RequestMapping(value = "/loadImports", method = RequestMethod.POST)
    @ResponseBody
    public ImportBankAccountMovementResult loadImports(HttpServletRequest request
            , @RequestBody ImportForm importForm
            , HttpServletResponse response) throws IOException
    {
        Assert.notNull(importForm, "ImportForm cannot be null");
        Assert.notNull(importForm.getBankAccount(), "bankAccount cannot be null");
        Assert.notNull(importForm.getYear(), "Year cannot be null");
        Assert.notNull(importForm.getMonth(), "Month cannot be null");

        List<ImportBankMovementData> movements = importBankAccountMovementFacade.loadImportMovements(
                importForm.getBankAccount().getPk(),
                importForm.getYear(),
                importForm.getMonth(), getCurrentUser(request));

        final ImportBankAccountMovementResult result = new ImportBankAccountMovementResult();
        result.setImportMovements(movements);
        result.setExistingMovements(Lists.newArrayList(extractExistingMovements(movements)));

        return result;
    }

    @RequestMapping(value = "/availableImports", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public AvailableImportBankMovementResult submitImport(HttpServletRequest request,
                                                          @RequestParam(value = "bankAccount") final Long bankAccountPk,
                                                          @RequestParam(value = "year") final Integer year) throws IOException
    {
        Assert.notNull(bankAccountPk, "bankAccountPk cannot be null");
        Assert.notNull(year, "year cannot be null");

        final List<AvailableImportBankMovementResult.AvailableImportBankMovementItemResult> availableMonths = Lists.newArrayList();
        for (int month = 1; month <= 12; month++)
        {
            final AvailableImportBankMovementResult.AvailableImportBankMovementItemResult item = new AvailableImportBankMovementResult.AvailableImportBankMovementItemResult();
            item.setMonth(dateTimeFacade.getMonthData(month));
            item.setAvailable(importBankAccountMovementFacade.isContentAvailable(bankAccountPk, year, month, getCurrentUser(request)));
            availableMonths.add(item);
        }

        final AvailableImportBankMovementResult result = new AvailableImportBankMovementResult();
        result.setAvailableImportMovements(availableMonths);

        return result;
    }

    private Collection<BankAccountMovementData> extractExistingMovements(List<ImportBankMovementData> movements)
    {
        final Map<Long, BankAccountMovementData> existingMovements = Maps.newHashMap();
        for (final ImportBankMovementData importMov : movements)
        {
            for (final BankAccountMovementData mov : importMov.getMatchingBankAccountMovements())
            {
                existingMovements.put(mov.getPk(), mov);
            }
            for (final BankAccountMovementData mov : importMov.getOtherBankAccountMovements())
            {
                existingMovements.put(mov.getPk(), mov);
            }
        }
        return existingMovements.values();
    }
}
