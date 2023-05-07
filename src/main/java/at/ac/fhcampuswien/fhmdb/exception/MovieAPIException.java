package at.ac.fhcampuswien.fhmdb.exception;

import at.ac.fhcampuswien.fhmdb.db.MovieAPI;

public class MovieAPIException extends Exception{
    public MovieAPIException(String errorMessage){
        super(errorMessage);
    }
}
