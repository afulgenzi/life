package com.fulg.life.model.repository;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fulg.life.model.entities.BankAccount;
import com.fulg.life.model.entities.Duty;
import com.fulg.life.model.entities.GenericItem;
import com.fulg.life.model.entities.User;

public abstract class AbstractRepositoryIntegrationTest extends AbstractTest {
	private static Logger LOG = LoggerFactory.getLogger(AbstractRepositoryIntegrationTest.class);

	@Autowired
	DutyRepository dutyRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	GenericItemRepository genericItemRepository;

	@Autowired
	BankAccountRepository bankAccountRepository;

	protected User createUser() {
		User user = new User();
		user.setFirstName("firstName");
		user.setLastName("lastName");
		user.setEmail("email");

		userRepository.save(user);
		LOG.info("Created User Id: " + user.getPk());

		return user;
	}

	protected Duty createDuty() {
		return createDuty("Title1", "Description1", new Date());
	}

	protected Duty createDuty(String title, String description, Date date) {
		Duty duty = new Duty();
		duty.setTitle(title);
		duty.setDescription(description);
		duty.setDate(date);

		duty = dutyRepository.save(duty);
		LOG.info("Created Duty Id: " + duty.getPk());

		return duty;
	}

	protected GenericItem createGenericItem(String title, String description) {
		GenericItem item = new GenericItem();
		item.setTitle(title);
		item.setDescription(description);

		item = genericItemRepository.save(item);
		LOG.info("Created GenericItem Id: " + item.getPk());

		return item;
	}

	protected BankAccount createBankAccount() {
		BankAccount item = new BankAccount();
		item.setAccountNumber("123456");
		item.setUser(createUser());

		bankAccountRepository.save(item);
		LOG.info("Created BankAccount Id: " + item.getPk());

		return item;
	}

}
