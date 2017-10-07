package com.fulg.life.webmvc.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by alessandro.fulgenzi on 14/02/16.
 */
public class MyTokenBasedRememberMeServices extends TokenBasedRememberMeServices {
    private static final Logger LOG = LoggerFactory.getLogger(MyTokenBasedRememberMeServices.class);

    @Override
    public void onLoginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication) {
        LOG.info("MyTokenBasedRememberMeServices.onLiginSuccess!!!!!!");
        super.onLoginSuccess(request, response, successfulAuthentication);
    }
}
