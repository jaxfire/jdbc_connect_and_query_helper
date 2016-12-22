package uk.co.jaxfire;

public class Main {
    public static void main(String[] args){

        String ip = "localhost";
        String port = "3306";
        String databaseName = "menagerie";
        String username = "javaTest";
        String password = "admin";

        MySQLAccess msa = MySQLAccess.getInstance();
        if (msa != null) {
            msa.initialise(ip, port, databaseName, username, password);
            msa.runQuery("SELECT * FROM pet", true);
        }
    }
}
