package com.fulg.life.facade.converter.reverse;

import com.fulg.life.data.UserData;
import com.fulg.life.model.entities.User;
import com.fulg.life.service.UserService;

import javax.annotation.Resource;

public class UserReverseConverter extends AbstractReverseConverter<User, UserData> {
	@Resource
	UserService userService;

	@Override
	protected User createTarget(Long pk) {
		User item = null;
		if (pk != null) {
			item = (User) userService.getOne(pk);
		} else {
			item = new User();
		}
		return item;
	}

	@Override
	public User populate(UserData source, User target) {
		target.setUsername(source.getUserName());
		target.setEmail(source.getEmail());
		target.setFirstName(source.getFirstName());
		target.setLastName(source.getLastName());
		return target;
	}

}
