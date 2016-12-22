package uk.co.jaxfire;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLAccess {

    private static boolean instanceExists;

    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    private String ip;
    private String port;
    private String databaseName;
    private String username;
    private String password;

    public static MySQLAccess getInstance(){
        if (!instanceExists){
            instanceExists = true;
            return new MySQLAccess();
        } else {
            return null;
        }
    }

    private void MySQLAccess(){

    }

    public void initialise(String ip, String port, String databaseName, String username, String password){

        this.ip = ip;
        this.port = port;
        this.databaseName = databaseName;
        this.username = username;
        this.password = password;

        // Load the MySQL driver
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("The database driver could not be loaded.");
            e.printStackTrace();
        }
    }

    private void connect(){
        // Setup the connection with the DB
        String url = "jdbc:mysql://" + ip + ":" + port + "/" + databaseName;
        try {
            connect = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("A connection to the database could not be established.");
            e.printStackTrace();
        }
        System.out.println("Connection made.");
    }

    public void runQuery(String query){
        runQuery(query, false);
    }
    public void runQuery(String query, boolean printResult){
        // Statements allow to issue SQL queries to the database
        try {
            connect();
            statement = connect.createStatement();
            resultSet = statement.executeQuery(query);
            if (printResult) {
                writeResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    private void writeResultSet(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++){
                System.out.println(resultSet.getMetaData().getColumnName(i) + ": " + resultSet.getString(i));
            }
        }
    }

    public void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {
            System.out.println("The DB connection could not be closed.");
            e.printStackTrace();
        }
        System.out.println("Connection closed.");
    }
}
