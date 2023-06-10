package at.ac.fhcampuswien.fhmdb.application;

import java.util.List;

public class DefaultSort implements SortState{
    @Override
    public List<Movie> sort(List<Movie> movies) {
        return movies;
    }
}