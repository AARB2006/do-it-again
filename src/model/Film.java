package model;

public class Film {

    private int filmId;
    private String title;
    private String description;
    private int releaseYear;
    private int languageId;
    private int originalLanguageId;
    private int rentalDuration;
    private double rentalRate;
    private int length;
    private double replacementCost;
    private String rating;
    private String specialFeatures;
    private java.sql.Timestamp lastUpdate;

    //to join with Language
    private Language language;
    

    public Film(){}
    public Film(int filmId, String title, String description, int releaseYear, int languageId, int originalLanguageId, int rentalDuration, double rentalRate, int length, double replacementCost, String rating, String specialFeatures, java.sql.Timestamp lastUpdate) {
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.languageId = languageId;
        this.originalLanguageId = originalLanguageId;
        this.rentalDuration = rentalDuration;
        this.rentalRate = rentalRate;
        this.length = length;
        this.replacementCost = replacementCost;
        this.rating = rating;
        this.specialFeatures = specialFeatures;
        this.lastUpdate = lastUpdate;
    }

    //getters
    public int getId(){
        return filmId;
    }
    public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;
    }
    public int getReleaseYear(){
        return releaseYear;
    }
    public int getLanguageId(){
        return languageId;
    }
    public int getOriginalLanguageId(){
        return originalLanguageId;
    }
    public int getRentalDuration(){
        return rentalDuration;
    }
    public double getRentalRate(){
        return rentalRate;
    }
    public int getLength(){
        return length;
    }
    public double getReplacementCost(){
        return replacementCost;
    }
    public String getRating(){
        return rating;
    }
    public String getSpecialFeatures(){
        return specialFeatures;
    }
    public java.sql.Timestamp getLastUpdate(){
        return lastUpdate;
    }

    //setters
    public void setId(int filmId){
        this.filmId = filmId;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setReleaseYear(int releaseYear){
        this.releaseYear = releaseYear;
    }
    public void setLanguageId(int languageId){
        this.languageId = languageId;
    }
    public void setOriginalLanguageId(int originalLanguageId){
        this.originalLanguageId = originalLanguageId;
    }
    public void setRentalDuration(int rentalDuration){
        this.rentalDuration = rentalDuration;
    }
    public void setRentalRate(double rentalRate){
        this.rentalRate = rentalRate;
    }
    public void setLength(int length){
        this.length = length;
    }
    public void setReplacementCost(double replacementCost){
        this.replacementCost = replacementCost;
    }
    public void setRating(String rating){
        this.rating = rating;
    }
    public void setSpecialFeatures(String specialFeatures){
        this.specialFeatures = specialFeatures;
    }
    public void setLastUpdate(java.sql.Timestamp lastUpdate){
        this.lastUpdate = lastUpdate;
    }

    //setter and getter for Language
    public Language getLanguage(){
        return language;
    }

    public void setLanguage(Language language){
        this.language = language;
    }

}
