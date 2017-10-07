package com.fulg.life.facade.strategy.impl;

import com.fulg.life.data.BankAccountMovementData;
import com.fulg.life.data.MatchingMovementsData;
import com.fulg.life.facade.strategy.FindMatchingMovementsStrategy;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class DefaultFindMatchingMovementsStrategy implements FindMatchingMovementsStrategy {

    private DateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
    private static final int DESCRIPTION_LENGTH_THRESHOLD = 2;
    private static final int DESCRIPTION_FOUND_TOKENS_THRESHOLD = 1;

    @Override
    public MatchingMovementsData findMatchingMovements(BankAccountMovementData importMovement,
                                                       List<BankAccountMovementData> movsByMonth)
    {
        final MatchingMovementsData matchingMovementsData = new MatchingMovementsData();

        List<Result> resultList = getResultList(importMovement, movsByMonth);

        List<BankAccountMovementData> mathingMovs = null;

        // matching all
        mathingMovs = filterResultList(resultList, true, true, true);
        if (CollectionUtils.isNotEmpty(mathingMovs))
        {
            matchingMovementsData.setMatchingBankAccountMovements(mathingMovs);
            matchingMovementsData.setMatchingPriority(1);
            return matchingMovementsData;
        }

        // matching amount, date
        mathingMovs = filterResultList(resultList, true, true, false);
        if (CollectionUtils.isNotEmpty(mathingMovs))
        {
            matchingMovementsData.setMatchingBankAccountMovements(mathingMovs);
            matchingMovementsData.setMatchingPriority(2);
            return matchingMovementsData;
        }

        // matching amount, description
        mathingMovs = filterResultList(resultList, true, false, true);
        if (CollectionUtils.isNotEmpty(mathingMovs))
        {
            matchingMovementsData.setMatchingBankAccountMovements(mathingMovs);
            matchingMovementsData.setMatchingPriority(3);
            return matchingMovementsData;
        }

        // matching amount
        mathingMovs = filterResultList(resultList, true, false, false);
        if (CollectionUtils.isNotEmpty(mathingMovs) && mathingMovs.size() == 1)
        {
            matchingMovementsData.setMatchingBankAccountMovements(mathingMovs);
            matchingMovementsData.setMatchingPriority(4);
            return matchingMovementsData;
        }

        // matching date, description
        mathingMovs = filterResultList(resultList, false, true, true);
        if (CollectionUtils.isNotEmpty(mathingMovs))
        {
            matchingMovementsData.setMatchingBankAccountMovements(mathingMovs);
            matchingMovementsData.setMatchingPriority(5);
            return matchingMovementsData;
        }

        // matching description
        mathingMovs = filterResultList(resultList, false, false, true);
        if (CollectionUtils.isNotEmpty(mathingMovs))
        {
            matchingMovementsData.setMatchingBankAccountMovements(mathingMovs);
            matchingMovementsData.setMatchingPriority(6);
            return matchingMovementsData;
        }

        // matching date
//		mathingMovs = filterResultList(resultList, false, true, false);
//		if (CollectionUtils.isNotEmpty(mathingMovs)){
//		matchingMovementsData.setMatchingBankAccountMovements(mathingMovs);
//		matchingMovementsData.setMatchingPriority(7);
//		return matchingMovementsData;
//		}

        return matchingMovementsData;
    }

    protected List<Result> getResultList(BankAccountMovementData importMovement,
                                         List<BankAccountMovementData> movsByMonth)
    {
        List<Result> resultList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(movsByMonth))
        {
            for (BankAccountMovementData mov : movsByMonth)
            {
                Result result = new Result();
                result.setMatchingMov(mov);
                if (sameAmount(mov, importMovement))
                {
                    result.setSameAmount(true);
                }
                if (sameDate(mov, importMovement))
                {
                    result.setSameDate(true);
                }
                if (sameDescription(mov, importMovement))
                {
                    result.setSameDescription(true);
                }
                if (similarDescription(mov, importMovement))
                {
                    result.setSimilarDescription(true);
                }
                if (mov.getEu().equals(importMovement.getEu()))
                {
                    result.setSameEu(true);
                }
                resultList.add(result);
            }
        }
        return resultList;
    }

    protected List<BankAccountMovementData> filterResultList(final List<Result> resultList, final boolean sameAmount,
                                                             final boolean sameDate, final boolean sameDescription)
    {
        final List<BankAccountMovementData> output = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(resultList))
        {
            for (Result result : resultList)
            {
                if (result.isSameEu())
                {
                    if ((!sameAmount) || result.isSameAmount())
                    {
                        if ((!sameDate) || result.isSameDate())
                        {
                            if ((!sameDescription) || result.isSameDescription())
                            {
                                output.add(result.getMatchingMov());
                            }
                        }
                    }
                }
            }

            if (sameDescription && CollectionUtils.isEmpty(output)){
                for (Result result : resultList)
                {
                    if (result.isSameEu())
                    {
                        if ((!sameAmount) || result.isSameAmount())
                        {
                            if ((!sameDate) || result.isSameDate())
                            {
                                if ((!sameDescription) || result.isSimilarDescription())
                                {
                                    output.add(result.getMatchingMov());
                                }
                            }
                        }
                    }
                }
            }
        }

        return output;
    }

    private boolean sameAmount(final BankAccountMovementData mov1, final BankAccountMovementData mov2)
    {
        return Math.abs(mov1.getAmount()) == Math.abs(mov2.getAmount()) && mov1.getEu().equals(mov2.getEu());
    }

    private boolean sameDate(final BankAccountMovementData mov1, final BankAccountMovementData mov2)
    {
        return dateFormatter.format(mov1.getDate()).equals(dateFormatter.format(mov2.getDate()));
    }

    private boolean sameDescription(final BankAccountMovementData mov1, final BankAccountMovementData mov2)
    {
        if (StringUtils.isNotBlank(mov1.getDescription()) && StringUtils.isNotBlank(mov2.getDescription()))
        {
            return mov1.getDescription().equalsIgnoreCase(mov2.getDescription());
        }
        return false;
    }

    private boolean similarDescription(final BankAccountMovementData mov1, final BankAccountMovementData mov2)
    {
        if (StringUtils.isNotBlank(mov1.getDescription()) && StringUtils.isNotBlank(mov2.getDescription()))
        {
            if (mov1.getDescription().equalsIgnoreCase(mov2.getDescription()))
            {
                return true;
            }

            int countFoundTokens = 0;
            for (final String token : mov1.getDescription().split(" "))
            {
                if (token.length() > DESCRIPTION_LENGTH_THRESHOLD && StringUtils.isNotBlank(
                        mov2.getDescription()) && mov2.getDescription().toLowerCase().contains(token.toLowerCase()))
                {
                    countFoundTokens++;
                }
            }
            if (countFoundTokens >= DESCRIPTION_FOUND_TOKENS_THRESHOLD)
            {
                return true;
            }
        }
        return false;
    }

    private class Result {
        private BankAccountMovementData matchingMov;
        private boolean sameAmount = false;
        private boolean sameDate = false;
        private boolean sameDescription = false;
        private boolean similarDescription = false;
        private boolean sameEu = false;

        public BankAccountMovementData getMatchingMov()
        {
            return matchingMov;
        }

        public void setMatchingMov(BankAccountMovementData matchingMov)
        {
            this.matchingMov = matchingMov;
        }

        public boolean isSameAmount()
        {
            return sameAmount;
        }

        public void setSameAmount(boolean sameAmount)
        {
            this.sameAmount = sameAmount;
        }

        public boolean isSameDate()
        {
            return sameDate;
        }

        public void setSameDate(boolean sameDate)
        {
            this.sameDate = sameDate;
        }

        public boolean isSameDescription()
        {
            return sameDescription;
        }

        public void setSameDescription(boolean sameDescription)
        {
            this.sameDescription = sameDescription;
        }

        public boolean isSimilarDescription()
        {
            return similarDescription;
        }

        public void setSimilarDescription(boolean similarDescription)
        {
            this.similarDescription = similarDescription;
        }

        public boolean isSameEu()
        {
            return sameEu;
        }

        public void setSameEu(boolean sameEu)
        {
            this.sameEu = sameEu;
        }
    }
}
