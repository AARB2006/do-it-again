import util.AppConfig;

public class App {
    public static void main(String[] args) throws Exception {
        AppConfig.init();
        // Start the CLI
        ui.FilmCLI filmCLI = new ui.FilmCLI();
        filmCLI.flow();
    }
}

//javac -cp ".;mysql-connector-j-9.6.0.jar" App.java

//java -cp ".;mysql-connector-j-9.6.0.jar" App