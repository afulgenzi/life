package com.fulg.life.facade.impl;

import com.fulg.life.data.*;
import com.fulg.life.data.util.DateUtils;
import com.fulg.life.facade.BankAccountMovementFacade;
import com.fulg.life.facade.CurrencyFacade;
import com.fulg.life.facade.converter.BankAccountConverter;
import com.fulg.life.facade.converter.BankAccountMovementConverter;
import com.fulg.life.facade.converter.BankTransferConverter;
import com.fulg.life.facade.converter.CurrencyConverter;
import com.fulg.life.facade.converter.reverse.BankAccountMovementReverseConverter;
import com.fulg.life.facade.converter.reverse.CurrencyReverseConverter;
import com.fulg.life.facade.strategy.BankAccountMovementParseStrategy;
import com.fulg.life.model.entities.BankAccount;
import com.fulg.life.model.entities.BankAccountMovement;
import com.fulg.life.model.entities.BankTransfer;
import com.fulg.life.model.entities.User;
import com.fulg.life.service.*;
import com.fulg.life.service.exception.UnauthorizedOperationException;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

public class BankAccountMovementFacadeImpl implements BankAccountMovementFacade {
    @SuppressWarnings("unused")
    private static Logger LOG = LoggerFactory.getLogger(BankAccountMovementFacadeImpl.class);

    @Resource
    BankAccountMovementService bankAccountMovementService;
    @Resource
    BankAccountService bankAccountService;
    @Resource
    BankAccountOwnerService bankAccountOwnerService;
    @Resource
    CurrencyService currencyService;
    @Resource
    BankTransferService bankTransferService;
    @Resource
    BankAccountMovementParseStrategy bankAccountMovementParseStrategy;

    @Resource
    BankAccountMovementConverter bankAccountMovementConverter;
    @Resource
    BankAccountConverter bankAccountConverter;
    @Resource
    BankAccountMovementReverseConverter bankAccountMovementReverseConverter;
    @Resource
    CurrencyReverseConverter currencyReverseConverter;
    @Resource
    CurrencyConverter currencyConverter;
    @Resource
    BankTransferConverter bankTransferConverter;

    /**
     * Get All
     *
     * @return
     */
    @Override
    public List<BankAccountMovementData> getAll(Long bankAccountPk, User user)
    {
        // get movements
        List<BankAccountMovement> movementList = bankAccountMovementService.getAll(bankAccountPk, user);

        // prepare output
        return prepareData(bankAccountPk, movementList, user, true);
    }

    /**
     * Get By Month
     */
    @Override
    public List<BankAccountMovementData> getByMonth(Long bankAccountPk, int year, int month, User user)
    {
        // get all movements, in order to be able to calculate Overall Balance
        List<BankAccountMovement> movementList = bankAccountMovementService.getByMonth(year, month, user);

        // prepare output
        return prepareData(bankAccountPk, movementList, user, true);
    }

    /**
     * Get By Text
     */
    @Override
    public List<BankAccountMovementData> getByText(final Long bankAccountPk, final String text,
                                                   final CategoryData category, final Date startDate,
                                                   final Date endDate, String inOut, final Boolean uncategorised,
                                                   final User user)
    {
        final Date actualStartDate = startDate != null ? startDate : DateUtils.getDefaultStartDate();
        final Date actualEndDate = endDate != null ? endDate : DateUtils.getDefaultEndDate();
        final String actualText = text == null ? "" : text;
        final boolean actualUncategorised = uncategorised == null ? false : uncategorised.booleanValue();
        final Long categoryPk = category == null ? null : category.getPk();
//        List<BankAccountMovement> movementList = bankAccountMovementService.getByDescription(bankAccountPk, actualText,
//                categoryPk, actualStartDate, actualEndDate, inOut, user);
        List<BankAccountMovement> movementList = bankAccountMovementService.getByAttributes(bankAccountPk, actualText,
                categoryPk, actualStartDate, actualEndDate, inOut, actualUncategorised, user);

        // prepare output
        return prepareData(bankAccountPk, movementList, user, false);
    }

    @Override
    public List<BankAccountMovementData> getByCategory(final Long categoryPk, final User user)
    {
        return bankAccountMovementConverter.convertAll(bankAccountMovementService.getByCategory(categoryPk, user));
    }

    /**
     * Get By Transfer
     */
    @Override
    public List<BankAccountMovementData> getByTransfer(
            final String fromTo,
            final BankAccountData account,
            final BankAccountData otherAccount,
            final Date startDate,
            final Date endDate,
            final User user)
    {
        final Long accountPk = account != null ? account.getPk() : 0L;
        final Long otherAccountPk = otherAccount != null ? otherAccount.getPk() : 0L;
        final Date actualStartDate = startDate != null ? startDate : DateUtils.getDefaultStartDate();
        final Date actualEndDate = endDate != null ? endDate : DateUtils.getDefaultEndDate();
        // get movements
        List<BankAccountMovement> movementList = bankAccountMovementService.getByTransfer(fromTo, accountPk, otherAccountPk, actualStartDate, actualEndDate, user);

        // prepare output
        return prepareData(accountPk, movementList, user, false);
    }

