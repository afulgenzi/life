package com.fulg.life.webmvc.security;

import com.fulg.life.model.entities.User;
import com.fulg.life.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by alessandro.fulgenzi on 07/05/16.
 */
public class LifeSimpleUrlAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
    @Resource
    UserService userService;
    @Resource
    TokenBasedRememberMeServices rememberMeServices;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
//        if (authentication != null && authentication.getName() != null) {
//            User user = userService.getUser(authentication.getName());
//            if (user != null) {
//                userService.setCurrentUser(request.getSession().getId(), user);
//            }
//        }

        rememberMeServices.onLoginSuccess(request, response, authentication);

        super.onAuthenticationSuccess(request, response, authentication);
    }

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isNotBlank(request.getRequestURI())){
            return request.getRequestURI();
        }
        else
        {
            return super.determineTargetUrl(request, response);
        }
    }
}
