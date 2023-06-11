package at.ac.fhcampuswien.fhmdb.application;

public interface Observable {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(String message);

    void addToWatchList(String movieName);
}
