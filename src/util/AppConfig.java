package util;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.Scanner;

public class AppConfig {

    //for the env file
    private static final String ENV_FILE = "../.env";
    private static final Properties props = new Properties();

    public static String dbUrl;
    public static String dbUser;
    public static String dbPass;

    //initialize from the env file
    public static void init(){
        if(loadFromEnv()){
            System.out.println("Existing credentials found. Testing connection...");
            if (testConn(dbUrl, dbUser, dbPass)){
                System.out.println("Connection successful!");
                return;
            } else {
                System.out.println("Connection failed. Please re-enter your credentials.");
            }
        }

        runSetupWizard();
    }

    //load credentials from env file
    private static boolean loadFromEnv(){

        File file = new File(ENV_FILE);
        if (!file.exists()){
            return false;
        }
        try (FileInputStream fih =  new FileInputStream(file)){
            props.load(fih);
            dbUrl = props.getProperty("db.url");
            dbUser = props.getProperty("db.user");
            dbPass = props.getProperty("db.pass");
            return dbUrl != null && dbUser != null && dbPass != null;
        }
        catch (IOException e){
            return false;
        }
    }

    //save credentials to env file
    private static void runSetupWizard(){
        Scanner s = new Scanner(System.in);

        while(true){
            System.out.print("Enter DB URL: ");
            String url = s.nextLine();
            System.out.print("Enter username: ");
            String user = s.nextLine();
            System.out.print("Enter password: ");
            String pass = s.nextLine();

            if(testConn(url, user, pass)){
                saveToEnv(url, user, pass);
                dbUrl = url;
                dbUser = user;
                dbPass = pass;
                System.out.println("Credentials saved.");
                break;
            }
            else{
                System.out.println("Connection failed.");
            }
        }
    }

    //test connection
    private static boolean testConn(String url, String user, String pass){
        try (Connection conn = DriverManager.getConnection(url, user, pass)){
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    //save credentials to env file
    private static void saveToEnv(String url, String user, String pass){
        props.setProperty("db.url", url);
        props.setProperty("db.user", user);
        props.setProperty("db.pass", pass);

        try (FileOutputStream foh = new FileOutputStream(ENV_FILE)){
            props.store(foh, "Database credentials");
        }
        catch (IOException e){
            System.out.println("Failed to save credentials: " + e.getMessage());
        }
    }
}
