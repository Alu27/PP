package system.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private Connection conn;

    public Database() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:/Dokumentverwaltung.db");

            try (Statement statement = conn.createStatement()) {
                statement.execute("CREATE TABLE IF NOT EXISTS documentsDB");
                statement.execute("CREATE TABLE IF NOT EXISTS keywordsDB " +
                        "(" + " ID INTEGER PRIMARY KEY, keywords VARCHAR(20));");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public List<Keywords> getKeyword() {
        try (Statement statement = conn.createStatement()) {
            ResultSet result;
            result = statement.executeQuery("SELECT id, keywords, FROM keywordsDB;");
            List<Keywords> returnval = new ArrayList<>(result.getFetchSize());
            while (result.next()) {
                returnval.add(new Keywords(
                        result.getInt(1),
                        result.getString(2)));
            }
            return returnval;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public Keywords addKeyword(Keywords keywords ) {
        if( keywords.getId() != null ) { // item is not new
            throw new IllegalArgumentException("Item has primary key (not new).");
        }

        // get new primaty key
        try (Statement statement = conn.createStatement()) {
            ResultSet result = statement.executeQuery("SELECT MAX(ID) + 1 AS newid FROM keywordsDB;");
            int newID = result.getInt(1);
            keywords.setId(newID);

            try (PreparedStatement insertstatement = conn.prepareStatement(
                    "INSERT INTO keywordsDB (id, keywords) VALUES (?, ?);")) {
                insertstatement.setInt(1, keywords.getId());
                insertstatement.setString(2, keywords.getKeywords());
                insertstatement.execute();

                return keywords;
            } catch (SQLException e) {
                System.out.println(e);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public int deleteKeyword(Keywords keywords ) {
        if( keywords.getId() == null ) {
            throw new IllegalArgumentException("no existing item");
        }
        try (PreparedStatement statement = conn.prepareStatement("DELETE FROM items WHERE id == ?;")) {
            statement.setInt(1, keywords.getId());
            return statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return -1;
    }
}