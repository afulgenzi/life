package com.fulg.life.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.fulg.life.model.entities.*;
import com.fulg.life.model.repository.querydsl.predicate.BankAccountMovementPredicates;
import com.fulg.life.service.exception.UnrecognizedParameterValueException;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.BooleanExpression;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fulg.life.model.repository.BankAccountMovementRepository;
import com.fulg.life.service.BankAccountMovementService;
import com.fulg.life.service.BankAccountOwnerService;
import com.fulg.life.service.BankAccountService;
import com.fulg.life.service.BankTransferService;

@Service
public class DefaultBankAccountMovementService implements BankAccountMovementService {
	@SuppressWarnings("unused")
	private static Logger LOG = LoggerFactory.getLogger(DefaultBankAccountMovementService.class);

	private static final String FROM = "FROM";
	private static final String TO = "TO";

	@Resource
	private BankAccountMovementRepository bankAccountMovementRepository;
	@Resource
	private BankAccountOwnerService bankAccountOwnerService;
	@Resource
	private BankAccountService bankAccountService;
	@Resource
	private BankTransferService bankTransferService;

	/**
	 * getAll
	 * 
	 * @return
	 */
	@Override
	public List<BankAccountMovement> getAll(Long bankAccount, User user) {
		List<BankAccountMovement> movements = bankAccountMovementRepository.findByAccount(bankAccount);

		return filterListByCurrentUser(movements, user);
	}

