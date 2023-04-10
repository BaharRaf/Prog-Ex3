package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.Genre;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import okhttp3.*;
import com.google.gson.*;


public class MovieAPI {
    private static final String URL = "http://localhost:8080/movies";
    private static final String DELIMITER = "&";



    private static String buildURL(String query, Genre genre, String releaseYear, String ratingFrom){

        StringBuilder url = new StringBuilder(URL);

        //Check if what was passed (If a parameter was passed, we need a "?"
        if((query != null && !query.isEmpty())|| genre != null || releaseYear != null || ratingFrom != null) { //empty ""
            url.append("?");
            if(query != null && !query.isEmpty()){
                url.append("query=").append(query).append(DELIMITER);
            }
            if(genre != null){
                url.append("genre=").append(genre).append(DELIMITER);
            }
            if(releaseYear != null){
                url.append("releaseYear=").append(releaseYear).append(DELIMITER);
            }
            if(ratingFrom != null){
                url.append("ratingFrom=").append(ratingFrom).append(DELIMITER);
            }
        }
        return url.toString();
    }

    //The call is then made without parameters: //More clear, so that you don't have to write it later in the other methods
    public static List<Movie> getAllMovies(){
        return getAllMovies(null, null, null, null);
    }

    //Get movies: send request -> get response back

    public static List<Movie> getAllMovies(String query, Genre genre, String releaseYear, String ratingFrom){
        String url = buildURL(query, genre, releaseYear, ratingFrom);

        //build requests:
        //Now have to send a GET request - with Okhttp :)
        Request request = new Request.Builder()
                .url(url)
                .removeHeader("User-Agent") //Hint -> User_agent_Header -> remove
                .addHeader("User-Agent", "http.agent")
                .build();

        // where a 404 error could come then you do something like that because there could be an error at runtime
        //Try-Catch where it's error-prone
        OkHttpClient client = new OkHttpClient();
        try (Response response = client.newCall(request).execute()){
            // get response in JSON format but should "parse" it on our classes -> make the movies out of it right away
            String responseBody = response.body().string(); //only body, where the important information for us drinkers is.
            //GSON responsible for automatically changing/parsing a JSON (that which is inside the response body) to a specific class.
            Gson gson = new Gson();
            //Create movies
            Movie[] movies = gson.fromJson(responseBody, Movie[].class); //takes response body and turns it into a movie array so we can use that in our app

            return Arrays.asList(movies);
        }
        catch (Exception e){
            System.err.println(); //so that the user knows that something went wrong.
        }
        return new ArrayList<>(); //pass an empty list so it doesn't crash.
    }

}