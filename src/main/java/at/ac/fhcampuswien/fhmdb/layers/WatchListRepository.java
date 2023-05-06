package at.ac.fhcampuswien.fhmdb.layers;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class WatchListRepository {
    //EXTRA LAYER TO ACCESS DAO FROM DATABASE
    Dao<WatchList, Long> dao;
    public WatchListRepository(){ //exception von Database
        try {
            this.dao = Data.getDatabase().getWatchlistDao();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //indication has WatchlistEntity as a parameter, in the video the object is converted into the entity
    public void addToWatchList(Movie movie) throws SQLException {
        dao.create(movieToWatchList(movie));
    }
    public void removeFromWatchList(Movie movie){

    }
    public List<WatchList> getAll() throws SQLException {
        return dao.queryForAll();
    }


    private WatchList movieToWatchList(Movie movie){
        return new WatchList(movie.getApiId());
    }

}