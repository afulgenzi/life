package com.fulg.life.migration.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;

public abstract class AbstractBaseController {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractBaseController.class);

    @ModelAttribute("contextPath")
    public String getContextPath(final HttpServletRequest request) {
        return request.getContextPath();
    }

    @ModelAttribute("resourcePath")
    public String getResourcePath(final HttpServletRequest request) {
        return request.getContextPath() + "/resources";
    }

    public void logHeaders(HttpServletRequest request) {
        LOG.info("----------HEADERS--------");
        for (Enumeration<String> headers = request.getHeaderNames(); headers.hasMoreElements();) {
            String headerName = headers.nextElement();
            LOG.info(headerName + ":" + request.getHeader(headerName));
        }
    }
}
