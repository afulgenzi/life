package com.fulg.life.facade.data;

import com.fulg.life.data.BankAccountMovementData;
import com.fulg.life.data.MonthData;
import com.google.common.collect.Lists;

import java.util.Date;
import java.util.List;

/**
 * Created by alessandro.fulgenzi on 18/05/16.
 */
public class YearlyMatchesCockpitData {
    private ImportCockpitStatus status;
    private int year;
    private List<MonthlyMatchesCockpitData> monthlyMatches = Lists.newArrayList();


    public ImportCockpitStatus getStatus() {
        return status;
    }

    public void setStatus(ImportCockpitStatus status) {
        this.status = status;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<MonthlyMatchesCockpitData> getMonthlyMatches() {
        return monthlyMatches;
    }

    public static class MonthlyMatchesCockpitData {
        private ImportCockpitStatus status;
        private MonthData month;
        private List<DailyMatchesCockpitData> dailyMatches = Lists.newArrayList();

        public ImportCockpitStatus getStatus() {
            return status;
        }

        public void setStatus(ImportCockpitStatus status) {
            this.status = status;
        }

        public MonthData getMonth() {
            return month;
        }

        public void setMonth(MonthData month) {
            this.month = month;
        }

        public List<DailyMatchesCockpitData> getDailyMatches() {
            return dailyMatches;
        }
    }

    public static class DailyMatchesCockpitData {
        private ImportCockpitStatus status = ImportCockpitStatus.UNAVAILABLE;
        private Date date;
        private Double amount;
        private String formattedAmount;
        private BankAccountMovementData importMov = null;
        private BankAccountMovementData existingMov = null;
        private String eu;

        public ImportCockpitStatus getStatus()
        {
            return status;
        }

        public void setStatus(ImportCockpitStatus status)
        {
            this.status = status;
        }

        public Date getDate()
        {
            return date;
        }

        public void setDate(Date date)
        {
            this.date = date;
        }

        public Double getAmount()
        {
            return amount;
        }

        public void setAmount(Double amount)
        {
            this.amount = amount;
        }

        public String getFormattedAmount()
        {
            return formattedAmount;
        }

        public void setFormattedAmount(String formattedAmount)
        {
            this.formattedAmount = formattedAmount;
        }

        public BankAccountMovementData getImportMov()
        {
            return importMov;
        }

        public void setImportMov(BankAccountMovementData importMov)
        {
            this.importMov = importMov;
        }

        public BankAccountMovementData getExistingMov()
        {
            return existingMov;
        }

        public void setExistingMov(BankAccountMovementData existingMov)
        {
            this.existingMov = existingMov;
        }

        public String getEu()
        {
            return eu;
        }

        public void setEu(String eu)
        {
            this.eu = eu;
        }
    }

    public static enum ImportCockpitStatus {
        MATCHED, UNMATCHED, PARTIALLY_MATCHED, PARTIALLY_MATCHED_SOME_MISSING, UNAVAILABLE;
    }
}
