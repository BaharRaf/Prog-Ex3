package at.ac.fhcampuswien.fhmdb.db;
import at.ac.fhcampuswien.fhmdb.application.Movie;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class WatchListRepository {
    //EXTRA LAYER TO ACCESS DAO FROM DATABASE
    Dao<WatchListEntity, Long> dao;
    public WatchListRepository(){ //exception von Database
        try {
            this.dao = Database.getDatabase().getWatchlistDao();
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
    public List<WatchListEntity> getAll() throws SQLException {
        return dao.queryForAll();
    }


    private WatchListEntity movieToWatchList(Movie movie){
        return new WatchListEntity(movie.getTitle(),movie.getDescription(),WatchListEntity.genresToString(movie.getGenres()),movie.getApiId(),movie.getReleaseYear(),movie.getImgUrl(),movie.getLengthInMinutes(), movie.getRating());
    }
}