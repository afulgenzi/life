package com.fulg.life.service;

import java.util.List;

import com.fulg.life.model.entities.Role;
import com.fulg.life.model.entities.User;

public interface RoleService {
	public List<Role> getUserRoles(User user);
}
