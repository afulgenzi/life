package com.fulg.life.webmvc.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomePageController extends AbstractBaseController {
	private static final Logger LOG = LoggerFactory.getLogger(HomePageController.class);

	@RequestMapping(value = "/")
	public ModelAndView home(HttpServletResponse response) throws IOException {
		ModelAndView model = new ModelAndView("home");

		return model;
	}

	@RequestMapping(value = "/hello")
	public ModelAndView hello(HttpServletResponse response) throws IOException {
		ModelAndView model = new ModelAndView("hello");

		return model;
	}

}
