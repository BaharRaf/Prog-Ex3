package at.ac.fhcampuswien.fhmdb.controller;

import at.ac.fhcampuswien.fhmdb.FhmdbApplication;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;


public class WatchlistViewController {

    @FXML
    public VBox mainVBox;
    @FXML
    public JFXListView movieWatchlistView;
    @FXML
    public JFXButton mainPageBtn;


    // SWITCH SCENE:
    public void switchToMainPage(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("home-view.fxml"));
        try{
            Scene scene = new Scene(fxmlLoader.load(), 890, 620);
            Stage stage = (Stage)mainVBox.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            System.err.println("Error while loading main page.");
        }
    }
}