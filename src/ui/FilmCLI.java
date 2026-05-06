package ui;

import dao.Impl.FilmDAOImpl;
import java.util.List;
import java.util.Scanner;
import model.Film;

public class FilmCLI {

    //TODO: implement CLI for Film entity
    private static final FilmDAOImpl filmDAO = new FilmDAOImpl();

    private static final Scanner s = new Scanner(System.in);

    public void flow(){
        while(true){
            Menu();
            pause();
        }
    }

    private void Menu(){
        System.out.println("=== Film Management CLI ===");
        System.out.println("1. Add Film");
        System.out.println("2. Update Film");
        System.out.println("3. Delete Film");
        System.out.println("4. Search Films");
        System.out.println("5. Exit");
        System.out.print("Select an option: ");

        int choice = s.nextInt();
        s.nextLine(); // Consume newline

        switch(choice){
            case 1 -> addFilmHandler();
            case 2 -> updateFilmHandler();
            case 3 -> deleteFilmHandler();
            case 4 -> searchFilmsMenu();
            case 5 -> {
                System.out.println("Exiting...");
                System.exit(0);
            }
            default -> System.out.println("Invalid option. Please try again.");
            }
    }

    private static void addFilmHandler(){

        System.out.println("=== Add New Film ===");
        System.out.print("Title: ");
        String title = s.nextLine();
        System.out.print("Description: ");
        String description = s.nextLine();
        System.out.print("Release Year (ex: 2006): ");
        int releaseYear = s.nextInt();
        s.nextLine(); // Consume newline
        System.out.print("Language ID (1 for English, 2 for Italian, 3 for Japanese, 4 for Mandarin, 5 for French, 6 for German): ");
        int languageId = s.nextInt();
        s.nextLine(); // Consume newline
        System.out.print("Rental Duration (days): ");
        int rentalDuration = s.nextInt();
        s.nextLine(); // Consume newline
        System.out.print("Rental Rate (ex: 4.99): ");
        double rentalRate = s.nextDouble();
        s.nextLine(); // Consume newline
        System.out.print("Length (minutes): ");
        int length = s.nextInt();
        s.nextLine(); // Consume newline
        System.out.print("Replacement Cost (ex: 19.99): ");
        double replacementCost = s.nextDouble();
        s.nextLine(); // Consume newline
        System.out.print("Rating (G, PG, PG-13, R, NC-17): ");
        String rating = s.nextLine();
        System.out.print("Special Features (comma separated, ex: Trailers,Commentaries,Deleted Scenes,Behind the Scenes): ");
        String specialFeatures = s.nextLine();

        Film f = new Film(0, title, description, releaseYear, languageId, 0, rentalDuration, rentalRate, length, replacementCost, rating, specialFeatures, null);
        filmDAO.addFilm(f);
        System.out.println("Film added successfully!");

    }

    private void updateFilmHandler(){
        System.out.println("=== Add New Film ===");
        System.out.print("Film ID to update: ");
        int filmId = s.nextInt();
        s.nextLine(); // Consume newline
         System.out.print("Title: ");
        String title = s.nextLine();
        System.out.print("Description: ");
        String description = s.nextLine();
        System.out.print("Release Year (ex: 2006): ");
        int releaseYear = s.nextInt();
        s.nextLine(); // Consume newline
        System.out.print("Language ID (1 for English, 2 for Italian, 3 for Japanese, 4 for Mandarin, 5 for French, 6 for German): ");
        int languageId = s.nextInt();
        s.nextLine(); // Consume newline
        System.out.print("Rental Duration (days): ");
        int rentalDuration = s.nextInt();
        s.nextLine(); // Consume newline
        System.out.print("Rental Rate (ex: 4.99): ");
        double rentalRate = s.nextDouble();
        s.nextLine(); // Consume newline
        System.out.print("Length (minutes): ");
        int length = s.nextInt();
        s.nextLine(); // Consume newline
        System.out.print("Replacement Cost (ex: 19.99): ");
        double replacementCost = s.nextDouble();
        s.nextLine(); // Consume newline
        System.out.print("Rating (G, PG, PG-13, R, NC-17): ");
        String rating = s.nextLine();
        System.out.print("Special Features (comma separated, ex: Trailers,Commentaries,Deleted Scenes,Behind the Scenes): ");
        String specialFeatures = s.nextLine();
        Film f = new Film(filmId, title, description, releaseYear, languageId, 0, rentalDuration, rentalRate, length, replacementCost, rating, specialFeatures, null);
        filmDAO.updateFilm(f);
        System.out.println("Film updated successfully!");
    }

    private void deleteFilmHandler(){
        System.out.println("=== Delete Film ===");
        System.out.print("Film ID to delete: ");
        int filmId = s.nextInt();
        s.nextLine(); // Consume newline
        if(filmDAO.deleteFilm(filmId)){
            System.out.println("Film deleted successfully!");
        } else {
            System.out.println("Failed to delete film. Please check the ID and try again.");
        }
    }

