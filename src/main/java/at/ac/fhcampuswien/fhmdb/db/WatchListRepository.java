package at.ac.fhcampuswien.fhmdb.db;
import at.ac.fhcampuswien.fhmdb.application.Movie;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.List;

public class WatchListRepository  {
    private static final WatchListRepository instance = new WatchListRepository();

    public static WatchListRepository getInstance() {
        return instance;
    }

    //EXTRA LAYER TO ACCESS DAO FROM DATABASE
    Dao<WatchListEntity, Long> dao;
    private WatchListRepository(){ //exception von Database
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
    public void removeFromWatchList(Movie movie) throws SQLException {
        String title = movie.getTitle();

        // Get the watchlist entity with the given title
        WatchListEntity watchListEntity = dao.queryBuilder().where().eq("title", title).queryForFirst();

        if (watchListEntity != null) {
            // Delete the watchlist entity from the database
            dao.delete(watchListEntity);
        }
    }
    public void removeMovieByTitle(String title) throws SQLException {
        DeleteBuilder<WatchListEntity, Long> deleteBuilder = dao.deleteBuilder();
        deleteBuilder.where().eq("title", title);
        deleteBuilder.delete();
    }
    public List<WatchListEntity> getAll() throws SQLException {
        return dao.queryForAll();
    }



    private WatchListEntity movieToWatchList(Movie movie){
        return new WatchListEntity(movie.getTitle(),movie.getDescription(),WatchListEntity.genresToString(movie.getGenres()),movie.getApiId(),movie.getReleaseYear(),movie.getImgUrl(),movie.getLengthInMinutes(), movie.getRating());
    }
}