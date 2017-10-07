package com.fulg.life.migration.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.fulg.life.migration.service.AbstractMigrator;
import com.fulg.life.model.entities.User;
import com.fulg.life.model.repository.RoleRepository;
import com.fulg.life.model.repository.UserRepository;
import com.fulg.life.model.repository.UserRoleRepository;

@Component
public class UserMigrator extends AbstractMigrator<User, User> {
	@Resource
	UserRepository userRepository;
	@Resource
	RoleRepository roleRepository;
	@Resource
	UserRoleRepository userRoleRepository;

	@Override
	public void clearAll() {
		userRoleRepository.deleteAll();
		userRepository.deleteAll();
	}

	@Override
	public List<User> getAllSource() {
		List<User> users = new ArrayList<User>();

		users.add(getOrCreateUser(USER_ALEX_USERNAME, USER_ALEX_USERNAME, USER_ALEX_EMAIL, USER_ALEX_FIRSTNAME,
				USER_ALEX_LASTNAME));
		users.add(getOrCreateUser(USER_ANONYMOUS_USERNAME, USER_ANONYMOUS_USERNAME, USER_ANONYMOUS_EMAIL, USER_ANONYMOUS_FIRSTNAME,
				USER_ANONYMOUS_LASTNAME));

		return users;
	}

	protected User getOrCreateUser(String username, String password, String email, String firstName, String lastName) {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			user = new User();
			user.setEmail(email);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setUsername(username);
			user.setPassword(username);
		}
		return user;
	}

	@Override
	public void migrateInternal(User user) {
		userRepository.save(user);
	}

}
