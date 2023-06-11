package at.ac.fhcampuswien.fhmdb.application;
import at.ac.fhcampuswien.fhmdb.db.MovieAPI;



public class URLBuilder {
        private static final String URL_API = "https://prog2.fh-campuswien.ac.at/movies?";
        private static final String URL = "http://localhost:8080/movies";
        private final StringBuilder url;
    private static final String DELIMITER = "&"; //Separator

        public URLBuilder() {
            url = new StringBuilder(URL_API);
        }

        public URLBuilder withQuery(String query) {
            if (query != null && !query.isEmpty()) {
                url.append("query=").append(query).append(DELIMITER);
            }
            return this;
        }

        public URLBuilder withGenre(Genre genre) {
            if (genre != null) {
                url.append("genre=").append(genre).append(DELIMITER);
            }
            return this;
        }

        public URLBuilder withReleaseYear(String releaseYear) {
            if (releaseYear != null) {
                url.append("releaseYear=").append(releaseYear).append(DELIMITER);
            }
            return this;
        }

        public URLBuilder withRatingFrom(String ratingFrom) {
            if (ratingFrom != null) {
                url.append("ratingFrom=").append(ratingFrom).append(DELIMITER);
            }
            return this;
        }

        public String build() {
            return url.toString();
        }
    }
