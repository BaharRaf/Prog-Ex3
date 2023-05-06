package at.ac.fhcampuswien.fhmdb.exception;

public class DatabaseException extends Exception {

    public DatabaseException(String errorMessage){
        super(errorMessage);
    }
}
