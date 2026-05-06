package dao;

import java.util.List;
import model.Film;

public interface FilmDAO {

    //crud operations
    void addFilm(Film film);
    void updateFilm(Film film);
    boolean deleteFilm(int filmId);

    //search by various attributes
    List<Film> findByTitle(String title, int limit);
    List<Film> findByDescription (String description, int limit);
    List<Film> findByReleaseYear(int releaseYear, int limit);
    List<Film> findByLanguage (String language, int limit);
    List<Film> findByRating (String rating, int limit);
    List<Film> findByRental(double rentalRate, int limit, String operator);
    List<Film> findByLength (int length, int limit, String operator);
    List<Film> findBySpecialFeatures (String specialFeatures, int limit);
    List<Film> findAll(int limit);

    //additional methods with conditionals - soon to be added
    List<Film> findRentalGreaterThan(double rentalRate, int limit);
    List<Film> findRentalLessThan(double rentalRate, int limit);
    List<Film> findLengthGreaterThan(int length, int limit);
    List<Film> findLengthLessThan(int length, int limit);

    //added
    Film findByID(int filmId);
}
