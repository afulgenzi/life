package com.fulg.life.msaccess.connection;

import java.io.IOException;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import com.healthmarketscience.jackcess.Database;

public class AccessConnectionProvider {
    private static final Logger LOG = LoggerFactory.getLogger(AccessConnectionProvider.class);

    private static final String DB_URL = "file://D|/db/agenda.mdb";

    // @Value("classpath*:/db/Agenda.mdb")
    // private Resource database;

    public static final String TB_NAME_ENTRATEUSCITE = "EntrateUscite";
    public static final String TB_NAME_VALUTE = "Valute";

    /**
     * Dynamically load the class sun.java.odbc.JdbcOdbcDriver
     */
    static {
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieve Connection to MSAccess Database
     * 
     * @return
     * @throws SQLException
     */
    // public static Connection getDirectConnection() throws DatabaseConnectionException {
    // String database = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=" + DIRECT_FILENAME + ";";
    // Connection conn;
    // try {
    // conn = DriverManager.getConnection(database, "", "");
    // } catch (SQLException ex) {
    // throw new DatabaseConnectionException("Cannot connect to:" + database, ex);
    // }
    //
    // return conn;
    // }

    // public static Connection getOdbcConnection() throws DatabaseConnectionException {
    // String database = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=" + ODBC_FILENAME + ";";
    // Connection conn;
    // try {
    // conn = DriverManager.getConnection(database, "", "");
    // } catch (SQLException ex) {
    // throw new DatabaseConnectionException("Cannot connect to:" + database, ex);
    // }
    //
    // return conn;
    // }

    public static Database getMsAccessConnection() throws DatabaseConnectionException {
        Database db;
        try {

            // // By Property
            // Properties props = new Properties();
            // InputStream stream = AccessConnectionProvider.class.getResourceAsStream("/local.properties");
            // props.load(stream);
            // dbUrl = props.getProperty("db.url");
            // // LOG.info("dbUrl: " + dbUrl);
            // File file = new File(dbUrl);
            // // LOG.info("dbUrl AbsolutePath: " + file.getAbsolutePath());
            // // LOG.info("dbUrl AbsolutePath exists: " + file.exists());
            // db = Database.open(file, true);

            // By Spring Resource
//            Resource dbFile = new ClassPathResource("/db/agenda.mdb");
            Resource dbFile = new UrlResource(DB_URL);
            db = Database.open(dbFile.getFile(), false);

        } catch (IOException e) {
            throw new DatabaseConnectionException("Unable to open db: " + DB_URL, e);
        }

        return db;
    }

    public static void main(String[] args) {
        try {
            Database db = getMsAccessConnection();
            LOG.info("Got connection!");

            for (String tableName : db.getTableNames()) {
                LOG.info("Table: " + tableName);
            }
        } catch (DatabaseConnectionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}