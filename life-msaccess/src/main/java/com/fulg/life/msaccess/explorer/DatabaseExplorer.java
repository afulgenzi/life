package com.fulg.life.msaccess.explorer;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fulg.life.msaccess.connection.AccessConnectionProvider;
import com.fulg.life.msaccess.connection.DatabaseConnectionException;
import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.Table;

public class DatabaseExplorer {
	private static final Logger LOG = LoggerFactory.getLogger(DatabaseExplorer.class);

	public static Set<String> getTableNames() {
		Database db = null;
		try {
			db = AccessConnectionProvider.getMsAccessConnection();
			return db.getTableNames();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DatabaseConnectionException e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				try {
					db.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public static List<Column> getTableFields(String tbName) {
		Database db = null;
		try {
			db = AccessConnectionProvider.getMsAccessConnection();
			Table table = db.getTable(tbName);
			return table.getColumns();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DatabaseConnectionException e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				try {
					db.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
