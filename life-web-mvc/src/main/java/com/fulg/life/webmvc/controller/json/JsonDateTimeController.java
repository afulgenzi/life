package com.fulg.life.webmvc.controller.json;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fulg.life.data.DateData;
import com.fulg.life.data.MonthData;
import com.fulg.life.data.YearData;
import com.fulg.life.facade.DateTimeFacade;
import com.fulg.life.webmvc.controller.AbstractBaseController;

@Controller
@RequestMapping(value = "/datetime/json")
public class JsonDateTimeController extends AbstractBaseController {
	private static final Logger LOG = LoggerFactory.getLogger(JsonDateTimeController.class);

	@Resource
	DateTimeFacade dateTimeFacade;

	@RequestMapping(value = "/getCurrentMonth", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public DateData getCurrentMonth() throws IOException {
		DateData monthData = dateTimeFacade.getCurrentMonth();
		return monthData;
	}

	@RequestMapping(value = "/getPreviousMonth", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public DateData getPreviousMonth(int year, int month) throws IOException {
		DateData monthData = dateTimeFacade.getPreviousMonth(year, month);
		return monthData;
	}

	@RequestMapping(value = "/getNextMonth", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public DateData getNextMonth(int year, int month) throws IOException {
		DateData monthData = dateTimeFacade.getNextMonth(year, month);
		return monthData;
	}

	@RequestMapping(value = "/getMonth", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public DateData getMonth(int year, int month) throws IOException {
		DateData monthData = dateTimeFacade.getMonth(year, month);
		return monthData;
	}
	
	@RequestMapping(value = "/getAllMonths", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<MonthData> getAllMonths() throws IOException {
		List<MonthData> months = dateTimeFacade.getMonths();
		return months;
	}

}
