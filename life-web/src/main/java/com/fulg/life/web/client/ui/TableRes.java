package com.fulg.life.web.client.ui;

import com.google.gwt.user.cellview.client.CellTable;

public interface TableRes extends CellTable.Resources {
	@Source({ CellTable.Style.DEFAULT_CSS, "table.css" })
	TableStyle cellTableStyle();

	interface TableStyle extends CellTable.Style {
	}
}