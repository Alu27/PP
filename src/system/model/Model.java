package system.model;

import java.sql.SQLException;

public class Model {
    static private Database db;

    static {
        try {
            db = new Database();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static private Keywords keyword = new Keywords(db);
    static private Documents documents = new Documents(db);

    static private Keywords getKeywords() {
        return keyword;
    }

    static private Documents getDocuments() {
        return documents;
    }
}
