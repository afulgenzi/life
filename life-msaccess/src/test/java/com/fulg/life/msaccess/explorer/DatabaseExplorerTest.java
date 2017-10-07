package com.fulg.life.msaccess.explorer;

import java.io.FileWriter;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fulg.life.msaccess.connection.AccessConnectionTest;
import com.fulg.life.msaccess.test.AbstractTest;
import com.healthmarketscience.jackcess.Column;

public class DatabaseExplorerTest extends AbstractTest {
    private final static Logger LOG = LoggerFactory.getLogger(AccessConnectionTest.class);

    @Test
    @Ignore
    public void testTableAndColumns() {
        LOG.info("*********************************");
        LOG.info("***       T A B E L L E       ***");
        for (String tbName : DatabaseExplorer.getTableNames()) {
            LOG.info("*** " + tbName);
            for (Column column : DatabaseExplorer.getTableFields(tbName)) {
                LOG.info("*** --> " + column.getName() + "\t(" + column.getType().name() + ")");
            }
        }
        LOG.info("*********************************");
    }

    @Test
    public void testClassSkeleton() {
        LOG.info("*********************************");
        LOG.info("***       T A B E L L E       ***");
        for (String tbName : DatabaseExplorer.getTableNames()) {
            LOG.info("*** " + tbName);
            StringBuilder classBody = new StringBuilder("package com.fulg.life.msaccess.entity;\n\n");
            classBody.append("public class " + tbName + "{\n");
            String type = null;
            for (Column column : DatabaseExplorer.getTableFields(tbName)) {
                type = "";
                if ("TEXT".equals(column.getType().name())) {
                    type = "String";
                } else if ("SHORT_DATE_TIME".equals(column.getType().name())) {
                    type = "Date";
                } else if ("LONG".equals(column.getType().name())) {
                    type = "long";
                } else if ("DOUBLE".equals(column.getType().name())) {
                    type = "double";
                } else if ("MONEY".equals(column.getType().name())) {
                    type = "double";
                } else if ("FLOAT".equals(column.getType().name())) {
                    type = "float";
                } else if ("BOOLEAN".equals(column.getType().name())) {
                    type = "boolean";
                } else if ("BYTE".equals(column.getType().name())) {
                    type = "int";
                }
                classBody.append((type.equals("") ? "[" + column.getType().name() + " -> ?]" : type) + " "
                        + column.getName() + ";\n");
                LOG.info("*** --> " + column.getName() + "\t(" + column.getType().name() + ")");
            }
            classBody.append("}\n");
            try {
                FileWriter fw = new FileWriter(tbName + ".java");
                fw.write(classBody.toString());
                fw.flush();
                fw.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        LOG.info("*********************************");
    }

}
