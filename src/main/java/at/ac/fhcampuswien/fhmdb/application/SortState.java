package at.ac.fhcampuswien.fhmdb.application;

import java.util.List;

public interface SortState {
    //sort() method takes a list of movies and return the sorted list.
    List<Movie> sort(List<Movie> movies);
}

