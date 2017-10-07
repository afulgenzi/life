package com.fulg.life.service.impl;

import com.fulg.life.model.entities.ImportBankMovements;
import com.fulg.life.model.entities.User;
import com.fulg.life.model.repository.ImportBankMovementsRepository;
import com.fulg.life.service.ImportBankMovementsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DefaultImportBankMovementsService implements ImportBankMovementsService {
	@SuppressWarnings("unused")
	private static Logger LOG = LoggerFactory.getLogger(DefaultImportBankMovementsService.class);

	@Resource
	private ImportBankMovementsRepository importBankMovementsRepository;

	@Override
	public ImportBankMovements getByMonth(Long bankAccountPk, int year, int month, User user) {
		ImportBankMovements movements = importBankMovementsRepository.findByMonth(bankAccountPk, month, year);
		return filterListByUser(movements, user);
	}

	@Override
	public ImportBankMovements insert(ImportBankMovements item) {
		importBankMovementsRepository.save(item);
		return item;
	}

	@Override
	public void delete(Long bankAccountPk, int year, int month, User user) {
		ImportBankMovements movements = importBankMovementsRepository.findByMonth(bankAccountPk, month, year);
		if (movements != null)
		{
			importBankMovementsRepository.delete(movements);
		}
	}

	@Override
	public ImportBankMovements getOne(Long pk) {
		return importBankMovementsRepository.findOne(pk);
	}

	protected ImportBankMovements filterListByUser(ImportBankMovements movements, User user) {
		if (user != null) {
			if (movementBelongsToUser(movements, user)) {
				return movements;
			}
		}
		return null;
	}

	public boolean movementBelongsToUser(ImportBankMovements movement, User user) {
		if (movement != null && user != null && movement.getBankAccount() != null) {
			if (user.getPk().equals(movement.getBankAccount().getUser().getPk())) {
				return true;
			}
		}
		return false;
	}

}
