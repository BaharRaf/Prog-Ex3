package at.ac.fhcampuswien.fhmdb.application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Movie {

    private final String title;
    private final String description;
    private final List<Genre> genres;
    //neue hinzufügen, damit man das JSON in das Objekt umschreiben kann. (sonst kennt sich der Compiler nicht aus wie der das Überschreiben soll)
    //alle Sachen die wir zurückbekommen von unserer Antwort
    private final String apiId;
    private final int releaseYear;
    private final String imgUrl;
    private final int lengthInMinutes;
    private final List<String> directors;
    private final List<String> writers;
    private final List<String> mainCast;
    private final double rating;



    public Movie(String title, String description, List<Genre> genres) {
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.apiId = null;
        this.releaseYear = 0;
        this.imgUrl = "";
        this.lengthInMinutes = 0;
        this.directors = null;
        this.writers = null;
        this.mainCast = null;
        this.rating = 0;

    }

    // Überladen - damit nicht alles abändern muss
    public Movie(String title, String description, List<Genre> genres,String apiId, int releaseYear, String imgUrl, int lengthInMinutes, List<String> directors, List<String> writers, List<String> mainCast, double rating) {
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.apiId = apiId;
        this.releaseYear = releaseYear;
        this.imgUrl = imgUrl;
        this.lengthInMinutes = lengthInMinutes;
        this.directors = directors;
        this.writers = writers;
        this.mainCast = mainCast;
        this.rating = rating;
    }

    // Überladen zum Ausprobieren der Streams
    public Movie(String title, List<String> directors, List<String> mainCast, int releaseYear) {
        this.title = title;
        this.description = "";
        this.genres = null;
        this.apiId = null;
        this.releaseYear = releaseYear;
        this.imgUrl = "";
        this.lengthInMinutes = 0;
        this.directors = directors;
        this.writers = null;
        this.mainCast = mainCast;
        this.rating = 0;

    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(obj == this) {
            return true;
        }
        if(!(obj instanceof Movie other)) {
            return false;
        }
        return this.title.equals(other.title) && this.description.equals(other.description) && this.genres.equals(other.genres);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public String getApiId() {
        return apiId;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public int getLengthInMinutes() {
        return lengthInMinutes;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public List<String> getWriters() {
        return writers;
    }

    public List<String> getMainCast() {
        return mainCast;
    }

    public double getRating() {
        return rating;
    }

    // Not used anymore

    public static List<Movie> initializeMovies(){
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie(
                "Life Is Beautiful",
                "When an open-minded Jewish librarian and his son become victims of the Holocaust, he uses a perfect mixture of will, humor, and imagination to protect his son from the dangers around their camp." ,
                Arrays.asList(Genre.DRAMA, Genre.ROMANCE)));
        movies.add(new Movie(
                "The Usual Suspects",
                "A sole survivor tells of the twisty events leading up to a horrific gun battle on a boat, which begin when five criminals meet at a seemingly random police lineup.",
                Arrays.asList(Genre.CRIME, Genre.DRAMA, Genre.MYSTERY)));
        movies.add(new Movie(
                "Puss in Boots",
                "An outlaw cat, his childhood egg-friend, and a seductive thief kitty set out in search for the eggs of the fabled Golden Goose to clear his name, restore his lost honor, and regain the trust of his mother and town.",
                Arrays.asList(Genre.COMEDY, Genre.FAMILY, Genre.ANIMATION)));
        movies.add(new Movie(
                "Avatar",
                "A paraplegic Marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.",
                Arrays.asList(Genre.ANIMATION, Genre.DRAMA, Genre.ACTION)));
        movies.add(new Movie(
                "The Wolf of Wall Street",
                "Based on the true story of Jordan Belfort, from his rise to a wealthy stock-broker living the high life to his fall involving crime, corruption and the federal government.",
                Arrays.asList(Genre.DRAMA, Genre.ROMANCE, Genre.BIOGRAPHY)));

        return movies;
    }

}