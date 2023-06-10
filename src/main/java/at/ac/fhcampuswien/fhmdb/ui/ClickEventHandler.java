package at.ac.fhcampuswien.fhmdb.ui;


import javafx.scene.control.Button;



@FunctionalInterface
public interface ClickEventHandler<T> {

    void onClick(T t, boolean isWatchlistCell, Button watchBtn);
}