module at.ac.fhcampuswien.fhmdb {
    requires javafx.controls;
    requires javafx.fxml;
    requires ormlite.jdbc;


    requires com.jfoenix;
    requires okhttp3;
    requires com.google.gson;
    requires java.sql;

    opens at.ac.fhcampuswien.fhmdb.models to com.google.gson; //damit das alles funktioniert
    opens at.ac.fhcampuswien.fhmdb.ui to ormlite.jdbc;//exception
    opens at.ac.fhcampuswien.fhmdb.layers to ormlite.jdbc;//exception

    opens at.ac.fhcampuswien.fhmdb to javafx.fxml;
    exports at.ac.fhcampuswien.fhmdb.models;
    exports at.ac.fhcampuswien.fhmdb;
    exports at.ac.fhcampuswien.fhmdb.controller;
    opens at.ac.fhcampuswien.fhmdb.controller to javafx.fxml;
}