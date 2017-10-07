package com.fulg.life.migration.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.fulg.life.migration.service.AbstractMigrator;
import com.fulg.life.model.entities.Role;
import com.fulg.life.model.repository.RoleRepository;
import com.fulg.life.model.repository.UserRoleRepository;

@Component
public class RoleMigrator extends AbstractMigrator<Role, Role> {
	@Resource
	RoleRepository roleRepository;
	@Resource
	UserRoleRepository userRoleRepository;

	@Override
	public void clearAll() {
		userRoleRepository.deleteAll();
		roleRepository.deleteAll();
	}

	@Override
	public List<Role> getAllSource() {
		List<Role> roles = new ArrayList<Role>();

		roles.add(getOrCreateRole(ROLE_ADMIN));
		roles.add(getOrCreateRole(ROLE_USER));

		return roles;
	}
	
	private Role getOrCreateRole(String roleName){
		Role role = roleRepository.findByRole(roleName);
		if (role == null) {
			role = new Role();
			role.setRole(roleName);
		}
		return role;
	}

	@Override
	public void migrateInternal(Role source) {
		roleRepository.save(source);
	}

}
