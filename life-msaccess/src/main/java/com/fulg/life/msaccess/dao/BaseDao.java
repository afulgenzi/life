package com.fulg.life.msaccess.dao;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fulg.life.msaccess.connection.AccessConnectionProvider;
import com.fulg.life.msaccess.connection.DatabaseConnectionException;
import com.fulg.life.msaccess.dao.exception.AttributePopulationException;
import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.Table;

public abstract class BaseDao<T> {
    private static final Logger LOG = LoggerFactory.getLogger(BaseDao.class);

    protected List<T> entities = new ArrayList<T>();
    protected Class<T> daoClass;

    protected void loadData(Class<T> clazz) {
        LOG.info("Loading data for class: " + clazz.getSimpleName() + " ...");
        this.daoClass = clazz;
        Database db = null;
        entities = new ArrayList<T>();
        try {
            // Apertura Database
            db = AccessConnectionProvider.getMsAccessConnection();

            // Apertura Tabella
            Table tb = db.getTable(clazz.getSimpleName());

            int rows = tb.getRowCount();
            LOG.info("Table: " + clazz.getSimpleName() + ", Rows to be loaded: " + rows);

            // Lettura riga
            Map<String, Object> row = tb.getNextRow();
            int rowNum = 0;
            while (row != null) {

                // Istanziazione entity
                T entity = createInstance();

                // Popolamento attributi
                for (Column column : tb.getColumns()) {
                    if (rowNum == 0) {
                        LOG.info(tb.getName() + " --> " + column.getName());
                    }
                    populateField(entity, column, row);
                }

                // LOG.info("Adding entity: " + entity);
                entities.add(entity);

                rowNum++;

                // Lettura riga
                row = tb.getNextRow();
            }
            LOG.info("Table: " + clazz.getSimpleName() + ", Rows loaded: " + rows);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DatabaseConnectionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AttributePopulationException e) {
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
        LOG.info("Loaded data for class: " + clazz.getSimpleName() + ", occurs populated: " + entities.size());
    }

    public abstract Integer getNextPK();

    private void populateField(Object entity, Column column, Map<String, Object> row)
            throws AttributePopulationException {
        try {
            String colName = column.getName();
            String getterPrefix = ("BOOLEAN".equals(column.getType().name()) ? "is" : "get");
            String getMethodName = getterPrefix + colName.substring(0, 1) + colName.substring(1);
            String setMethodName = "set" + colName.substring(0, 1) + colName.substring(1);
            Class<?> retType = entity.getClass().getMethod(getMethodName, new Class[0]).getReturnType();
            Method setMethod = entity.getClass().getMethod(setMethodName, retType);
            Object colValue = row.get(colName);
            setMethod.invoke(entity, retType.cast(colValue));

            if (entity.getClass().getSimpleName().equals("Valute")) {
                LOG.info("Populating field, Entity <" + entity.getClass().getSimpleName() + ">, Field <" + colName
                        + ">, Value <" + colValue + ">");
            }
        } catch (ClassCastException e) {
            throw new AttributePopulationException("Cannot popoulate field " + column.getName() + " of class "
                    + entity.getClass().getSimpleName(), e);
        } catch (InvocationTargetException e) {
            throw new AttributePopulationException("Cannot popoulate field " + column.getName() + " of class "
                    + entity.getClass().getSimpleName(), e);
        } catch (NoSuchMethodException e) {
            throw new AttributePopulationException("Cannot popoulate field " + column.getName() + " of class "
                    + entity.getClass().getSimpleName(), e);
        } catch (IllegalAccessException e) {
            throw new AttributePopulationException("Cannot popoulate field " + column.getName() + " of class "
                    + entity.getClass().getSimpleName(), e);
        } catch (IllegalArgumentException e) {
            throw new AttributePopulationException("Cannot popoulate field " + column.getName() + " of class "
                    + entity.getClass().getSimpleName(), e);
        }
    }

    public List<T> getAll() {
        return entities;
    }

    public List<Map<String, Object>> getAllAsRawData() {
        List<Map<String, Object>> retRows = new ArrayList<Map<String, Object>>();

        Database db = null;
        entities = new ArrayList<T>();
        try {
            // Apertura Database
            db = AccessConnectionProvider.getMsAccessConnection();

            // Apertura Tabella
            Table tb = db.getTable(daoClass.getSimpleName());

            int rows = tb.getRowCount();
            LOG.info("Table: " + daoClass.getSimpleName() + ", Rows to be loaded: " + rows);

            // Lettura riga
            Map<String, Object> row = tb.getNextRow();
            int rowNum = 0;
            while (row != null) {
                retRows.add(row);
                rowNum++;

                row = tb.getNextRow();
            }
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DatabaseConnectionException e) {
            e.printStackTrace();
        } catch (IOException e) {
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

        return retRows;
    }

    protected abstract T createInstance();
}
