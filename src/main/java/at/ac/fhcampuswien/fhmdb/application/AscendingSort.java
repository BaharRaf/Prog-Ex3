package at.ac.fhcampuswien.fhmdb.application;

import java.util.Comparator;
import java.util.List;

public class AscendingSort implements SortState{
    public List<Movie> sort(List<Movie> movies) {
        movies.sort(Comparator.comparing(Movie::getTitle)); // assuming Movie has a getTitle() method
        return movies;
    }
}
