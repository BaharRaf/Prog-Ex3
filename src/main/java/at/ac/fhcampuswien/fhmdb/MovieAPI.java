package at.ac.fhcampuswien.fhmdb;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

    public class MovieAPI {
        private final String baseUrl = "https://prog2.fh-campuswien.ac.at/movies";
        private final String userAgent = "http.agent";

        public String sendRequest(String endpoint, String queryParams) throws IOException {
            URL url = new URL(baseUrl + endpoint + queryParams);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", userAgent);
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                Scanner scanner = new Scanner(conn.getInputStream());
                String response = scanner.useDelimiter("\\A").next();
                scanner.close();
                return response;
            } else {
                throw new RuntimeException("Failed to fetch data from the API. Response code: " + responseCode);
            }
        }

        public String getAllMovies() throws IOException {
            String endpoint = "";
            String queryParams = "";
            return sendRequest(endpoint, queryParams);
        }

        public String getMoviesByQueryAndGenre(String query, String genre) throws IOException {
            String endpoint = "";
            String queryParams = "?query=" + query + "&genre=" + genre;
            return sendRequest(endpoint, queryParams);
        }
    }

