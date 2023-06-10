package at.ac.fhcampuswien.fhmdb.application;

import java.util.Comparator;
import java.util.List;

public class DescendingSort implements SortState{
    public List<Movie> sort(List<Movie> movies) {
        movies.sort(Comparator.comparing(Movie::getTitle).reversed());
        return movies;
    }
}
