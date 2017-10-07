package com.fulg.life.model.repository.querydsl.predicate;


import com.fulg.life.model.entities.QBankAccountMovement;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.BooleanExpression;

import java.util.Date;


/**
 * Created by alessandro.fulgenzi on 23/07/16.
 */
public class BankAccountMovementPredicates {
        private BankAccountMovementPredicates() {}

        public static BooleanExpression byBankAccount(Long bankAccountPk) {
            return QBankAccountMovement.bankAccountMovement.bankAccount.pk.eq(bankAccountPk);
        }

        public static BooleanExpression byDescription(String description) {
            return QBankAccountMovement.bankAccountMovement.description.contains(description);
        }

        public static BooleanExpression startAfter(Date startDate) {
            return QBankAccountMovement.bankAccountMovement.date.after(startDate);
        }

        public static BooleanExpression endsBefore(Date endDate) {
            return QBankAccountMovement.bankAccountMovement.date.before(endDate);
        }

        public static BooleanExpression inOut(String inOut) {
            return QBankAccountMovement.bankAccountMovement.eu.eq(inOut);
        }

        public static BooleanExpression uncategorised() {
            return QBankAccountMovement.bankAccountMovement.category.isNull();
        }
    }
