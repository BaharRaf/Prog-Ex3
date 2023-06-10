package at.ac.fhcampuswien.fhmdb.application.controller;

import at.ac.fhcampuswien.fhmdb.FhmdbApplication;
import at.ac.fhcampuswien.fhmdb.ui.ClickEventHandler;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class DetailViewController {

    @FXML
    private VBox mainVBox;
    @FXML
    private Label titleLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    public JFXButton homeBtn;

    private ClickEventHandler<ActionEvent> homeButtonClickedHandler;

    public void setMovieDetails(String title, String description) {
        titleLabel.setText(title);
        descriptionLabel.setText(description);
    }

    public void setHomeButtonClickedHandler(ClickEventHandler<ActionEvent> handler) {
        this.homeButtonClickedHandler = handler;
    }

    public void switchToMainPage(ActionEvent event) {
        if (homeButtonClickedHandler != null) {
            ActionEvent newEvent = new ActionEvent();
            homeButtonClickedHandler.onClick(newEvent, false, null);
        }
    }
}
