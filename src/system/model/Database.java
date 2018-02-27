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
                statement.execute(
                    "CREATE TABLE IF NOT EXISTS `dokumente` (" +
                    "numeric_id integer PRIMARY KEY AUTOINCREMENT, " +
                    "ID UNIQUE NOT NULL," +
                    "title," +
                    "author," +
                    "tags text," +
                    "shed text," +
                    "rack text," +
                    "folder text," +
                    "file text," +
                    "url text," +
                    "doc_type string check(doc_type = \"url\" or doc_type = \"file\" or doc_type = \"archive\"));");
                statement.execute(
                    "CREATE TABLE IF NOT EXISTS `keywords` (" +
                    "numeric_id integer PRIMARY KEY AUTOINCREMENT," +
                    "val text NOT NULL);");
                statement.execute(
                    "CREATE TABLE IF NOT EXISTS `dokumente_keyword` (" +
                    "dokument_id integer, "+
                    "keyword_id integer);");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public List<Documents> getDocuments() {
        try (Statement statement = conn.createStatement()) {
            ResultSet result;
            result = statement.executeQuery("SELECT id, title, author FROM documents;");
            List<Documents> returnval = new ArrayList<>(result.getFetchSize());
            while (result.next()) {
                returnval.add(new Documents(result.getInt(1),
                        result.getString(2),
                        result.getString(3)));
            }
            return returnval;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public Documents addItem(Documents documents ) {
        if( documents.getId() != null ) { // item is not new
            throw new IllegalArgumentException("Item has primary key (not new).");
        }

        // get new primaty key
        try (Statement statement = conn.createStatement()) {
            ResultSet result = statement.executeQuery("SELECT MAX(ID) + 1 AS newid FROM documents;");
            int newID = result.getInt(1);
            documents.setId(newID);

            try (PreparedStatement insertstatement = conn.prepareStatement(
                    "INSERT INTO items (id, value) VALUES (?,?,?);")) {
                insertstatement.setInt(1, documents.getId());
                insertstatement.setString(2, documents.getTitle());
                insertstatement.setString(3,documents.getAuthor());
                insertstatement.execute();

                return documents;
            } catch (SQLException e) {
                System.out.println(e);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public int deleteItem( Documents document ) {
        if( document.getId() == null ) {
            throw new IllegalArgumentException("no existing item");
        }
        try (PreparedStatement statement = conn.prepareStatement("DELETE FROM items WHERE id == ?;")) {
            statement.setInt(1, document.getId());
            return statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return -1;
    }

    public void updateItem( Documents document ) {
        if( document.getId() == null ) {
            throw new IllegalArgumentException("no existing item");
        }

        try (PreparedStatement statement = conn.prepareStatement(
                "UPDATE items SET title = ? WHERE id == ?;")) {
            statement.setString(1, document.getTitle());
            statement.setInt(2, document.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}