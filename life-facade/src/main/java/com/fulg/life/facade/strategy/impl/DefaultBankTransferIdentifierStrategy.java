package com.fulg.life.facade.strategy.impl;

import com.fulg.life.facade.strategy.BankTransferIdentifierStrategy;
import com.fulg.life.model.entities.BankAccount;
import com.fulg.life.model.entities.User;
import com.fulg.life.service.BankAccountService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DefaultBankTransferIdentifierStrategy implements BankTransferIdentifierStrategy {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultBankTransferIdentifierStrategy.class);

    private final static String KEY_DELIMITER = ":";

    @Resource
    BankAccountService bankAccountService;

    private Map<String, Map<String, String>> importBankAccountMap;

    @Override
    public BankAccount getBankAccountForBankTransfer(final User user, final String bankCode,
                                                     final String movementDescription, final String fromBankAccount)
    {
        return getCorrespondingBankAccount(user, bankCode, movementDescription, fromBankAccount);
    }

    protected Map<String, Map<String, String>> getImportBankAccountMap()
    {
        return importBankAccountMap;
    }

    @Required
    public void setImportBankAccountMap(Map<String, Map<String, String>> importBankAccountMap)
    {
        this.importBankAccountMap = importBankAccountMap;
    }

    protected BankAccount getCorrespondingBankAccount(final User user, final String bankCode,
                                                      final String movementDescription, final String fromBankAccount)
    {
        if (StringUtils.isNotBlank(movementDescription))
        {
            for (Entry<String, Map<String, String>> mainEntry : getImportBankAccountMap().entrySet())
            {
                if (movementDescription.contains(mainEntry.getKey()))
                {
                    final String accountCode = getBankAccountForBankAndOriginAccount(bankCode, fromBankAccount,
                            mainEntry.getValue());
                    if (StringUtils.isNotBlank(accountCode))
                    {
                        return getBankAccountForName(user, accountCode);
                    }
                }
            }
        }
        return null;
    }

    protected String getBankAccountForBankAndOriginAccount(final String bankCode, final String fromBankAccount,
                                                           final Map<String, String> map)
    {
        for (Entry<String, String> entry : map.entrySet())
        {
            final String[] tokens = entry.getKey().split(KEY_DELIMITER);
            if (tokens != null && tokens.length == 2)
            {
                final String keyBank = tokens[0];
                final String keyAccount = tokens[1];
                if (keyBank.equals(bankCode) && keyAccount.equals(fromBankAccount))
                {
                    return entry.getValue();
                } else if (keyBank.equals(bankCode) && keyAccount.equals("*"))
                {
                    return entry.getValue();
                }
            } else
            {
                LOG.warn("Skipping key [{}], Value: [{}]. Expected format [bank:Account]", entry.getKey(),
                        entry.getValue());
            }
        }
        return StringUtils.EMPTY;
    }

    protected BankAccount getBankAccountForName(final User user, final String bankAccountName)
    {
        List<BankAccount> bankAccounts = bankAccountService.getAll(user);
        if (CollectionUtils.isNotEmpty(bankAccounts))
        {
            for (final BankAccount bankAccount : bankAccounts)
            {
                if (bankAccountName.equalsIgnoreCase(bankAccount.getDisplayName()))
                {
                    return bankAccount;
                }
            }
        }
        LOG.warn("Couldn't find Bank Account for name [{}]", bankAccountName);
        return null;
    }
}
