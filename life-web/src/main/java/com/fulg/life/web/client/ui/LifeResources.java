package com.fulg.life.web.client.ui;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.CellTable.Resources;
import com.google.gwt.user.cellview.client.CellTable.Style;

public class LifeResources implements Resources {

    public static final LifeResources INSTANCE = new LifeResources();

    @Override public ImageResource cellTableFooterBackground() {

        return LifeImageResource.INSTANCE;
    }

    @Override public ImageResource cellTableHeaderBackground() {

        return LifeImageResource.INSTANCE;
    }

    @Override public ImageResource cellTableLoading() {

        return LifeImageResource.INSTANCE;
    }

    @Override public ImageResource cellTableSelectedBackground() {

        return LifeImageResource.INSTANCE;
    }

    @Override public ImageResource cellTableSortAscending() {

        return LifeImageResource.INSTANCE;
    }

    @Override public ImageResource cellTableSortDescending() {

        return LifeImageResource.INSTANCE;
    }

    @Override public Style cellTableStyle() {

        return TableStyle.INSTANCE;
    }
}