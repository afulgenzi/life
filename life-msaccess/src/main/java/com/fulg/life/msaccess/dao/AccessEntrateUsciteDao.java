package com.fulg.life.msaccess.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fulg.life.msaccess.connection.AccessConnectionProvider;
import com.fulg.life.msaccess.connection.DatabaseConnectionException;
import com.fulg.life.msaccess.entity.EntrateUscite;
import com.fulg.life.msaccess.entity.Valute;
import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.Cursor;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.Table;

public class AccessEntrateUsciteDao extends BaseDao<EntrateUscite> {
    private static final Logger LOG = LoggerFactory.getLogger(AccessEntrateUsciteDao.class);

    private AccessValuteDao accessValuteDao;

    public AccessEntrateUsciteDao() {
        loadData(EntrateUscite.class);
    }

    public EntrateUscite getByIdEU(long idEU) {
        for (EntrateUscite entity : entities) {
            if (entity.getIDEU() == idEU) {
                return entity;
            }
        }
        return null;
    }

    /**
     * Month 1-12
     * 
     * @param year
     * @param month
     * @return
     */
    public List<EntrateUscite> getByMonth(int year, int month) {
        List<EntrateUscite> retValue = new ArrayList<EntrateUscite>();
        for (EntrateUscite entity : entities) {
            Calendar cal = new GregorianCalendar();
            try {
                cal.setTime(entity.getData());
            } catch (NullPointerException ex) {
                LOG.warn("Found NULL on field EntrateUscite.Data (IDEU: " + entity.getIDEU() + ")");
                cal.setTime(new Date());
            }
            int euYear = cal.get(Calendar.YEAR);
            int euMonth = cal.get(Calendar.MONTH);
            if (euYear == year && (euMonth + 1) == month) {
                retValue.add(entity);
            }
        }
        return retValue;
    }

    public List<EntrateUscite> getByDescription(String description) {
        List<EntrateUscite> retValue = new ArrayList<EntrateUscite>();
        for (EntrateUscite entity : entities) {
            if (entity.getDescrizione().toLowerCase().contains(description.toLowerCase())) {
                retValue.add(entity);
            }
        }
        return retValue;
    }

    /**
     * Sum all amounts before date
     * 
     * @param date
     * @return
     */
    public Double getBalanceBeforeDate(Date date) {
        LOG.info("Search for balance before: " + date);
        Double balance = 0.0;
        for (EntrateUscite entity : entities) {
            if (entity.getData().before(date)) {
                Valute valuta = accessValuteDao.getByIdValute(entity.getIDValutaImporto());
                logIdInMonth(entity, valuta, 2013, 4);
                if ("E".equals(entity.getEU())) {
                    balance += entity.getImporto() * valuta.getValoreInEuro();
                } else {
                    balance -= entity.getImporto() * valuta.getValoreInEuro();
                }
            }
        }
        LOG.info("Found balance: " + balance);
        return balance;
    }

    public EntrateUscite update(EntrateUscite eu) {
        String tableName = eu.getClass().getSimpleName();
        LOG.info("Updating class: " + tableName + " ...");
        Database db = null;
        try {
            // Apertura Database
            db = AccessConnectionProvider.getMsAccessConnection();

            // Apertura Tabella
            Table tb = db.getTable(tableName);

            // Lettura riga
            Cursor cursor = Cursor.createCursor(tb);
            // Show me all rows with column 'town' = nyc
            if (cursor.findFirstRow(tb.getColumn("IDEU"), eu.getIDEU())) {
                System.out.println(cursor.getCurrentRow());
                Column column;

                column = tb.getColumn("Data");
                cursor.setCurrentRowValue(column, eu.getData());

                column = tb.getColumn("Descrizione");
                cursor.setCurrentRowValue(column, eu.getDescrizione());

                column = tb.getColumn("Importo");
                cursor.setCurrentRowValue(column, eu.getImporto());

                column = tb.getColumn("EU");
                cursor.setCurrentRowValue(column, eu.getEU());

                loadData(EntrateUscite.class);

            } else {
                LOG.warn("EU not found. IDEU: " + eu.getIDEU());
            }
            // LOG.info("Table: "+clazz.getSimpleName() + ", Rows loaded: " +
            // rows);
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
        LOG.info("Updated class: " + eu.getClass().getSimpleName());
        return eu;
    }

    public EntrateUscite create(EntrateUscite eu) {
        String tableName = eu.getClass().getSimpleName();
        LOG.info("Inserting class: " + tableName + " ...");
        Database db = null;
        try {
            // Apertura Database
            db = AccessConnectionProvider.getMsAccessConnection();

            // Apertura Tabella
            Table tb = db.getTable(tableName);

            tb.addRow(eu.getIDEU(), eu.getIDEUMaster(), eu.getData(), eu.getDescrizione(), eu.getImporto(),
                    eu.getIDValutaImporto(), eu.getEU(), eu.isPagato(), eu.isPreavviso(), eu.getGiorniPreavviso(),
                    eu.isValido(), eu.getTipologia());

            loadData(EntrateUscite.class);
            // LOG.info("Table: "+clazz.getSimpleName() + ", Rows loaded: " +
            // rows);
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
        LOG.info("Inserting class: " + eu.getClass().getSimpleName());
        return eu;
    }

    public Boolean remove(EntrateUscite eu) {
        String tableName = eu.getClass().getSimpleName();
        LOG.info("Removing instance of class: " + tableName + " ...");
        Database db = null;
        try {
            // Apertura Database
            db = AccessConnectionProvider.getMsAccessConnection();

            // Apertura Tabella
            Table tb = db.getTable(tableName);

            // Lettura riga
            Cursor cursor = Cursor.createCursor(tb);
            // Show me all rows with column 'town' = nyc
            if (cursor.findFirstRow(tb.getColumn("IDEU"), eu.getIDEU())) {
                cursor.getCurrentRow();
                cursor.deleteCurrentRow();
                LOG.info("Deleted row, IDEU: " + eu.getIDEU());
            } else {
                LOG.warn("EU not found. IDEU: " + eu.getIDEU());
            }

            loadData(EntrateUscite.class);

            return Boolean.TRUE;
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
        LOG.info("Removed instance of class: " + eu.getClass().getSimpleName());
        return Boolean.FALSE;
    }

    @Override
    protected EntrateUscite createInstance() {
        return new EntrateUscite();
    }

    private void logIdInMonth(EntrateUscite entity, Valute valuta, int year, int month) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(entity.getData());
        int entityYear = cal.get(Calendar.YEAR);
        int entityMonth = cal.get(Calendar.MONTH);
        if (entityMonth == month && entityYear == year) {
            LOG.info("Adding '" + entity.getEU() + "' --> " + (entity.getImporto() * valuta.getValoreInEuro()));
        }
    }

    @Override
    public Integer getNextPK() {
        int maxPk = 0;
        for (EntrateUscite entity : entities) {
            if (entity.getIDEU() > maxPk) {
                maxPk = entity.getIDEU();
            }
        }
        return Integer.valueOf(maxPk + 1);
    }

    protected AccessValuteDao getAccessValuteDao() {
        return accessValuteDao;
    }

    public void setAccessValuteDao(AccessValuteDao accessValuteDao) {
        this.accessValuteDao = accessValuteDao;
    }

}
