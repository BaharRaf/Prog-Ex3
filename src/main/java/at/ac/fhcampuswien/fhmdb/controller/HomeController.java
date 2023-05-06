package at.ac.fhcampuswien.fhmdb.controller;

import at.ac.fhcampuswien.fhmdb.FhmdbApplication;
import at.ac.fhcampuswien.fhmdb.ui.MovieAPI;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.SortedState;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXButton watchlistBtn;

    @FXML
    public JFXListView movieListView;

    @FXML
    public JFXComboBox genreComboBox;

    @FXML
    public JFXButton sortBtn;

    @FXML
    public JFXComboBox releaseYearComboBox;

    @FXML
    public JFXComboBox ratingFromComboBox;
    @FXML
    public Button resetBtn;
    @FXML
    public VBox mainVBox;


    public List<Movie> allMovies;

    public ObservableList<Movie> observableMovies = FXCollections.observableArrayList();

    public SortedState sortedState;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeState();
        initializeLayout();
    }

    public void initializeState() {
        allMovies = MovieAPI.getAllMovies(); //get the API movies hehe
        observableMovies.clear();
        observableMovies.addAll(allMovies); // add all movies to the observable list
        sortedState = SortedState.NONE;
    }

    public void initializeLayout() {
        movieListView.setItems(observableMovies);   // set the items of the listview to the observable list
        movieListView.setCellFactory(movieListView -> new MovieCell()); // apply custom cells to the listview

        // Add genres
        Object[] genres = Genre.values();   // get all genres
        genreComboBox.getItems().add("No filter");  // add "no filter" to the ComboBox
        genreComboBox.getItems().addAll(genres);    // add all genres to the ComboBox
        genreComboBox.setPromptText("Filter by Genre");

        // Add release years
        Integer[] releaseYears = new Integer[78];
        for (int i = 0; i < 78; i++) {
            releaseYears[i] = 2023 - i;
        }
        //releaseYearComboBox.getItems().add("No filter");
        releaseYearComboBox.getItems().addAll(releaseYears);
        releaseYearComboBox.setPromptText("Filter by release Year");

        // Add rating
        Double[] rating = new Double[]{1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00};
        ratingFromComboBox.getItems().addAll(rating);
        ratingFromComboBox.setPromptText("Filter by rating: selected or higher");
    }

    // sort movies based on sortedState
    // by default sorted state is NONE
    // afterwards it switches between ascending and descending
    public void sortMovies() {
        if (sortedState == SortedState.NONE || sortedState == SortedState.DESCENDING) {
            observableMovies.sort(Comparator.comparing(Movie::getTitle));
            sortedState = SortedState.ASCENDING;
        } else if (sortedState == SortedState.ASCENDING) {
            observableMovies.sort(Comparator.comparing(Movie::getTitle).reversed());
            sortedState = SortedState.DESCENDING;
        }
    }

    public List<Movie> filterByQuery(List<Movie> movies, String query){
        if(query == null || query.isEmpty()) return movies;

        if(movies == null) {
            throw new IllegalArgumentException("movies must not be null");
        }

        return movies.stream()
                .filter(Objects::nonNull)
                .filter(movie ->
                        movie.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                                movie.getDescription().toLowerCase().contains(query.toLowerCase())
                )
                .toList();

    }


    public List<Movie> filterByReleaseYear(List<Movie> movies, Integer releaseYear){
        if (releaseYear == null) return movies;

        if(movies == null) {
            throw new IllegalArgumentException("movies must not be null");
        }

        return movies.stream()
                .filter(Objects::nonNull)
                .filter(movie -> movie.getReleaseYear() == releaseYear)
                .toList();
    }


    public List<Movie> filterByRating(List<Movie> movies, String rating){
        if (rating == null) return movies;

        if(movies == null) {
            throw new IllegalArgumentException("movies must not be null");
        }


        double minRating = Double.parseDouble(rating.replace("+", ""));
        return movies.stream()
                .filter(Objects::nonNull)
                .filter(movie -> movie.getRating() >= minRating)
                .toList();
    }


    public List<Movie> filterByGenre(List<Movie> movies, Genre genre){
        if(genre == null) return movies;

        if(movies == null) {
            throw new IllegalArgumentException("movies must not be null");
        }

        return movies.stream()
                .filter(Objects::nonNull)
                .filter(movie -> movie.getGenres().contains(genre))
                .toList();
    }


    public void applyAllFilters(String searchQuery, Object genre, Integer releaseYear, Double rating) {
        List<Movie> filteredMovies = allMovies;

        if (!searchQuery.isEmpty()) {
            filteredMovies = filterByQuery(filteredMovies, searchQuery);
        }

        if (genre != null && !genre.toString().equals("No filter")) {
            filteredMovies = filterByGenre(filteredMovies, Genre.valueOf(genre.toString()));
        }

        if (releaseYear != null) {
            filteredMovies = filterByReleaseYear(filteredMovies, Integer.valueOf(releaseYear.toString()));
        }

        if (rating != null) {
            filteredMovies = filterByRating(filteredMovies, rating.toString());
        }

        observableMovies.clear();
        observableMovies.addAll(filteredMovies);
    }

    public void searchBtnClicked(ActionEvent actionEvent) {
        String searchQuery = searchField.getText().trim().toLowerCase();
        Object genre = genreComboBox.getSelectionModel().getSelectedItem();
        Integer releaseYear = (Integer) releaseYearComboBox.getSelectionModel().getSelectedItem();
        Double rating = (Double) ratingFromComboBox.getSelectionModel().getSelectedItem();

        applyAllFilters(searchQuery, genre, releaseYear, rating);

        if(sortedState != SortedState.NONE) {
            sortMovies();
        }
    }

    // RESET BUTTON
    public void clearBtnClicked(ActionEvent actionEvent) {
        genreComboBox.getSelectionModel().clearSelection();
        releaseYearComboBox.getSelectionModel().clearSelection();
        ratingFromComboBox.getSelectionModel().clearSelection();
        searchField.clear();

        initializeState();
    }


    public void sortBtnClicked(ActionEvent actionEvent) {
        sortMovies();
    }

    // SWITCH SCENE:
    public void switchToWatchlistView(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("watchlist-view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 890, 620);
            Stage stage = (Stage)mainVBox.getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException ioe) {
            System.err.println("Error while loading main page.");
        }
    }


    // Streams:

    static String getMostPopularActor(List<Movie> movies) {
        // Return an empty string if the movie list is null or empty
        if (movies == null || movies.isEmpty()) {
            return "";
        }
        // Create a stream of all actors in the main cast of the movies
        Stream<String> actorStream = movies.stream().flatMap(e -> e.getMainCast().stream());
        // Create a map of actor names to their frequency count in the main cast
        Map<String, Long> actorCounts = actorStream.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        // Find the actor with the highest count and return their name
        return actorCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                // Return an empty string if there is no most frequent actor
                .orElseGet(() -> "");
    }

    static String getLongestMovieTitle(List<Movie> movies){
       /* if (movies == null || movies.isEmpty()) {
            return null;
        }
        */
        return movies.stream()                    // Step 1: create a stream of movies
                .map(Movie::getTitle)            // Step 2: map each movie to its title
                .max(Comparator.comparingInt(String::length)) // Step 3: find the longest title using a comparator
                .orElse(null);                   // Step 4: return the longest title, or null if the stream is empty
    }

    static long countMoviesFrom(List<Movie> movies, String director){
        var result = movies.stream() //mehrere Director können an einen Film gearbeitet haben, deswegen: contains
                .filter(movie -> movie.getDirectors().contains(director)) //hier alle Filme, wo der Director drinnen ist
                .count(); //schauen wie lange dieser Stream noch ist
        return result;
    }

    static List<Movie> getMoviesBetweenYears(List<Movie> movies, int startYear, int endYear){
        if (movies == null || movies.isEmpty()) {
            return null;
        }
        return movies.stream()                      // Step 2: create a stream of movies
                .filter(movie -> movie.getReleaseYear() >= startYear && movie.getReleaseYear() <= endYear) // Step 3: filter the stream to include only movies released between startYear and endYear
                .collect(Collectors.toList());     // Step 4: collect the filtered movies into a list and return it
    }

}