package at.ac.fhcampuswien.fhmdb.application;
import at.ac.fhcampuswien.fhmdb.application.controller.HomeController;
import at.ac.fhcampuswien.fhmdb.application.controller.WatchlistViewController;
import javafx.util.Callback;

public class Factory implements Callback<Class<?>, Object> {
    private static Factory instance = null;
    private HomeController homeController = null;
    private WatchlistViewController watchlistController = null;

    private Factory() {
        // Private constructor to enforce singleton pattern
    }

    public static Factory getInstance() {
        if (instance == null) {
            instance = new Factory();
        }
        return instance;
    }

    @Override
    public Object call(Class<?> aClass) {
        try {
            if (aClass == WatchlistViewController.class) {
                if (watchlistController == null) {
                    watchlistController = new WatchlistViewController();
                }
                return watchlistController;
            } else if (aClass == HomeController.class) {
                if (homeController == null) {
                    homeController = new HomeController();
                }
                return homeController;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}