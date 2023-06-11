package at.ac.fhcampuswien.fhmdb.db;

import at.ac.fhcampuswien.fhmdb.application.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.*;
import com.google.gson.*;


public class MovieAPI {
    private static SortState state = new DefaultSort();
    private static final String URL_API = "https://prog2.fh-campuswien.ac.at/movies";
    private static final String URL = "http://localhost:8080/movies";
    private static final String DELIMITER = "&"; //Separator


    public static void sortAscending() {
        state = new AscendingSort();
    }
    public static void sortDescending() {
        state = new DescendingSort();
    }
    public static void unsorted() {
        state = new DefaultSort();
    }





    //damit wir die dann hinschicken können hehe
    private static String buildURL(String query, Genre genre, String releaseYear, String ratingFrom){

        URLBuilder url = new URLBuilder();

        //Checken, ob was übergeben wurde (Wenn ein Parameter übergeben wurde, brauchen wir ein "?"
        if((query != null && !query.isEmpty())|| genre != null || releaseYear != null || ratingFrom != null) { //empty ""
            if(query != null && !query.isEmpty()){
                url.withQuery(query);
            }
            if(genre != null){
                url.withGenre(genre);
            }
            if(releaseYear != null){
                url.withReleaseYear(releaseYear);
            }
            if(ratingFrom != null){
                url.withRatingFrom(ratingFrom);
            }
        }
        return url.build();
    }

    //Ohne Parameter Aufgerufen wird dann:  //Übersichtlicher, damit man es nicht später in den anderen Methoden schreiben muss
    public static List<Movie> getAllMovies() throws IOException {
        return getAllMovies(null, null, null, null);
    }

    //Movies herholen: request schicken -> response zurückbekommen

    public static List<Movie> getAllMovies(String query, Genre genre, String releaseYear, String ratingFrom) throws IOException {
        String url = buildURL(query, genre, releaseYear, ratingFrom);

        Request request = new Request.Builder()
                .url(url)
                .removeHeader("User-Agent")
                .addHeader("User-Agent", "http.agent")
                .build();

        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();

        String responseBody = response.body().string();
        Gson gson = new Gson();

        List<Movie> movies = Arrays.asList(gson.fromJson(responseBody, Movie[].class));
        return state.sort(movies);  // Sort the movies before returning them
    }



}
