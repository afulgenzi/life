package com.fulg.life.webmvc.filter;

import com.fulg.life.model.entities.User;
import com.fulg.life.service.RoleService;
import com.fulg.life.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by alessandro.fulgenzi on 06/05/16.
 */
public class LifeUserFilter extends OncePerRequestFilter {
    private static final Logger LOG = LoggerFactory.getLogger(LifeUserFilter.class);

    private static final String ANONYMOUS = "anonymous";

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        final String sessionId = request.getSession().getId();
        final User currentUser = userService.getCurrentUser(sessionId);
        if (currentUser == null || ANONYMOUS.equals(currentUser.getUsername()))
        {
            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication != null && authentication.getName() != null) {
                    User user = userService.getUser(authentication.getName());
                    if (user != null) {
                        LOG.info("Restored user [{}], previous user in session was [{}]", user.getUsername(), currentUser == null ? "null" : currentUser.getUsername());
                        userService.setCurrentUser(sessionId, user);
                    }
                }
            } else {
                LOG.info("SecurityContextHolder doesn't contain authentication. User not retrieved.");
            }
        }
        else
        {
            LOG.debug("User [{}] already in session", currentUser.getUsername());
        }
        chain.doFilter(request, response);
    }
}
