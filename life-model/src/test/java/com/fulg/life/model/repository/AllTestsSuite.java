package com.fulg.life.model.repository;

import org.junit.runners.Suite;
import org.springframework.transaction.annotation.Transactional;

@Suite.SuiteClasses({ BankAccountTest.class, DutyRepositoryTest.class, UserRepositoryTest.class })
@Transactional
public final class AllTestsSuite extends AbstractTest {
}