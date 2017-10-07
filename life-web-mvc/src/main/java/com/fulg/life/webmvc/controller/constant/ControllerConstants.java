package com.fulg.life.webmvc.controller.constant;

/**
 * Created by alessandro.fulgenzi on 26/07/16.
 */

/*
 * TedBaker project.
 * Copyright (c) 2012-2014 Neoworks Limited
 * All rights reserved.
 */


/**
 */
public interface ControllerConstants {

    interface PageUrls {
        interface Json {
            String BANK_ACCOUNTS = "/accounts/json";
        }
    }

    interface Views {
        String INVOICES = "invoices-list";

        String CATEGORIES = "categories";

        String COCKPIT_IMPORTS = "cockpit-imports";
        String COCKPIT_CATEGORIES = "cockpit-categories";

        String MOVEMENTS = "movements";
        String MOVEMENTS_IMPORT = "movements-import";
    }

    interface PageTypes {
        String PAGE_TYPE_TRANSACTIONS = "transactions";
        String PAGE_TYPE_INVOICES = "invoices";
    }
}
