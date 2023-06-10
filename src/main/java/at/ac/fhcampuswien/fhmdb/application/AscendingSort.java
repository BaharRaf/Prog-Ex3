package at.ac.fhcampuswien.fhmdb.application;

import java.util.Comparator;
import java.util.List;

public class AscendingSort implements SortState{
    @Override
    public List<Movie> sort(List<Movie> movies) {
        movies.sort(Comparator.comparing(Movie::getTitle));
        return movies;
    }
}