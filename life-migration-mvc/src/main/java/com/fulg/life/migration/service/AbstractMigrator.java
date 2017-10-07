package com.fulg.life.migration.service;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.fulg.life.model.entities.BankAccount;
import com.fulg.life.model.entities.MigrationItem;
import com.fulg.life.model.entities.User;
import com.fulg.life.model.repository.BankAccountRepository;
import com.fulg.life.model.repository.UserRepository;

public abstract class AbstractMigrator<SOURCE, TARGET> {
	protected static final String USER_ALEX_FIRSTNAME = "Alessandro";
	protected static final String USER_ALEX_LASTNAME = "Fulgenzi";
	protected static final String USER_ALEX_EMAIL = "alex@hotmail.com";
	protected static final String USER_ALEX_USERNAME = "alex";
	
	protected static final String USER_ANONYMOUS_FIRSTNAME = "Anonymous";
	protected static final String USER_ANONYMOUS_LASTNAME = "";
	protected static final String USER_ANONYMOUS_EMAIL = "anonymous@flastech.com";
	protected static final String USER_ANONYMOUS_USERNAME = "anonymous";

	protected static final String ROLE_ADMIN = "admin";
	protected static final String ROLE_USER = "user";


	@Resource
	protected UserRepository userRepository;
	@Resource
	protected BankAccountRepository bankAccountRepository;

	public SOURCE sourceType = null;
	public TARGET targetType = null;

	public final MigrationItem migrate() {
		return migrate(true);
	}

	public MigrationItem migrate(boolean cleanAllBeforeMigrating) {
		// migrate
		int count = 0;
		List<SOURCE> sourceList = getAllSource();
		for (SOURCE source : sourceList) {
			migrateInternal(source);
			count++;
			
			if (count % 10 == 0){
				System.out.println("Migrated " + getTargetType() + " " + source.getClass().getSimpleName() + " #"
						+ count + "/" + sourceList.size());
			}
		}
		System.out.println("Migrated traditional " + getTargetType() + " #" + count
				+ "/" + sourceList.size());
		
		// create additional targets
		count += createAdditionalTargets();

		MigrationItem migrationItem = new MigrationItem();
		migrationItem.setSource(getSourceType());
		migrationItem.setDestination(getTargetType());
		migrationItem.setMigrationDate(new Date());
		migrationItem.setMigratedItems(Integer.valueOf(count));

		return migrationItem;
	}

	private String getSourceType() {
		return getFieldType("sourceType");
	}

	private String getTargetType() {
		return getFieldType("sourceType");
	}

	private String getFieldType(String fieldName) {
		Field field;
		try {
			field = AbstractMigrator.class.getField(fieldName);
			Class fieldType = field.getDeclaringClass();
			return fieldType.getSimpleName();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public abstract void clearAll();

	public abstract List<SOURCE> getAllSource();

	public abstract void migrateInternal(SOURCE source);

	public int createAdditionalTargets(){
		return 0;
	}

	protected User getDefaultUser() {
		User defaultUser = userRepository.findByUsername(USER_ALEX_USERNAME);
		return defaultUser;
	}

	protected BankAccount getDefaultBankAccount() {
		User defaultUser = getDefaultUser();
		BankAccount defaultBankAccount = bankAccountRepository
				.findDefaultAccount(defaultUser.getUsername());
		return defaultBankAccount;
	}
}
