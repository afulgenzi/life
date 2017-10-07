package com.fulg.life.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * Created by alessandro.fulgenzi on 14/02/16.
 */
public class StandaloneService {
    private static final Logger LOG = LoggerFactory.getLogger(StandaloneService.class);

    public static final String NUMBER1 = "£12.34";
    public static final String NUMBER2 = "12.34";
    public static final String NUMBER3 = "£12";
    public static final Locale UK = Locale.UK;

    public void test(String str, Locale locale){
        try
        {
            NumberFormat numberFormat = new DecimalFormat("¤#.00", new DecimalFormatSymbols(locale));

            Double number = Double.valueOf(numberFormat.parse(str).doubleValue());
            LOG.info("Number: [{}]", number);
        } catch (ParseException e)
        {
            LOG.info("Could't parse [{}] using locale [{}]", str, UK);
        }
    }

    public static void main(String[] args){
        final StandaloneService standaloneService = new StandaloneService();
        standaloneService.test(NUMBER1, UK);
        standaloneService.test(NUMBER2, UK);
        standaloneService.test(NUMBER3, UK);
    }
}
