package com.fulg.life.data;

import com.google.common.collect.Lists;

import java.util.List;

public class AvailableImportBankMovementResult {
    private List<AvailableImportBankMovementItemResult> availableImportMovements = Lists.newArrayList();

    public List<AvailableImportBankMovementItemResult> getAvailableImportMovements() {
        return availableImportMovements;
    }

    public void setAvailableImportMovements(List<AvailableImportBankMovementItemResult> availableImportMovements) {
        this.availableImportMovements = availableImportMovements;
    }

    public static class AvailableImportBankMovementItemResult{
        MonthData month;
        boolean available;

        public MonthData getMonth() {
            return month;
        }

        public void setMonth(MonthData month) {
            this.month = month;
        }

        public boolean isAvailable() {
            return available;
        }

        public void setAvailable(boolean available) {
            this.available = available;
        }
    }
}
