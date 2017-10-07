package com.fulg.life.model.repository;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.fulg.life.model.entities.BankAccount;

@Transactional
public class BankAccountTest extends AbstractRepositoryIntegrationTest {
	@SuppressWarnings("unused")
	private static Logger LOG = LoggerFactory.getLogger(BankAccountTest.class);

	@Before
	public void cleanUp() {
		genericItemRepository.deleteAll();
	}

	@Test
	public void testFindOne() {
		{
			createBankAccount();

			List<BankAccount> accountList = bankAccountRepository.findAll();

			assertNotNull(accountList);
		}
	}

}
