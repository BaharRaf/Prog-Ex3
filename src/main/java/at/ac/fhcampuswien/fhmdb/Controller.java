package at.ac.fhcampuswien.fhmdb;
//import com.jfoenix
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Controller {
    private final MovieAPI movieAPI = new MovieAPI();
    private final Gson gson = new Gson();

    public List<Movie> getAllMovies() throws IOException {
        String response = movieAPI.getAllMovies();
        Movie[] movies = gson.fromJson(response, Movie[].class);
        return Arrays.asList(movies);
    }

    public List<Movie> getMoviesByQueryAndGenre(String query, String genre) throws IOException {
        String response = movieAPI.getMoviesByQueryAndGenre(query, genre);
        Movie[] movies = gson.fromJson(response, Movie[].class);
        return Arrays.asList(movies);
    }

    public String getMostPopularActor(List<Movie> movies) {
        return movies.stream()
                .flatMap(movie -> movie.getCast().stream())
                .collect(Collectors.groupingBy(actor -> actor, Collectors.counting()))
                .entrySet().stream()
                .max(Comparator.comparingLong(entry -> entry.getValue()))
                .map(entry -> entry.getKey())
                .orElse(null);
    }

    public int getLongestMovieTitle(List<Movie> movies) {
        return movies.stream()
                .mapToInt(movie -> movie.getTitle().length())
                .max()
                .orElse(0);
    }

    public long countMoviesFrom(List<Movie> movies, String director) {
        return movies.stream()
                .filter(movie -> movie.getDirector().equals(director))
                .count();
    }

    // public List<Movie> getMoviesBetweenYears(List<Movie> movies, int startYear, int endYear) {
    //  return movies.stream()
    //    .filter(movie -> movie.getReleaseYear() > startYear && movie.getReleaseYear() <endYear)
    //   ;
}