    private void searchFilmsMenu(){
        System.out.println("=== Search Films ===");
        System.out.println("1. By Title");
        System.out.println("2. By Description");
        System.out.println("3. By Release Year");
        System.out.println("4. By Language");
        System.out.println("5. By Rating");
        System.out.println("6. By Rental Rate");
        System.out.println("7. By Length");
        System.out.println("8. By Special Features");
        System.out.println("9. By ID");
        System.out.println("10. Show All Films");
        System.out.print("Select an option: ");
        int option = s.nextInt();
        s.nextLine(); // Consume newline

        System.out.print("Enter result limit: ");
        int limit = s.nextInt();
        s.nextLine(); // Consume newline

        switch(option){
            case 1 -> {
                System.out.print("Enter title keyword: ");
                String title = s.nextLine();
                List<Film> filmsByTitle = filmDAO.findByTitle(title, limit);
                displayFilmList(filmsByTitle);
            }
            
            case 2 -> {
                System.out.print("Enter description keyword: ");
                String description = s.nextLine();
                List<Film> filmsByDescription = filmDAO.findByDescription(description, limit);
                displayFilmList(filmsByDescription);
            }
            case 3 -> {
                System.out.print("Enter release year (ex: 2006): ");
                int releaseYear = s.nextInt();
                s.nextLine(); // Consume newline
                List<Film> filmsByReleaseYear = filmDAO.findByReleaseYear(releaseYear, limit);
                displayFilmList(filmsByReleaseYear);
            }
            case 4 -> {
                System.out.print("Enter language (English, Italian, Japanese, Mandarin, French, German): ");
                String language = s.nextLine();
                List<Film> filmsByLanguage = filmDAO.findByLanguage(language, limit);
                displayFilmList(filmsByLanguage);
            }  

            case 5 -> {
                System.out.print("Enter rating (G, PG, PG-13, R, NC-17): ");
                String rating = s.nextLine();
                List<Film> filmsByRating = filmDAO.findByRating(rating, limit);
                displayFilmList(filmsByRating);
            }
            case 6 -> {
                System.out.print("Enter rental rate (ex: 4.99): ");
                double rentalRate = s.nextDouble();
                s.nextLine(); // Consume newline
                System.out.print("Enter operator (>, <, =): ");
                String operator = s.nextLine();
                List<Film> filmsByRentalRate = filmDAO.findByRental(rentalRate, limit, operator);
                displayFilmList(filmsByRentalRate);
            }
            case 7 -> {
                System.out.print("Enter length in minutes: ");
                int length = s.nextInt();
                s.nextLine(); // Consume newline
                System.out.print("Enter operator (>, <, =): ");
                String lengthOperator = s.nextLine();
                List<Film> filmsByLength = filmDAO.findByLength(length, limit, lengthOperator);
                displayFilmList(filmsByLength);
            }
            case 8 -> {
                System.out.print("Enter special feature keyword or mix of keywords (Trailers, Commentaries, Deleted Scenes, Behind the Scenes): ");
                String specialFeature = s.nextLine();
                List<Film> filmsBySpecialFeature = filmDAO.findBySpecialFeatures(specialFeature, limit);
                displayFilmList(filmsBySpecialFeature);
            }
            case 9 -> {
                System.out.print("Enter film ID: ");
                int filmId = s.nextInt();
                s.nextLine(); // Consume newline
                Film filmById = filmDAO.findByID(filmId);
                if (filmById != null) {
                    printFilm(filmById);
                } else {
                    System.out.println("No film found with ID: " + filmId);
                }
            }
            case 10 -> {
                List<Film> allFilms = filmDAO.findAll(limit);
                displayFilmList(allFilms);
            }
        }
    }

    private void printFilm (Film f){
        //one line of all data
        System.out.printf("ID: %d | Title: %s | Description: %s | Release Year: %d | Language ID: %d | Rental Duration: %d | Rental Rate: %.2f | Length: %d | Replacement Cost: %.2f | Rating: %s | Special Features: %s | Last Update: %s\n",
                f.getId(), f.getTitle(), f.getDescription(), f.getReleaseYear(), f.getLanguageId(), f.getRentalDuration(), f.getRentalRate(), f.getLength(), f.getReplacementCost(), f.getRating(), f.getSpecialFeatures(), f.getLastUpdate());

    }

    private void displayFilmList(List<Film> films){
        if(films.isEmpty()){
            System.out.println("No films found.");
        } else {
            for(Film f : films){
                printFilm(f);
            }
        }
    }

    private void pause() {
        System.out.println("\nPress ENTER to return to the menu...");
        s.nextLine();
    }
}