    protected List<BankAccountMovementData> prepareData(Long bankAccountPk,
                                                        List<BankAccountMovement> inputMovements,
                                                        User user,
                                                        boolean calculateBalance)
    {
        LOG.debug("Preparing data for [{}] transactions.", CollectionUtils.isEmpty(inputMovements) ? 0 : inputMovements.size());
        // convert
        List<BankAccountMovementData> movements = Lists.newArrayList();

        // sort
//        Collections.sort(movements);

//            movements = new CopyOnWriteArrayList<BankAccountMovementData>(movements);
        inputMovements = Ordering.natural().onResultOf(new Function<BankAccountMovement, String>() {
            @Override
            public String apply(BankAccountMovement mov)
            {
                return mov.getDate().getTime() + "_" + mov.getPk();
            }
        }).sortedCopy(inputMovements);

        // populate balance
        if (!CollectionUtils.isEmpty(inputMovements))
        {
            List<BankAccountBalanceData> balanceList = getBankAccountBalancesBeforeMovement(user, bankAccountMovementConverter.convert(inputMovements.get(0)));

            for (BankAccountMovement movement : inputMovements)
            {
                final BankAccountMovementData movementData = bankAccountMovementConverter.convert(movement);
                if (calculateBalance)
                {
                    calculateNewBalance(balanceList, movementData);
                }
                if (bankAccountPk.equals(movement.getBankAccount().getPk()))
                {
                    if (movement.getBankTransfer() != null)
                    {
                        populateTransferData(movementData);
                    }
                    movements.add(movementData);
                }
            }
        }

        return Ordering.natural().onResultOf(new Function<BankAccountMovementData, String>() {
            @Override
            public String apply(BankAccountMovementData mov)
            {
                return mov.getDate().getTime() + "_" + mov.getPk();
            }
        }).reverse().sortedCopy(movements);
    }

    protected void populateTransferData(final BankAccountMovementData mov)
    {
        if (mov != null && mov.getBankTransfer() != null)
        {
            final BankTransfer transfer = (BankTransfer) bankTransferService.getOne(mov.getBankTransfer().getPk());
            final BankTransferData transferData = bankTransferConverter.convert(transfer);
            final BankAccountMovementData fromMovData = bankAccountMovementConverter.convert(transfer.getFromMovement());
            final BankAccountMovementData toMovData = bankAccountMovementConverter.convert(transfer.getToMovement());

            transferData.setFromMovement(fromMovData);
            transferData.setToMovement(toMovData);

//			fromMovData.setBankTransfer(transferData);
//			toMovData.setBankTransfer(transferData);

            mov.setBankTransfer(transferData);
        }
    }

    private List<BankAccountBalanceData> getBankAccountBalancesBeforeMovement(User user,
                                                                              BankAccountMovementData movement)
    {
        List<BankAccountBalanceData> balanceList = new ArrayList<BankAccountBalanceData>();
        // Search for BankAccounts
        List<BankAccount> userBankAccountList = bankAccountService.getAll(user);
        if (!CollectionUtils.isEmpty(userBankAccountList))
        {
            // Convert BankAccounts
            List<BankAccountData> userBankAccountDataList = bankAccountConverter.convertAll(userBankAccountList);

            for (BankAccountData bankAccount : userBankAccountDataList)
            {
                Double balance = bankAccountMovementService.getBalanceBeforeMovement(bankAccount.getPk(),
                        movement.getDate(), movement.getPk());

                BankAccountBalanceData bankAccountBalance = new BankAccountBalanceData();
                bankAccountBalance.setBankAccount(bankAccount);
                bankAccountBalance.setBalance(balance);
                balanceList.add(bankAccountBalance);
            }
        }
        return balanceList;
    }

    private void calculateNewBalance(List<BankAccountBalanceData> accountBalanceList, BankAccountMovementData movement)
    {
        if (!CollectionUtils.isEmpty(accountBalanceList))
        {
            for (BankAccountBalanceData balance : accountBalanceList)
            {
                // If Movement's BankAccount
                if (balance.getBankAccount().equals(movement.getBankAccount()))
                {
                    Double oldBalance = Double.valueOf(balance.getBalance());
                    // Calculate new balance
                    if ("E".equals(movement.getEu()))
                    {
                        balance.setBalance(balance.getBalance() + movement.getAmount());
                    } else
                    {
                        balance.setBalance(balance.getBalance() - movement.getAmount());
                    }
                    Double newBalance = Double.valueOf(balance.getBalance());
                    LOG.info("[{}] amount [{}], updated balance from [{}] to [{}]", movement.getDescription(), movement.getAmount(), oldBalance, newBalance);
                    movement.setBalanceAfter(balance.getBalance());
                }
            }
            movement.setBankAccountBalances(cloneBankAccountBalanceList(accountBalanceList));
            final CurrencyData forcedGBPCurrency = currencyConverter.convert(currencyService.getBritishPound());
            movement.setOverallBalanceAfter(getOverallBalance(accountBalanceList, forcedGBPCurrency), forcedGBPCurrency);
//            movement.setOverallBalanceAfter(getOverallBalance(accountBalanceList, movement.getBankAccount()
//                    .getCurrency()));
        }
    }

