package at.ac.fhcampuswien.fhmdb.application.controller;

import at.ac.fhcampuswien.fhmdb.FhmdbApplication;
import at.ac.fhcampuswien.fhmdb.application.Observer;
import at.ac.fhcampuswien.fhmdb.db.WatchListEntity;
import at.ac.fhcampuswien.fhmdb.db.WatchListRepository;
import at.ac.fhcampuswien.fhmdb.ui.ClickEventHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class WatchlistViewController  {

    @FXML
    public VBox mainVBox;
    @FXML
    public JFXListView<String> movieWatchlistView;
    @FXML
    public JFXButton watchlistBtn;


    private WatchListRepository repository = WatchListRepository.getInstance();

    public void initialize() {
        try {
            List<WatchListEntity> watchlistMovies = repository.getAll();

            // Clear the existing items in the movieWatchlistView
           // movieWatchlistView.getItems().clear();

            // Set the watchlistMovies titles to the movieWatchlistView for display
            movieWatchlistView.getItems().addAll(watchlistMovies.stream().map(WatchListEntity::getTitle).toList());

            // Set the click event handler for the movieWatchlistView items
            movieWatchlistView.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    String selectedItem = movieWatchlistView.getSelectionModel().getSelectedItem();
                    // Handle the double click event on a watchlist item
                    handleWatchlistItemDoubleClick(selectedItem);
                }
            });

            // Set the click event handler for the watchlistBtn button
            watchlistBtn.setOnAction(this::watchlistBtnClicked);



        } catch (SQLException e) {
            // Handle any exceptions that may occur during database operations
            e.printStackTrace();
        }
    }

    // Event handler for the mainPageBtn button
    public void switchToMainPage(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("home-view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 890, 620);
            Stage stage = (Stage) mainVBox.getScene().getWindow();
            stage.setScene(scene);
            stage.show(); // Show the main page scene
        } catch (IOException e) {
            System.err.println("Error while loading main page.");
            e.printStackTrace();
        }
    }

    // Event handler for double-clicking a watchlist item
    private void handleWatchlistItemDoubleClick(String selectedItem) {
        // Implement the logic for what should happen when a watchlist item is double-clicked
        System.out.println("Watchlist item double-clicked: " + selectedItem);
    }

    // Event handler for the watchlistBtn button
    public void watchlistBtnClicked(ActionEvent event) {
        System.out.println("Watchlist button clicked");

        List<WatchListEntity> watchlistMovies;
        try {
            watchlistMovies = repository.getAll();
            // Clear the existing items in the movieWatchlistView
            movieWatchlistView.getItems().clear();
            // Set the watchlistMovies titles to the movieWatchlistView for display
            movieWatchlistView.getItems().addAll(watchlistMovies.stream().map(WatchListEntity::getTitle).toList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Event handler for removing a movie from the watchlist
    public void removeMovieFromWatchlist(ActionEvent event) {
        String selectedItem = movieWatchlistView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                repository.removeMovieByTitle(selectedItem);
                movieWatchlistView.getItems().remove(selectedItem);
                System.out.println("Movie removed from watchlist: " + selectedItem);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    // Event handler for the showDetailBtn button
    public void showDetailBtnClicked(ActionEvent event) {
        String selectedItem = movieWatchlistView.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            // Get the movie details from the repository or any other source
            String title = selectedItem;
            String description = ""; // Replace with the actual description

            // Load the detail view FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("detail-view.fxml"));
            try {
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show(); // Show the detail view scene

                // Get the controller instance and set the movie details
                DetailViewController controller = fxmlLoader.getController();
                controller.setMovieDetails(title, description);
            } catch (IOException e) {
                System.err.println("Error while loading detail view.");
                e.printStackTrace();
            }
        }
    }




    // Define the ClickEventHandler variable
    private ClickEventHandler<ActionEvent> clickEventHandler;

    // Setter method for the ClickEventHandler
    public void setClickEventHandler(ClickEventHandler<ActionEvent> clickEventHandler) {
        this.clickEventHandler = clickEventHandler;
    }


}