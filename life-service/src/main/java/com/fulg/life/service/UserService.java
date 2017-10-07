package com.fulg.life.service;

import com.fulg.life.model.entities.User;

public interface UserService extends ItemService {
	User getUser(String username);

	User getCurrentUser(String sessionId);

	void setCurrentUser(String sessionId, User user);
}
