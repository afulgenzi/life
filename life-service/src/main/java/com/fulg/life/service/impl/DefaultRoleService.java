package com.fulg.life.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fulg.life.model.entities.Role;
import com.fulg.life.model.entities.User;
import com.fulg.life.model.entities.UserRole;
import com.fulg.life.model.repository.RoleRepository;
import com.fulg.life.model.repository.UserRoleRepository;
import com.fulg.life.service.RoleService;

@Service
public class DefaultRoleService implements RoleService {
	@Resource
	private RoleRepository roleRepository;
	@Resource
	private UserRoleRepository userRoleRepository;

	@Override
	public List<Role> getUserRoles(User user) {
		List<Role> roles = new ArrayList<Role>();
		List<UserRole> userRoles = userRoleRepository.findByUserName(user.getUsername());
		if (!CollectionUtils.isEmpty(userRoles)){
			for (UserRole userRole:userRoles){
				roles.add(userRole.getRole());
			}
		}
		return roles;
	}

}
