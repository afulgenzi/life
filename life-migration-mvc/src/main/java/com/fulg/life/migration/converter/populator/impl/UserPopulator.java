package com.fulg.life.migration.converter.populator.impl;

import java.util.List;
import java.util.Map;

import com.fulg.life.model.entities.Item;
import com.fulg.life.model.entities.Role;
import com.fulg.life.model.entities.User;

public class UserPopulator extends ItemPopulator {

	@Override
	public void populate(Item item, Map<String, Object> map) {
		super.populate(item, map);
		User target = (User) item;
		map.put("Username", target.getUsername());
		map.put("Password", target.getPassword());
		map.put("Email", target.getEmail());
		map.put("FirstName", target.getFirstName());
		map.put("LastName", target.getLastName());
//		map.put("Roles", getRoles(target));
	}

	private String getRoles(List<Role> roles) {
		StringBuffer buf = new StringBuffer();
		int count = 0;
		for (Role role : roles) {
			count++;
			if (count > 1) {
				buf.append(",");
			}
			buf.append(getItemAsString(role));
		}
		return buf.toString();
	}

}
