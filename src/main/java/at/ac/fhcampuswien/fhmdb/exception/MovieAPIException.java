package at.ac.fhcampuswien.fhmdb.exception;

import at.ac.fhcampuswien.fhmdb.ui.MovieAPI;

public class MovieAPIException extends Exception{
    public MovieAPIException(String errorMessage){
        super(errorMessage);
    }
}
