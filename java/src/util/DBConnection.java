package util;

import java.sql.*;

public abstract class DBConnection {
    private static final String DbString = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost/calendar";
    private static final String password = "sqluserpw";
    private static final String user = "sqluser";

    protected Connection connection;
    private Statement statement;

    public Connection connect(){
        System.out.println("Connecting to database.....");
        try {
            Class.forName(DbString);
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            System.out.println("Connected!");
        } catch (Exception e){
            System.out.println("Connection failed: " + e.getMessage());
        }
        return connection;
    }

    public void close() {
        try{
            if (connection != null)
                connection.close();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public ResultSet getResults(String query){
        ResultSet result = null;

        try {
            result = statement.executeQuery(query);
            result.next();
        } catch (SQLException e){
            System.out.println("Failed to execute query: " + e.getMessage());
        }

        return result;
    }

    public ResultSet getResult(PreparedStatement statement){
        ResultSet result = null;
        try {
            statement.setMaxFieldSize(1);
            result = statement.executeQuery();
            result.next();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return result;
    }
}
