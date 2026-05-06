package dao.Impl;

import dao.FilmDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Film;
import model.Language;
import util.DBUtil;


public class FilmDAOImpl implements FilmDAO{

    public FilmDAOImpl(){}

    @Override
    public void addFilm(Film film) {

        //add checks
        if (film.getRentalRate() < 0 || film.getRentalRate() > 4.99){
            System.out.println("Invalid rental rate. Must be between 0 and 4.99.");
            return;
        }

        if (film.getLength() <= 0){
            System.out.println("Invalid length. Must be greater than 0.");
            return;
        }

        String sql = "INSERT INTO film (title, description, release_year, language_id, original_langugage_id, rental_duration, rental_rate, length, replacement_cost, rating, special_features) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        
        try (Connection conn = DBUtil.getConnection()){

            PreparedStatement ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);

            try{
                
                ps.setString(1, film.getTitle());
                ps.setString(2, film.getDescription());
                ps.setInt(3, film.getReleaseYear());
                ps.setInt(4, film.getLanguageId());
                ps.setInt(5, film.getOriginalLanguageId());
                ps.setInt(6, film.getRentalDuration());
                ps.setDouble(7, film.getRentalRate());
                ps.setInt(8, film.getLength());
                ps.setDouble(9, film.getReplacementCost());
                ps.setString(10, film.getRating());
                ps.setString(11, film.getSpecialFeatures());

                ps.executeUpdate();
                conn.commit();
            
            }catch (SQLException e){
                conn.rollback();
                e.printStackTrace();
            }
           
        } catch (Exception e) {
            e.printStackTrace();
        } 
        
    }

    @Override
    public void updateFilm(Film film) {

                //add checks
        if (film.getRentalRate() < 0 || film.getRentalRate() > 4.99){
            System.out.println("Invalid rental rate. Must be between 0 and 4.99.");
            return;
        }

        if (film.getLength() <= 0){
            System.out.println("Invalid length. Must be greater than 0.");
            return;
        }

        String sql = "UPDATE film SET title=?, description=?, release_year=?, language_id=?, original_language_id=?, rental_duration=?, rental_rate=?, length=?, replacement_cost=?, rating=?, special_features=? WHERE film_id=?";

        try (Connection conn = DBUtil.getConnection()){
            
                PreparedStatement ps = conn.prepareStatement(sql);
                
                try {
                    ps.setString(1, film.getTitle());
                    ps.setString(2, film.getDescription());
                    ps.setInt(3, film.getReleaseYear());
                    ps.setInt(4, film.getLanguageId());
                    ps.setInt(5, film.getOriginalLanguageId());
                    ps.setInt(6, film.getRentalDuration());
                    ps.setDouble(7, film.getRentalRate());
                    ps.setInt(8, film.getLength());
                    ps.setDouble(9, film.getReplacementCost());
                    ps.setString(10, film.getRating());
                    ps.setString(11, film.getSpecialFeatures());

                    ps.executeUpdate();
                    conn.commit();
                } 
                
                catch (SQLException e) {

                    conn.rollback();
                    e.printStackTrace();

                }

        }catch(Exception e){
            e.printStackTrace();
        }
       
    }

    @Override
    public boolean deleteFilm(int filmId) {

        String sql = "DELETE FROM film WHERE film_id = ?";
        
        try (Connection conn =  DBUtil.getConnection()){

            PreparedStatement ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);

            try{
                ps.setInt(1, filmId);
                ps.executeUpdate();
                conn.commit();
                return true;

            }catch(SQLException e){
                conn.rollback();
                e.printStackTrace();
                return false;
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return false;

        }
    }

    @Override
    public List<Film> findByTitle(String title, int limit) {

        String sql = "SELECT f.*, l.name AS language_name from film f JOIN language l on f.language_id = l.language_id WHERE f.title LIKE ? LIMIT ?";
        ArrayList<Film> films = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection()){

            PreparedStatement ps = conn.prepareStatement(sql);
            

            try {
                ps.setString(1, "%" + title + "%");
                ps.setInt(2, limit);

                ResultSet rs = ps.executeQuery();

                while(rs.next()){

                    Film film = new Film();
                    film.setId(rs.getInt("film_id"));
                    film.setTitle(rs.getString("title"));
                    film.setDescription(rs.getString("description"));
                    film.setReleaseYear(rs.getInt("release_year"));
                    film.setLanguageId(rs.getInt("language_id"));
                    film.setLanguage(new Language(rs.getInt("language_id"), rs.getString("language_name")));
                    film.setOriginalLanguageId(rs.getInt("original_language_id"));
                    film.setRentalDuration(rs.getInt("rental_duration"));
                    film.setRentalRate(rs.getDouble("rental_rate"));
                    film.setLength(rs.getInt("length"));
                    film.setReplacementCost(rs.getDouble("replacement_cost"));
                    film.setRating(rs.getString("rating"));
                    film.setSpecialFeatures(rs.getString("special_features"));
                    film.setLastUpdate(rs.getTimestamp("last_update"));
                    
                    films.add(film);
                }
            } 
            
            catch (Exception e) {
                System.out.println("No matches found for title: " + title);
                e.printStackTrace();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        
        return films;
    }

    @Override
    public List<Film> findByDescription(String description, int limit) {
        String sql = "SELECT f.*, l.name AS language_name from film f JOIN language l on f.language_id = l.language_id WHERE f.description LIKE ? LIMIT ?";
        ArrayList<Film> films = new ArrayList<>();

        try(Connection conn = DBUtil.getConnection()){

            PreparedStatement ps = conn.prepareStatement(sql);

            try {
                ps.setString(1, "%" + description + "%");
                ps.setInt(2, limit);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    Film film = new Film();
                    film.setId(rs.getInt("film_id"));
                    film.setTitle(rs.getString("title"));
                    film.setDescription(rs.getString("description"));
                    film.setReleaseYear(rs.getInt("release_year"));
                    film.setLanguageId(rs.getInt("language_id"));
                    film.setLanguage(new Language(rs.getInt("language_id"), rs.getString("language_name")));
                    film.setOriginalLanguageId(rs.getInt("original_language_id"));
                    film.setRentalDuration(rs.getInt("rental_duration"));
                    film.setRentalRate(rs.getDouble("rental_rate"));
                    film.setLength(rs.getInt("length"));
                    film.setReplacementCost(rs.getDouble("replacement_cost"));
                    film.setRating(rs.getString("rating"));
                    film.setSpecialFeatures(rs.getString("special_features"));
                    film.setLastUpdate(rs.getTimestamp("last_update"));

                    films.add(film);
                }

            } catch (Exception e) {

                System.out.println("No matches found for description: " + description);
                e.printStackTrace();
            }

            
        }

        catch(SQLException e){
            e.printStackTrace();
        }

        return films;
    }

    @Override
    public List<Film> findByReleaseYear(int releaseYear, int limit) {

        String sql = "SELECT f.*, l.name AS language_name from film f JOIN language l on f.language_id = l.language_id WHERE f.release_year = ? LIMIT ?";
        ArrayList<Film> films = new ArrayList<>();
        try(Connection conn = DBUtil.getConnection()){

            PreparedStatement ps = conn.prepareStatement(sql);

            try {
                ps.setInt(1, releaseYear);
                ps.setInt(2, limit);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    Film film = new Film();
                    film.setId(rs.getInt("film_id"));
                    film.setTitle(rs.getString("title"));
                    film.setDescription(rs.getString("description"));
                    film.setReleaseYear(rs.getInt("release_year"));
                    film.setLanguageId(rs.getInt("language_id"));
                    film.setLanguage(new Language(rs.getInt("language_id"), rs.getString("language_name")));
                    film.setOriginalLanguageId(rs.getInt("original_language_id"));
                    film.setRentalDuration(rs.getInt("rental_duration"));
                    film.setRentalRate(rs.getDouble("rental_rate"));
                    film.setLength(rs.getInt("length"));
                    film.setReplacementCost(rs.getDouble("replacement_cost"));
                    film.setRating(rs.getString("rating"));
                    film.setSpecialFeatures(rs.getString("special_features"));
                    film.setLastUpdate(rs.getTimestamp("last_update"));

                    films.add(film);
                }

            } catch (Exception e) {

                System.out.println("No matches found for release year: " + releaseYear);
                e.printStackTrace();
            }

            
        }

        catch(SQLException e){
            e.printStackTrace();
        }
        return films;
    }

    @Override
    public List<Film> findByLanguage(String language, int limit) {
            String sql = "SELECT f.*, l.name AS language_name from film f JOIN language l on f.language_id = l.language_id WHERE l.name = ? LIMIT ?";
        ArrayList<Film> films = new ArrayList<>();
        try(Connection conn = DBUtil.getConnection()){

            PreparedStatement ps = conn.prepareStatement(sql);

            try {
                ps.setString(1, language);
                ps.setInt(2, limit);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    Film film = new Film();
                    film.setId(rs.getInt("film_id"));
                    film.setTitle(rs.getString("title"));
                    film.setDescription(rs.getString("description"));
                    film.setReleaseYear(rs.getInt("release_year"));
                    film.setLanguageId(rs.getInt("language_id"));
                    film.setLanguage(new Language(rs.getInt("language_id"), rs.getString("language_name")));
                    film.setOriginalLanguageId(rs.getInt("original_language_id"));
                    film.setRentalDuration(rs.getInt("rental_duration"));
                    film.setRentalRate(rs.getDouble("rental_rate"));
                    film.setLength(rs.getInt("length"));
                    film.setReplacementCost(rs.getDouble("replacement_cost"));
                    film.setRating(rs.getString("rating"));
                    film.setSpecialFeatures(rs.getString("special_features"));
                    film.setLastUpdate(rs.getTimestamp("last_update"));

                    films.add(film);
                }

            } catch (Exception e) {

                System.out.println("No matches found for language: " + language);
                e.printStackTrace();
            }

            
        }

        catch(SQLException e){
            e.printStackTrace();
        }
        return films;
    }

    @Override
    public List<Film> findByRating(String rating, int limit) {

        String sql = "SELECT f.*, l.name AS language_name from film f JOIN language l on f.language_id = l.language_id WHERE f.rating = ? LIMIT ?";
        ArrayList<Film> films = new ArrayList<>();
        try(Connection conn = DBUtil.getConnection()){

            PreparedStatement ps = conn.prepareStatement(sql);

            try {
                ps.setString(1, rating);
                ps.setInt(2, limit);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    Film film = new Film();
                    film.setId(rs.getInt("film_id"));
                    film.setTitle(rs.getString("title"));
                    film.setDescription(rs.getString("description"));
                    film.setReleaseYear(rs.getInt("release_year"));
                    film.setLanguageId(rs.getInt("language_id"));
                    film.setLanguage(new Language(rs.getInt("language_id"), rs.getString("language_name")));
                    film.setOriginalLanguageId(rs.getInt("original_language_id"));
                    film.setRentalDuration(rs.getInt("rental_duration"));
                    film.setRentalRate(rs.getDouble("rental_rate"));
                    film.setLength(rs.getInt("length"));
                    film.setReplacementCost(rs.getDouble("replacement_cost"));
                    film.setRating(rs.getString("rating"));
                    film.setSpecialFeatures(rs.getString("special_features"));
                    film.setLastUpdate(rs.getTimestamp("last_update"));

                    films.add(film);
                }

            } catch (Exception e) {

                System.out.println("No matches found for rating: " + rating);
                e.printStackTrace();
            }

            
        }

        catch(SQLException e){
            e.printStackTrace();
        }
        return films;
        
    }

    @Override
    public List<Film> findByRental(double rentalRate, int limit, String operator) {

        List<Film> films = new ArrayList<>();
        switch (operator){
            case "<":
                films = findRentalLessThan(rentalRate, limit);
                break;
            case ">":
                films = findRentalGreaterThan(rentalRate, limit);
                break;
            default:
                System.out.println("Invalid operator. Use '<' or '>'.");
        }
        return films;
    }

    @Override
    public List<Film> findByLength(int length, int limit, String operator) {
        
        List<Film> films = new ArrayList<>();
        switch (operator){
            case "<":
                films = findLengthLessThan(length, limit);
                break;
            case ">":
                films = findLengthGreaterThan(length, limit);
                break;
            default:
                System.out.println("Invalid operator. Use '<' or '>'.");
        }
        return films;
    }

    @Override
    public List<Film> findBySpecialFeatures(String specialFeatures, int limit) {

        String sql = "SELECT f.*, l.name AS language_name from film f JOIN language l on f.language_id = l.language_id WHERE f.special_features LIKE ? LIMIT ?";
        ArrayList<Film> films = new ArrayList<>();
        try(Connection conn = DBUtil.getConnection()){

            PreparedStatement ps = conn.prepareStatement(sql);

            try {

                ps.setString(1, "%" + specialFeatures + "%");
                ps.setInt(2, limit);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    Film film = new Film();
                    film.setId(rs.getInt("film_id"));
                    film.setTitle(rs.getString("title"));
                    film.setDescription(rs.getString("description"));
                    film.setReleaseYear(rs.getInt("release_year"));
                    film.setLanguageId(rs.getInt("language_id"));
                    film.setLanguage(new Language(rs.getInt("language_id"), rs.getString("language_name")));
                    film.setOriginalLanguageId(rs.getInt("original_language_id"));
                    film.setRentalDuration(rs.getInt("rental_duration"));
                    film.setRentalRate(rs.getDouble("rental_rate"));
                    film.setLength(rs.getInt("length"));
                    film.setReplacementCost(rs.getDouble("replacement_cost"));
                    film.setRating(rs.getString("rating"));
                    film.setSpecialFeatures(rs.getString("special_features"));
                    film.setLastUpdate(rs.getTimestamp("last_update"));

                    films.add(film);
                }

            } catch (Exception e) {

                System.out.println("None Found with the Special Feature: " + specialFeatures);
                e.printStackTrace();
            }

            
        }

        catch(SQLException e){
            e.printStackTrace();
        }
        return films;
    }

    @Override
    public List<Film> findAll(int limit) {

        String sql = "SELECT f.*, l.name AS language_name from film f JOIN language l on f.language_id = l.language_id LIMIT ?";
        ArrayList<Film> films = new ArrayList<>();
        try(Connection conn = DBUtil.getConnection()){

            PreparedStatement ps = conn.prepareStatement(sql);

            try {

                ps.setInt(1, limit);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    Film film = new Film();
                    film.setId(rs.getInt("film_id"));
                    film.setTitle(rs.getString("title"));
                    film.setDescription(rs.getString("description"));
                    film.setReleaseYear(rs.getInt("release_year"));
                    film.setLanguageId(rs.getInt("language_id"));
                    film.setLanguage(new Language(rs.getInt("language_id"), rs.getString("language_name")));
                    film.setOriginalLanguageId(rs.getInt("original_language_id"));
                    film.setRentalDuration(rs.getInt("rental_duration"));
                    film.setRentalRate(rs.getDouble("rental_rate"));
                    film.setLength(rs.getInt("length"));
                    film.setReplacementCost(rs.getDouble("replacement_cost"));
                    film.setRating(rs.getString("rating"));
                    film.setSpecialFeatures(rs.getString("special_features"));
                    film.setLastUpdate(rs.getTimestamp("last_update"));

                    films.add(film);
                }

            } catch (Exception e) {

                System.out.println("None Found");
                e.printStackTrace();
            }

            
        }

        catch(SQLException e){
            e.printStackTrace();
        }
        return films;
    }

    @Override
    public List<Film> findRentalGreaterThan(double rentalRate, int limit) {
        String sql = "SELECT f.*, l.name AS language_name from film f JOIN language l on f.language_id = l.language_id WHERE f.rental_rate > ? LIMIT ?";
        ArrayList<Film> films = new ArrayList<>();

        try(Connection conn = DBUtil.getConnection()){

            PreparedStatement ps = conn.prepareStatement(sql);

            try {
                ps.setDouble(1, rentalRate);
                ps.setInt(2, limit);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    Film film = new Film();
                    film.setId(rs.getInt("film_id"));
                    film.setTitle(rs.getString("title"));
                    film.setDescription(rs.getString("description"));
                    film.setReleaseYear(rs.getInt("release_year"));
                    film.setLanguageId(rs.getInt("language_id"));
                    film.setLanguage(new Language(rs.getInt("language_id"), rs.getString("language_name")));
                    film.setOriginalLanguageId(rs.getInt("original_language_id"));
                    film.setRentalDuration(rs.getInt("rental_duration"));
                    film.setRentalRate(rs.getDouble("rental_rate"));
                    film.setLength(rs.getInt("length"));
                    film.setReplacementCost(rs.getDouble("replacement_cost"));
                    film.setRating(rs.getString("rating"));
                    film.setSpecialFeatures(rs.getString("special_features"));
                    film.setLastUpdate(rs.getTimestamp("last_update"));

                    films.add(film);
                }

            } catch (Exception e) {

                System.out.println("No matches found for rental rate greater than: " + rentalRate);
                e.printStackTrace();
            }

            
        }

        catch(SQLException e){
            e.printStackTrace();
        }
        return films;
    }

    @Override
    public List<Film> findRentalLessThan(double rentalRate, int limit) {

        String sql = "SELECT f.*, l.name AS language_name from film f JOIN language l on f.language_id = l.language_id WHERE f.rental_rate < ? LIMIT ?";
        ArrayList<Film> films = new ArrayList<>();

        try(Connection conn = DBUtil.getConnection()){

            PreparedStatement ps = conn.prepareStatement(sql);

            try {
                ps.setDouble(1, rentalRate);
                ps.setInt(2, limit);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    Film film = new Film();
                    film.setId(rs.getInt("film_id"));
                    film.setTitle(rs.getString("title"));
                    film.setDescription(rs.getString("description"));
                    film.setReleaseYear(rs.getInt("release_year"));
                    film.setLanguageId(rs.getInt("language_id"));
                    film.setLanguage(new Language(rs.getInt("language_id"), rs.getString("language_name")));
                    film.setOriginalLanguageId(rs.getInt("original_language_id"));
                    film.setRentalDuration(rs.getInt("rental_duration"));
                    film.setRentalRate(rs.getDouble("rental_rate"));
                    film.setLength(rs.getInt("length"));
                    film.setReplacementCost(rs.getDouble("replacement_cost"));
                    film.setRating(rs.getString("rating"));
                    film.setSpecialFeatures(rs.getString("special_features"));
                    film.setLastUpdate(rs.getTimestamp("last_update"));

                    films.add(film);
                }

            } catch (Exception e) {

                System.out.println("No matches found for rental rate greater than: " + rentalRate);
                e.printStackTrace();
            }

            
        }

        catch(SQLException e){
            e.printStackTrace();
        }
        return films;
    }

    @Override
    public List<Film> findLengthGreaterThan(int length, int limit) {

        String sql = "SELECT f.*, l.name AS language_name from film f JOIN language l on f.language_id = l.language_id WHERE f.length > ? LIMIT ?";
        ArrayList<Film> films = new ArrayList<>();

        try(Connection conn = DBUtil.getConnection()){

            PreparedStatement ps = conn.prepareStatement(sql);

            try {
                ps.setInt(1, length);
                ps.setInt(2, limit);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    Film film = new Film();
                    film.setId(rs.getInt("film_id"));
                    film.setTitle(rs.getString("title"));
                    film.setDescription(rs.getString("description"));
                    film.setReleaseYear(rs.getInt("release_year"));
                    film.setLanguageId(rs.getInt("language_id"));
                    film.setLanguage(new Language(rs.getInt("language_id"), rs.getString("language_name")));
                    film.setOriginalLanguageId(rs.getInt("original_language_id"));
                    film.setRentalDuration(rs.getInt("rental_duration"));
                    film.setRentalRate(rs.getDouble("rental_rate"));
                    film.setLength(rs.getInt("length"));
                    film.setReplacementCost(rs.getDouble("replacement_cost"));
                    film.setRating(rs.getString("rating"));
                    film.setSpecialFeatures(rs.getString("special_features"));
                    film.setLastUpdate(rs.getTimestamp("last_update"));

                    films.add(film);
                }

            } catch (Exception e) {

                System.out.println("No matches found for length greater than: " + length);
                e.printStackTrace();
            }

            
        }

        catch(SQLException e){
            e.printStackTrace();
        }
        return films;
    }

    @Override
    public List<Film> findLengthLessThan(int length, int limit) {
       String sql = "SELECT f.*, l.name AS language_name from film f JOIN language l on f.language_id = l.language_id WHERE f.length < ? LIMIT ?";
        ArrayList<Film> films = new ArrayList<>();

        try(Connection conn = DBUtil.getConnection()){

            PreparedStatement ps = conn.prepareStatement(sql);

            try {
                ps.setInt(1, length);
                ps.setInt(2, limit);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    Film film = new Film();
                    film.setId(rs.getInt("film_id"));
                    film.setTitle(rs.getString("title"));
                    film.setDescription(rs.getString("description"));
                    film.setReleaseYear(rs.getInt("release_year"));
                    film.setLanguageId(rs.getInt("language_id"));
                    film.setLanguage(new Language(rs.getInt("language_id"), rs.getString("language_name")));
                    film.setOriginalLanguageId(rs.getInt("original_language_id"));
                    film.setRentalDuration(rs.getInt("rental_duration"));
                    film.setRentalRate(rs.getDouble("rental_rate"));
                    film.setLength(rs.getInt("length"));
                    film.setReplacementCost(rs.getDouble("replacement_cost"));
                    film.setRating(rs.getString("rating"));
                    film.setSpecialFeatures(rs.getString("special_features"));
                    film.setLastUpdate(rs.getTimestamp("last_update"));

                    films.add(film);
                }

            } catch (Exception e) {

                System.out.println("No matches found for length less than: " + length);
                e.printStackTrace();
            }

            
        }

        catch(SQLException e){
            e.printStackTrace();
        }
        return films;
    }

    @Override
    public Film findByID (int filmId){
        String sql = "SELECT f.*, l.name AS language_name from film f JOIN language l on f.language_id = l.language_id WHERE f.film_id = ?";
        try(Connection conn = DBUtil.getConnection()){

            PreparedStatement ps = conn.prepareStatement(sql);

            try {
                ps.setInt(1, filmId);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    Film film = new Film();
                    film.setId(rs.getInt("film_id"));
                    film.setTitle(rs.getString("title"));
                    film.setDescription(rs.getString("description"));
                    film.setReleaseYear(rs.getInt("release_year"));
                    film.setLanguageId(rs.getInt("language_id"));
                    film.setLanguage(new Language(rs.getInt("language_id"), rs.getString("language_name")));
                    film.setOriginalLanguageId(rs.getInt("original_language_id"));
                    film.setRentalDuration(rs.getInt("rental_duration"));
                    film.setRentalRate(rs.getDouble("rental_rate"));
                    film.setLength(rs.getInt("length"));
                    film.setReplacementCost(rs.getDouble("replacement_cost"));
                    film.setRating(rs.getString("rating"));
                    film.setSpecialFeatures(rs.getString("special_features"));
                    film.setLastUpdate(rs.getTimestamp("last_update"));

                    return film;
                }
                else{
                    System.out.println("No film found with ID: " + filmId);
                    return null;
                }
            } catch (Exception e) {
                System.out.println("Error finding film with ID: " + filmId);
                e.printStackTrace();
                return null;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            return null;
        }
        
    }

}