	/**
	 * getByMonth
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	@Override
//	@Cacheable(cacheName="movementsByMonth")
	public List<BankAccountMovement> getByMonth(Long bankAccountPk, int year, int month, User user) {
		// EU
		List<BankAccountMovement> movements = bankAccountMovementRepository.findByMonthAndAccount(bankAccountPk, month, year);
		return filterListByCurrentUser(movements, user);
	}

	@Override
//	@Cacheable(cacheName="movementsByMonth")
	public List<BankAccountMovement> getByMonth(int year, int month, User user) {
		// EU
		List<BankAccountMovement> movements = bankAccountMovementRepository.findByMonth(month, year);
		return filterListByCurrentUser(movements, user);
	}

	@Override
	public List<BankAccountMovement> getByTransfer(String fromTo, Long accountPk, Long otherAccountPk, Date startDate, Date endDate, User user){
		// EU
		final List<BankAccountMovement> movements;
		if (FROM.equalsIgnoreCase(fromTo))
		{
			movements = bankAccountMovementRepository.findByTransfer(accountPk, otherAccountPk, accountPk, startDate, endDate);
		}
		else if (TO.equalsIgnoreCase(fromTo))
		{
			movements = bankAccountMovementRepository.findByTransfer(accountPk, accountPk, otherAccountPk, startDate, endDate);
		}
		else
		{
			throw new UnrecognizedParameterValueException("fromTo", fromTo);
		}
		return filterListByCurrentUser(movements, user);
	}

//	@Override
//	public List<BankAccountMovement> getByDescription(Long bankAccountPk, String description, User user) {
//		// EU
//		List<BankAccountMovement> movements = bankAccountMovementRepository.findByDescription(bankAccountPk, description);
//		return filterListByCurrentUser(movements, user);
//	}

	@Override
	public List<BankAccountMovement> getByDescription(Long bankAccountPk, String description, Long categoryPk, Date startDate, Date endDate, String inOut, User user) {
		LOG.info("DefaultBankAccountMovementService.getByDescription");
		final List<BankAccountMovement> movements;
		if (StringUtils.isNotBlank(inOut))
		{
			movements = bankAccountMovementRepository.findByDescriptionAndInOut(bankAccountPk, description, startDate, endDate, inOut);
		}
		else
		{
			movements = bankAccountMovementRepository.findByDescription(bankAccountPk, description, startDate, endDate);
		}
		final List<BankAccountMovement> filteredMovements = filterListByCategory(movements, categoryPk);
		return filterListByCurrentUser(filteredMovements, user);
	}

	@Override
	public List<BankAccountMovement> getByAttributes(Long bankAccountPk, String description, Long categoryPk,
													 Date startDate, Date endDate, String inOut, boolean uncategorised, User user)
	{
		LOG.info("DefaultBankAccountMovementService.getByAttributes");
		final List<BankAccountMovement> movements = Lists.newArrayList();

		Iterable<BankAccountMovement> iterable = bankAccountMovementRepository.findAll(
				createPredicate(bankAccountPk, description, startDate, endDate, inOut, uncategorised));
		movements.addAll(Lists.<BankAccountMovement>newArrayList(iterable));

		final List<BankAccountMovement> filteredMovements = filterListByCategory(movements, categoryPk);
		return filterListByCurrentUser(filteredMovements, user);
	}

	private Predicate createPredicate(Long bankAccountPk, String description, Date startDate,
									  Date endDate, String inOut, boolean uncategorised)
	{
		BooleanExpression expr = null;
		if (bankAccountPk != null)
		{
			expr = (BooleanExpression) appendPredicate(BankAccountMovementPredicates.byBankAccount(bankAccountPk),
					expr);
		}
		expr = (BooleanExpression) appendPredicate(BankAccountMovementPredicates.byDescription(description), expr);
		expr = (BooleanExpression) appendPredicate(BankAccountMovementPredicates.startAfter(startDate), expr);
		expr = (BooleanExpression) appendPredicate(BankAccountMovementPredicates.endsBefore(endDate), expr);
		if (StringUtils.isNotBlank(inOut))
		{
			expr = (BooleanExpression) appendPredicate(BankAccountMovementPredicates.inOut(inOut), expr);
		}
		if (uncategorised)
		{
			expr = (BooleanExpression) appendPredicate(BankAccountMovementPredicates.uncategorised(), expr);
		}
		return expr;
	}

	private Predicate appendPredicate(BooleanExpression newPredicate, BooleanExpression existingPredicate)
	{
		if (existingPredicate == null)
		{
			return newPredicate;
		} else
		{
			return existingPredicate.and(newPredicate);
		}
	}

	protected List<BankAccountMovement> filterListByCurrentUser(List<BankAccountMovement> movements, User currentUser) {
		return filterListByUser(movements, currentUser);
	}

	protected List<BankAccountMovement> filterListByUser(List<BankAccountMovement> movements, User user) {
		List<BankAccountMovement> retList = new ArrayList<BankAccountMovement>();
		if (user != null) {
			for (BankAccountMovement movement : movements) {
				if (movementBelongsToUser(movement, user)) {
					retList.add(movement);
				}
			}
		}
		return retList;
	}

	protected List<BankAccountMovement> filterListByCategory(List<BankAccountMovement> movements, Long categoryPk)
	{
		List<BankAccountMovement> retList = new ArrayList<BankAccountMovement>();
		if (categoryPk != null)
		{
			for (BankAccountMovement movement : movements)
			{
				if (isCategoryInHierarchy(movement.getCategory(), categoryPk))
				{
					retList.add(movement);
				}
			}
		} else
		{
			retList.addAll(movements);
		}
		return retList;
	}

	private boolean isCategoryInHierarchy(final Category category, final Long categoryPk){
		if (category!=null){
			if (category.getPk().equals(categoryPk)){
				return true;
			}
			else{
				return isCategoryInHierarchy(category.getSupercategory(), categoryPk);
			}
		}
		return false;
	}

	public boolean movementBelongsToUser(BankAccountMovement movement, User user) {
		if (movement != null && user != null && movement.getBankAccount() != null) {
			if (user.getPk().equals(movement.getBankAccount().getUser().getPk())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public BankAccountMovement update(BankAccountMovement movement) {
		bankAccountMovementRepository.save(movement);
		return movement;
	}

	@Override
	public BankAccountMovement insert(BankAccountMovement eu) {
		bankAccountMovementRepository.save(eu);
		return eu;
	}

	@Override
	public void delete(BankAccountMovement movement) {
		BankTransfer transfer = bankTransferService.getBankTransferByMovement(movement);
		if (transfer != null) {
			bankTransferService.delete(transfer);
		} else {
			bankAccountMovementRepository.delete(movement);
		}
	}

	public BankAccountMovement getByIdEU(Long key) {
		return bankAccountMovementRepository.findOne(key);
	}

	public Double getBalanceBeforeMovement(Long bankAccountPk, Date date, Long movementPk) {
		Double totIn = bankAccountMovementRepository.findSumByTypeBeforeMovement(bankAccountPk,
				BankAccountMovement.TYPE_IN, date, movementPk);
		Double totOut = bankAccountMovementRepository.findSumByTypeBeforeMovement(bankAccountPk,
				BankAccountMovement.TYPE_OUT, date, movementPk);
		if (totIn == null) {
			totIn = Double.valueOf(0.0);
		}
		if (totOut == null) {
			totOut = Double.valueOf(0.0);
		}
		return totIn - totOut;
	}

	@Override
	public Double getOverallBalanceBeforeMovement(User user, Date date, Long movementPk) {
		List<BankAccount> accounts = bankAccountService.getAll(user);
		double overallBalance = 0.0;
		for (BankAccount account : accounts) {
			double balance = this.getBalanceBeforeMovement(account.getPk(), date, movementPk);
			overallBalance = overallBalance + balance;
		}
		return Double.valueOf(overallBalance);
	}

	@Override
	public BankAccountMovement getLatestByCategory(final Category category, final User user)
	{
		final List<BankAccountMovement> movements = filterListByCurrentUser(bankAccountMovementRepository.findByCategory(category.getPk()), user);
		if (CollectionUtils.isEmpty(movements)){
			return null;
		}
		final List<BankAccountMovement> filteredMovements = filterListByCurrentUser(movements, user);
		final List<BankAccountMovement> sortedMovements = Ordering.natural().onResultOf(new Function<BankAccountMovement, String>() {
			@Override
			public String apply(BankAccountMovement mov)
			{
				return mov.getDate().getTime() + "_" + mov.getPk();
			}
		}).sortedCopy(filteredMovements);
		return sortedMovements.get(sortedMovements.size()-1);
	}

	@Override
	public List<BankAccountMovement> getByCategory(final Long categoryPk, final User user)
	{
		return filterListByCurrentUser(bankAccountMovementRepository.findByCategory(categoryPk), user);
	}

	@Override
	public List<BankAccountMovement> getByInvoice(final Long invoicePk, User user)
	{
		return filterListByCurrentUser(bankAccountMovementRepository.findByInvoice(invoicePk), user);
	}

	@Override
	public BankAccountMovement getOne(Long pk) {
		return bankAccountMovementRepository.findOne(pk);
	}

}
