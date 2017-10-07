package com.fulg.life.service.impl;

import com.fulg.life.model.entities.User;
import com.fulg.life.model.repository.UserRepository;
import com.fulg.life.service.SessionService;
import com.fulg.life.service.UserService;
import com.fulg.life.service.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DefaultUserService implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultUserService.class);

    private static final String ANONYMOUS_USER = "anonymous";
    private static final Map<String, User> users = new ConcurrentHashMap<String, User>();

    @Resource
    private SessionService sessionService;
    @Resource
    private UserRepository userRepository;

    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User getCurrentUser(String sessionId) {
        User currentUser = sessionService.<User>getSessionAttribute(sessionId, SessionService.SESSION_ATTRIBUTE_USER,
                User.class);
        if (currentUser == null) {
            try {
                currentUser = getAnonymousUser();
            } catch (UserNotFoundException e) {
                throw new RuntimeException("Cannot procede without '" + ANONYMOUS_USER + "' user in the system.");
            }
            sessionService.setSessionAttribute(sessionId, SessionService.SESSION_ATTRIBUTE_USER, currentUser);
        }
        return currentUser;
    }

    @Override
    public synchronized void setCurrentUser(String sessionId, User user) {
        LOG.debug("setCurrentUser({}, {})", sessionId, user == null ? "null" : user.getUsername());
        sessionService.setSessionAttribute(sessionId, SessionService.SESSION_ATTRIBUTE_USER, user);
    }

    private User getAnonymousUser() throws UserNotFoundException {
        User anonymousUser = userRepository.findByUsername(ANONYMOUS_USER);
        if (anonymousUser == null) {
            throw new UserNotFoundException(ANONYMOUS_USER);
        }
        return anonymousUser;
    }

    @Override
    public User getOne(Long pk) {
        return userRepository.findOne(pk);
    }

}
