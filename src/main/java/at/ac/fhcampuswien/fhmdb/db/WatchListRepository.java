package at.ac.fhcampuswien.fhmdb.db;
import at.ac.fhcampuswien.fhmdb.application.Movie;
import at.ac.fhcampuswien.fhmdb.application.Observable;
import at.ac.fhcampuswien.fhmdb.application.Observer;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WatchListRepository  implements Observable {
    private static final WatchListRepository instance = new WatchListRepository();

    public static WatchListRepository getInstance() {
        return instance;
    }

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
        QueryBuilder<WatchListEntity, Long> queryBuilder = dao.queryBuilder();
        queryBuilder.selectColumns("title");
        PreparedQuery<WatchListEntity> preparedQuery = queryBuilder.prepare();
        return dao.query(preparedQuery);
    }




    private WatchListEntity movieToWatchList(Movie movie){
        return new WatchListEntity(movie.getTitle(),movie.getDescription(),WatchListEntity.genresToString(movie.getGenres()),movie.getApiId(),movie.getReleaseYear(),movie.getImgUrl(),movie.getLengthInMinutes(), movie.getRating());
    }



   private List<Observer> obs ;

    private List<String> Watchlist= new ArrayList<>();


        public void addToWatchList(String movieName) {
            if (!Watchlist.contains(movieName)){
                Watchlist.add(movieName);
                notifyObservers( "Movie successfully added to watchlist");
            }
            else {
                notifyObservers("Movie already on watchlist");
            }
        }


    public void registerObserver(Observer observer) {
        obs.add(observer);

    }

    public void removeObserver(Observer observer) {
        obs.remove(observer);

    }

    public void notifyObservers(String message) {
        for (Observer observer: obs){
            observer.update(message);
        }

    }
}