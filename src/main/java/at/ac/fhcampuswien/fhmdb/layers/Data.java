package at.ac.fhcampuswien.fhmdb.layers;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class Data {
    public static final String DB_URL = "jdbc:h2:file: ./db/watchlistdb"; //find DB_URL in documentation (embedded)
    public static final String user = "user";
    public static final String password = "pass";

    private static ConnectionSource connectionSource; //connectionSource als attribut, weil wir sie immer wieder brauchen
    private Dao<WatchList, Long> dao; //DAO provides create,delete,update and other operations for database

    private static Data instance;
    private Data() throws SQLException{ //exception zu getDatabase
        // when creating an instance -> connection to database is created, DAO is created, table is created
        createConnectionSource(); //
        dao = DaoManager.createDao(connectionSource, WatchList.class);
        createTables();

    }
    public static Data getDatabase() throws SQLException { //exception zu WatchlistRepository
        if(instance == null){ //if no instance exists create new Database instance, only one instance should exist(singleton)
            instance = new Data();
        }
        return instance;
    }

    private static void createTables() throws SQLException { //create table form WatchlistEntity attributes
        TableUtils.createTableIfNotExists(connectionSource, WatchList.class);
    }


    private static void createConnectionSource() throws SQLException {
        connectionSource = new JdbcConnectionSource(DB_URL, user, password); //connection protected with user and password
    }

    public Dao getWatchlistDao(){
        return this.dao;
    }


}
