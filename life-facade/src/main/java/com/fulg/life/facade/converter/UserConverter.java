package com.fulg.life.facade.converter;

import com.fulg.life.data.UserData;
import com.fulg.life.model.entities.User;

public class UserConverter extends AbstractConverter<UserData, User> {

	@Override
	protected UserData createTarget() {
		return new UserData();
	}

	@Override
	public UserData populate(User source, UserData target) {
		target.setPk(source.getPk());
		target.setUserName(source.getUsername());
		target.setEmail(source.getEmail());
		target.setFirstName(source.getFirstName());
		target.setLastName(source.getLastName());
		return target;
	}
}
