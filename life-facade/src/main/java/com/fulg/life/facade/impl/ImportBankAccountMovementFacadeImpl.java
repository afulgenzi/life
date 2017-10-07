package com.fulg.life.facade.impl;

import com.fulg.life.data.BankAccountData;
import com.fulg.life.data.BankAccountMovementData;
import com.fulg.life.data.ImportBankMovementData;
import com.fulg.life.data.MatchingMovementsData;
import com.fulg.life.data.util.DateUtils;
import com.fulg.life.facade.BankAccountMovementFacade;
import com.fulg.life.facade.DateTimeFacade;
import com.fulg.life.facade.ImportBankAccountMovementFacade;
import com.fulg.life.facade.converter.BankAccountConverter;
import com.fulg.life.facade.data.YearlyMatchesCockpitData;
import com.fulg.life.facade.strategy.BankAccountMovementParseStrategy;
import com.fulg.life.facade.strategy.BankMovementToMapStrategy;
import com.fulg.life.facade.strategy.BankTransferIdentifierStrategy;
import com.fulg.life.facade.strategy.FindMatchingMovementsStrategy;
import com.fulg.life.model.entities.BankAccount;
import com.fulg.life.model.entities.ImportBankMovements;
import com.fulg.life.model.entities.User;
import com.fulg.life.service.BankAccountService;
import com.fulg.life.service.ImportBankMovementsService;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ImportBankAccountMovementFacadeImpl implements ImportBankAccountMovementFacade {
    @SuppressWarnings("unused")
    private static Logger LOG = LoggerFactory.getLogger(ImportBankAccountMovementFacadeImpl.class);

    // Facades
    @Resource
    BankAccountMovementFacade bankAccountMovementFacade;
    @Resource
    DateTimeFacade dateTimeFacade;

    // Services
    @Resource
    BankAccountService bankAccountService;
    @Resource
    ImportBankMovementsService importBankMovementsService;

    // Strategies
    @Resource
    BankAccountMovementParseStrategy bankAccountMovementParseStrategy;
    @Resource
    BankTransferIdentifierStrategy bankTransferIdentifierStrategy;
    @Resource
    FindMatchingMovementsStrategy findMatchingMovementsStrategy;
    @Resource
    BankMovementToMapStrategy bankMovementToMapStrategy;

    // Converters
    @Resource
    BankAccountConverter bankAccountConverter;

    /**
     * Immport Movements By Text
     */
    @Override
    public List<ImportBankMovementData> importMovementsFromText(final Long bankAccountPk, final String text, final User user)
    {
        List<ImportBankMovementData> result = parseImportMovementsFromText(bankAccountPk, text, user);

        final Date minDate = getMinMaxDate(result, MIN_MAX.MIN);
        final Date maxDate = getMinMaxDate(result, MIN_MAX.MAX);
        LOG.info("MIN DATE: [{}]", minDate);
        LOG.info("MAX DATE: [{}]", maxDate);
        createAndSaveImportBankMovements(bankAccountPk, text, minDate, maxDate, user);

        return result;
    }

    @Override
    public List<ImportBankMovementData> loadImportMovements(Long bankAccountPk, int year, int month, User user)
    {
        ImportBankMovements movements = importBankMovementsService.getByMonth(bankAccountPk, year, month, user);

        return parseImportMovementsFromText(bankAccountPk, movements.getMovementsText(), user);
    }

    @Override
    public YearlyMatchesCockpitData getYearlyMatchesCockpitData(Long bankAccount, int year, User user)
    {
        final YearlyMatchesCockpitData yearlyData = new YearlyMatchesCockpitData();
        yearlyData.setYear(year);
        int countMatched = 0;
        int countUnmatched = 0;
        int countPartiallyMatched = 0;
        int countUnavailable = 0;
        final Stopwatch yearStopwatch = new Stopwatch().start();
        for (int month = 1; month <= 12; month++)
        {
            final Stopwatch monthStopwatch = new Stopwatch().start();
            final YearlyMatchesCockpitData.MonthlyMatchesCockpitData monthlyData = getMonthlyMatchesCockpitData(bankAccount, year, month, user);
            monthStopwatch.stop();
            LOG.info("getMonthlyMatchesCockpitData (1) [{}/{}] -> [{}]", month, year, monthStopwatch.toString());
            monthStopwatch.reset();
            monthStopwatch.start();
            if (monthlyData != null)
            {
                if (YearlyMatchesCockpitData.ImportCockpitStatus.MATCHED.equals(monthlyData.getStatus()))
                {
                    countMatched++;
                } else if (YearlyMatchesCockpitData.ImportCockpitStatus.UNMATCHED.equals(monthlyData.getStatus()))
                {
                    countUnmatched++;
                } else if (YearlyMatchesCockpitData.ImportCockpitStatus.PARTIALLY_MATCHED.equals(monthlyData.getStatus()))
                {
                    countPartiallyMatched++;
                } else if (YearlyMatchesCockpitData.ImportCockpitStatus.UNAVAILABLE.equals(monthlyData.getStatus()))
                {
                    countUnavailable++;
                }
                yearlyData.getMonthlyMatches().add(monthlyData);
            }
            monthStopwatch.stop();
            LOG.info("getYearlyMatchesCockpitData (2) [{}/{}] -> [{}]", month, year, monthStopwatch.toString());
        }
        yearStopwatch.stop();
        LOG.info("getYearlyMatchesCockpitData [{}] -> [{}]", year, yearStopwatch.toString());
        if (countMatched == 0 && countUnmatched == 0 && countPartiallyMatched == 0 && countUnavailable > 0)
        {
            yearlyData.setStatus(YearlyMatchesCockpitData.ImportCockpitStatus.UNAVAILABLE);
        } else if (countMatched > 0 && countUnmatched == 0 && countPartiallyMatched == 0 && countUnavailable == 0)
        {
            yearlyData.setStatus(YearlyMatchesCockpitData.ImportCockpitStatus.MATCHED);
        } else if (countMatched == 0 && countUnmatched > 0 && countPartiallyMatched == 0 && countUnavailable == 0)
        {
            yearlyData.setStatus(YearlyMatchesCockpitData.ImportCockpitStatus.UNMATCHED);
        } else
        {
            yearlyData.setStatus(YearlyMatchesCockpitData.ImportCockpitStatus.PARTIALLY_MATCHED);
        }
        return yearlyData;
    }

    @Override
    public YearlyMatchesCockpitData.MonthlyMatchesCockpitData getMonthlyMatchesCockpitData(Long bankAccount, int year, int month, User user)
    {
        final YearlyMatchesCockpitData.MonthlyMatchesCockpitData monthlyData = new YearlyMatchesCockpitData.MonthlyMatchesCockpitData();
        monthlyData.setMonth(dateTimeFacade.getMonthData(month));

        ImportBankMovements movements = importBankMovementsService.getByMonth(bankAccount, year, month, user);
        if (movements != null)
        {
            final List<BankAccountMovementData> importMovs = getImportMovInnerMovements(bankAccount, movements.getMovementsText(), user);
            final List<BankAccountMovementData> existingMovs = bankAccountMovementFacade.getByMonth(bankAccount, year, month, user);

            populateMatchingMovs(monthlyData, importMovs, existingMovs);
        } else
        {
            monthlyData.setStatus(YearlyMatchesCockpitData.ImportCockpitStatus.UNAVAILABLE);
        }
        return monthlyData;
    }

    protected void populateMatchingMovs(final YearlyMatchesCockpitData.MonthlyMatchesCockpitData monthlyData, final List<BankAccountMovementData> importMovs, final List<BankAccountMovementData> existingMovs)
    {
        int countMatched = 0;
        int countOnlyImport = 0;
        int countOnlyExisting = 0;
        int countDifferentDescription = 0;

        List<BankAccountMovementData> residualExistingMovs = existingMovs;

        // Iterating ImportMov
        if (CollectionUtils.isNotEmpty(importMovs))
        {
            final Map<String, List<BankAccountMovementData>> existingMovsMap = bankMovementToMapStrategy.asMap(existingMovs);
            for (final BankAccountMovementData importMov : importMovs)
            {
                final BankAccountMovementData existingMov = bankMovementToMapStrategy.popCorrespondingMovement(importMov, existingMovsMap);
                if (existingMov != null)
                {
                    countMatched++;

                    if (!importMov.getDescription().equalsIgnoreCase(existingMov.getDescription()))
                    {
                        countDifferentDescription++;
                    }

                    monthlyData.getDailyMatches().add(buildMatchToMonthlyData(importMov, existingMov));
                } else
                {
                    countOnlyImport++;

                    monthlyData.getDailyMatches().add(buildMatchToMonthlyData(importMov, null));
                }
            }
            residualExistingMovs = bankMovementToMapStrategy.asList(existingMovsMap);
        }

        // Iterating ExistingMov
        if (CollectionUtils.isNotEmpty(residualExistingMovs))
        {
            for (final BankAccountMovementData existingMov : residualExistingMovs)
            {
                countOnlyExisting++;

                monthlyData.getDailyMatches().add(buildMatchToMonthlyData(null, existingMov));
            }
        }

        if (countOnlyImport == 0 && countOnlyExisting == 0)
        {
            if (countDifferentDescription > 0)
            {
                monthlyData.setStatus(YearlyMatchesCockpitData.ImportCockpitStatus.PARTIALLY_MATCHED);
            }
            else
            {
                monthlyData.setStatus(YearlyMatchesCockpitData.ImportCockpitStatus.MATCHED);
            }
        } else if (countMatched > 0)
        {
            monthlyData.setStatus(YearlyMatchesCockpitData.ImportCockpitStatus.PARTIALLY_MATCHED_SOME_MISSING);
        } else
        {
            monthlyData.setStatus(YearlyMatchesCockpitData.ImportCockpitStatus.UNMATCHED);
        }
    }

    private YearlyMatchesCockpitData.DailyMatchesCockpitData buildMatchToMonthlyData(final BankAccountMovementData importMov, final BankAccountMovementData existingMov)
    {
        final YearlyMatchesCockpitData.DailyMatchesCockpitData match = new YearlyMatchesCockpitData.DailyMatchesCockpitData();
        match.setImportMov(importMov);
        match.setExistingMov(existingMov);
        if (existingMov != null)
        {
            match.setDate(existingMov.getDate());
            match.setAmount(existingMov.getAmount());
            match.setFormattedAmount(existingMov.getFormattedAmount());
            match.setEu(existingMov.getEu());
        } else
        {
            match.setDate(importMov.getDate());
            match.setAmount(importMov.getAmount());
            match.setFormattedAmount(importMov.getFormattedAmount());
            match.setEu(importMov.getEu());
        }
        if (importMov != null && existingMov != null)
        {
            if (importMov.getDescription().equalsIgnoreCase(existingMov.getDescription()))
            {
                match.setStatus(YearlyMatchesCockpitData.ImportCockpitStatus.MATCHED);
            } else
            {
                match.setStatus(YearlyMatchesCockpitData.ImportCockpitStatus.PARTIALLY_MATCHED);
            }
        } else
        {
            match.setStatus(YearlyMatchesCockpitData.ImportCockpitStatus.UNMATCHED);
        }
        return match;
    }

    protected List<BankAccountMovementData> getImportMovInnerMovements(Long bankAccount, String text, User user)
    {
        final List<BankAccountMovementData> result = Lists.newArrayList();

        final List<ImportBankMovementData> importMovs = parseImportMovementsFromText(bankAccount, text, user);
        if (CollectionUtils.isNotEmpty(importMovs))
        {
            for (ImportBankMovementData importMov : importMovs)
            {
                result.add(importMov.getImportedBankAccountMovement());
            }
        }

        return result;
    }

    @Override
    public Boolean isContentAvailable(Long bankAccountPk, int year, int month, User user)
    {
        ImportBankMovements movements = importBankMovementsService.getByMonth(bankAccountPk, year, month, user);

        return (movements != null);
    }

    public void createAndSaveImportBankMovements(final Long bankAccountPk, final String text, final Date minDate, final Date maxDate, final User user)
    {
        final BankAccount bankAccount = bankAccountService.getByPk(bankAccountPk);

        final Integer month = Integer.valueOf(DateUtils.getMonth(minDate)) + 1;
        final Integer year = Integer.valueOf(DateUtils.getYear(minDate));
        LOG.info("createAndSaveImportBankMovements -> [{}/{}]", month, year);

        // Delete
        importBankMovementsService.delete(bankAccountPk, year, month, user);

        // Insert
        final ImportBankMovements importBankMovements = new ImportBankMovements();
        importBankMovements.setBankAccount(bankAccount);
        importBankMovements.setMonth(month);
        importBankMovements.setYear(year);
        importBankMovements.setMovementsText(text);
        importBankMovements.setMinDate(minDate);
        importBankMovements.setMaxDate(maxDate);
        importBankMovementsService.insert(importBankMovements);
    }

    public Date getMinMaxDate(final List<ImportBankMovementData> movements, final MIN_MAX minMax)
    {
        Date result = null;
        if (CollectionUtils.isNotEmpty(movements))
        {
            for (final ImportBankMovementData mov : movements)
            {
                final Date date = mov.getImportedBankAccountMovement().getDate();
                if (result == null)
                {
                    result = date;
                } else if (MIN_MAX.MIN.equals(minMax) && date.before(result))
                {
                    result = date;
                } else if (MIN_MAX.MAX.equals(minMax) && date.after(result))
                {
                    result = date;
                }
            }
        }
        return result;
    }

    public List<ImportBankMovementData> parseImportMovementsFromText(final Long bankAccountPk, final String text, final User user)
    {
        List<ImportBankMovementData> result = Lists.newArrayList();

        final BankAccount bankAccount = bankAccountService.getByPk(bankAccountPk);

        // Parse text -> BankAccountMovementData
        List<BankAccountMovementData> parsedMovements = bankAccountMovementParseStrategy.parseText(bankAccount.getBank().getCode(), text);
        if (CollectionUtils.isNotEmpty(parsedMovements))
        {
            List<BankAccountMovementData> movsByMonth = bankAccountMovementFacade.getByMonth(bankAccount.getPk()
                    , DateUtils.getYear(parsedMovements.get(0).getDate()), DateUtils.getMonth(parsedMovements.get(0).getDate()) + 1, user);

            for (final BankAccountMovementData mov : parsedMovements)
            {
                // Enrich Movement
                enrichMovementData(mov, bankAccount);

                final MatchingMovementsData matchingMovs = findMatchingMovementsStrategy.findMatchingMovements(mov, movsByMonth);

                // Create ImportBankMovementData
                final ImportBankMovementData importMovement = new ImportBankMovementData();
                importMovement.setImportedBankAccountMovement(mov);
                importMovement.setMatchingBankAccountMovements(matchingMovs.getMatchingBankAccountMovements());
                importMovement.setMatchingPriority(matchingMovs.getMatchingPriority());
                importMovement.setOtherBankAccountMovements(getOtherMovements(mov, movsByMonth, matchingMovs.getMatchingBankAccountMovements()));
                checkIfBankTransfer(user, importMovement, bankAccount.getDisplayName());
                result.add(importMovement);
            }
        }

        return result;
    }

    protected void checkIfBankTransfer(final User user, final ImportBankMovementData importMovement, final String fromBankAccount)
    {
        final BankAccount bankAccount = bankTransferIdentifierStrategy.getBankAccountForBankTransfer(user, importMovement.getImportedBankAccountMovement().getBankAccount().getBank().getCode(), importMovement.getImportedBankAccountMovement().getDescription(), fromBankAccount);
        if (bankAccount != null)
        {
            importMovement.setBankTransfer(true);
            importMovement.setOtherBankAccount(bankAccountConverter.convert(bankAccount));
        }
    }

    private void enrichMovementData(final BankAccountMovementData movementData, final BankAccount bankAccount)
    {
        final BankAccountData bankAccountData = bankAccountConverter.convert(bankAccount);
        movementData.setBankAccount(bankAccountData);
        movementData.setCurrency(bankAccountData.getCurrency());
    }

    protected List<BankAccountMovementData> getOtherMovements(final BankAccountMovementData importMovement, List<BankAccountMovementData> movsByMonth, List<BankAccountMovementData> matchingMovs)
    {
        List<BankAccountMovementData> candidates = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(movsByMonth))
        {
            for (BankAccountMovementData mov : movsByMonth)
            {
                if (!matchingMovs.contains(mov))
                {
                    candidates.add(mov);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(candidates))
        {
            return candidates;
        } else
        {
            return Lists.newArrayList();
        }
    }

    protected List<BankAccountMovementData> filterCorrespondingMovements(final BankAccountMovementData importMovement, List<BankAccountMovementData> monthMovements)
    {
        final List<BankAccountMovementData> result = Lists.newArrayList();
        if (monthMovements != null && monthMovements.size() > 0)
        {
            for (BankAccountMovementData mov : monthMovements)
            {
                if (movementsMatches(importMovement, mov))
                {
                    result.add(mov);
                }
            }
        }
        return result;
    }

    protected boolean movementsMatches(final BankAccountMovementData importMovement, BankAccountMovementData monthMovement)
    {
        Assert.notNull(importMovement, "importMovement cannot be null");
        Assert.notNull(monthMovement, "monthMovement cannot be null");

        if (importMovement.getAmount().equals(monthMovement.getAmount())
                && importMovement.getDate().equals(monthMovement.getDate())
                && importMovement.getDescription().equals(monthMovement.getDescription()))
        {
            return true;
        } else if (importMovement.getAmount().equals(monthMovement.getAmount())
                && importMovement.getDate().equals(monthMovement.getDate()))
        {
            return true;
        } else if (importMovement.getAmount().equals(monthMovement.getAmount()))
        {
            return true;
        }
        return false;
    }

    private enum MIN_MAX {
        MIN, MAX
    }

    @Override
    public String getMovementKey(final BankAccountMovementData mov)
    {
        return bankMovementToMapStrategy.getKeyForMovement(mov);
    }
}
