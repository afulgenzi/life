package com.fulg.life.msaccess.connection;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fulg.life.msaccess.test.AbstractTest;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.Table;

public class AccessConnectionTest extends AbstractTest {
    private final static Logger LOG = LoggerFactory.getLogger(AccessConnectionTest.class);

    @Test
    public void testMSAccessConnection() throws IOException {
        Database db = null;
        try {
            db = AccessConnectionProvider.getMsAccessConnection();
            for (String tbName : db.getTableNames()) {
                Table tb = db.getTable(tbName);
                int rows = tb.getRowCount();
                LOG.info(tbName + ", Rows: " + rows);
            }
        } catch (DatabaseConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Assert.assertNotNull(db);
    }
}