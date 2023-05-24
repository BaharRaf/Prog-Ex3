module at.ac.fhcampuswien.fhmdb {
    requires javafx.controls;
    requires javafx.fxml;
    requires ormlite.jdbc;


    requires com.jfoenix;
    requires okhttp3;
    requires com.google.gson;
    requires java.sql;



    opens at.ac.fhcampuswien.fhmdb.application to com.google.gson; //damit das alles funktioniert
    opens at.ac.fhcampuswien.fhmdb.ui to ormlite.jdbc;//exception
    opens at.ac.fhcampuswien.fhmdb.db to ormlite.jdbc;//exception

    opens at.ac.fhcampuswien.fhmdb to javafx.fxml;
    exports at.ac.fhcampuswien.fhmdb;
    exports at.ac.fhcampuswien.fhmdb.db;
    exports at.ac.fhcampuswien.fhmdb.application.controller;


    opens at.ac.fhcampuswien.fhmdb.application.controller to javafx.fxml;
}