    private List<BankAccountBalanceData> cloneBankAccountBalanceList(List<BankAccountBalanceData> bankAccountBalances)
    {
        List<BankAccountBalanceData> newList = new ArrayList<BankAccountBalanceData>();
        for (BankAccountBalanceData balance : bankAccountBalances)
        {
            BankAccountBalanceData newBalance = new BankAccountBalanceData();
            newBalance.setBankAccount(balance.getBankAccount());
            newBalance.setBalance(Double.valueOf(Double.valueOf(balance.getBalance() * 100).longValue() / 100));
            newList.add(newBalance);
        }
        return newList;
    }

    private Double getOverallBalance(List<BankAccountBalanceData> balanceList, CurrencyData currency)
    {
        if (!CollectionUtils.isEmpty(balanceList))
        {
            Double overallBalance = Double.valueOf(0.0);
            for (BankAccountBalanceData balance : balanceList)
            {
                overallBalance = overallBalance
                        + currencyService.convertAmount(balance.getBalance(),
                        currencyReverseConverter.convert(balance.getBankAccount().getCurrency()),
                        currencyReverseConverter.convert(currency));
            }
            return overallBalance;
        } else
        {
            return Double.valueOf(0.0);
        }
    }

    // UPDATE
    public BankAccountMovementData update(BankAccountMovementData movementData, User currentUser)
            throws UnauthorizedOperationException
    {
        BankAccountMovement movement = bankAccountMovementReverseConverter.convert(movementData);
        if (bankAccountOwnerService.canWrite(movement.getBankAccount(), currentUser))
        {
            BankTransfer transfer = bankTransferService.getBankTransferByMovement(movement);
            if (transfer != null)
            {
                throw new RuntimeException("Cannot update a Bank Transfer by movement. Original movement [" + movement.getDescription() + "]");
            } else
            {
                bankAccountMovementService.update(movement);
            }
        } else
        {
            throw new UnauthorizedOperationException(currentUser.getUsername());
        }
        return bankAccountMovementConverter.convert(movement);
    }

    // UPDATE ALL
    @Transactional
    public List<BankAccountMovementData> updateAll(List<BankAccountMovementData> movements, User currentUser)
            throws UnauthorizedOperationException
    {
        final List<BankAccountMovementData> retList = Lists.newArrayList();
        for (final BankAccountMovementData mov : movements)
        {
            retList.add(update(mov, currentUser));
        }
        return retList;
    }

    @Override
    @Transactional
    public List<BankAccountMovementData> insertAll(BankAccountMovementData movementData
            , FrequencyData frequency
            , Date fromDate
            , Date untilDate
            , boolean skipFirstDate
            , User currentUser) throws UnauthorizedOperationException
    {
        final List<BankAccountMovementData> insertedMovements = Lists.newArrayList();

        //Initialize movement
        movementData.setDate(fromDate);
        if (skipFirstDate){
            movementData = calculateNextMovement(movementData, frequency, untilDate);
        }

        // Insert
        while (movementData != null)
        {
            movementData.setPk(null);
            insertedMovements.add(insert(movementData, currentUser));

            movementData = calculateNextMovement(movementData, frequency, untilDate);
        }
        return insertedMovements;
    }

    private BankAccountMovementData calculateNextMovement(final BankAccountMovementData mov, final FrequencyData freq,
                                                          final Date untilDate)
    {
        final Date newDate = DateUtils.calculateNextDate(mov.getDate(), freq, untilDate);
        if (newDate == null)
        {
            return null;
        } else
        {
            mov.setDate(newDate);
            return mov;
        }
    }

    // INSERT
    public BankAccountMovementData insert(BankAccountMovementData movementData, User currentUser)
            throws UnauthorizedOperationException
    {
        LOG.info("Inserting transaction [{}], date [{}]", movementData.getDescription(), movementData.getDate());
        BankAccountMovement movement = bankAccountMovementReverseConverter.convert(movementData);
        movement = bankAccountMovementService.insert(movement);
        return bankAccountMovementConverter.convert(movement);
    }

    // INSERT ALL
    @Transactional
    public List<BankAccountMovementData> insertAll(List<BankAccountMovementData> movements, User currentUser)
            throws UnauthorizedOperationException
    {
        final List<BankAccountMovementData> retList = Lists.newArrayList();
        for (final BankAccountMovementData mov : movements)
        {
            retList.add(insert(mov, currentUser));
        }
        return retList;
    }

    // DELETE
    public void delete(BankAccountMovementData movementData, User currentUser) throws UnauthorizedOperationException
    {
        BankAccountMovement movement = bankAccountMovementReverseConverter.convert(movementData);
        bankAccountMovementService.delete(movement);
    }

}
