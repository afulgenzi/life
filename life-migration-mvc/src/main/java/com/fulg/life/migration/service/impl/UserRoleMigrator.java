package com.fulg.life.migration.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.fulg.life.migration.service.AbstractMigrator;
import com.fulg.life.model.entities.Role;
import com.fulg.life.model.entities.User;
import com.fulg.life.model.entities.UserRole;
import com.fulg.life.model.repository.RoleRepository;
import com.fulg.life.model.repository.UserRepository;
import com.fulg.life.model.repository.UserRoleRepository;

@Component
public class UserRoleMigrator extends AbstractMigrator<User, User> {
	@Resource
	UserRepository userRepository;
	@Resource
	RoleRepository roleRepository;
	@Resource
	UserRoleRepository userRoleRepository;

	@Override
	public void clearAll() {
		userRoleRepository.deleteAll();
	}

	@Override
	public List<User> getAllSource() {
		List<User> users = userRepository.findAll();
		return users;
	}

	public void migrateInternal(User user) {
		List<Role> roleList = roleRepository.findAll();

		if (!CollectionUtils.isEmpty(roleList)) {
			if (user.getUsername().equals(USER_ANONYMOUS_USERNAME)) {
				for (Role role : roleList) {
					if (role.getRole().equalsIgnoreCase(ROLE_USER)) {
						UserRole userRole = new UserRole();
						userRole.setRole(role);
						userRole.setUser(user);
						userRoleRepository.save(userRole);
						userRepository.save(user);
						roleRepository.save(role);
					}
				}
			} else {
				for (Role role : roleList) {
					UserRole userRole = new UserRole();
					userRole.setRole(role);
					userRole.setUser(user);
					userRoleRepository.save(userRole);
					userRepository.save(user);
					roleRepository.save(role);
				}
			}
		}
	}

	protected void getOrCreateUserRole(String userName, String roleName) {
		User user = userRepository.findByUsername(userName);
		Role role = roleRepository.findByRole(roleName);
		if (user != null && role != null) {
			getOrCreateUserRole(user, role);
		}
	}

	protected void getOrCreateUserRole(User user, Role role) {
		if (user != null && role != null) {
			UserRole userRole = userRoleRepository.findByUserRole(user.getUsername(), role.getRole());
			if (userRole == null) {
				userRole = new UserRole();
				userRole.setRole(role);
				userRole.setUser(user);
				userRoleRepository.save(userRole);
			}
		}
	}

}
