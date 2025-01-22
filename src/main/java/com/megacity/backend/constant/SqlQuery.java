package com.megacity.backend.constant;

public class SqlQuery {

    private SqlQuery() {
    }

    /**
     * This holds all the select queries
     */
    public static class SelectQuery {
        public static final String FETCH_ALL_GUIDELINE = """
                SELECT * FROM Guideline;""";

        public static final String FETCH_GUIDELINE_BY_ID = """
                """;

        private SelectQuery() {
        }
    }

    /**
     * This holds all the insert queries
     */
    public static class InsertQuery {

        public static final String ADD_NEW_GUIDELINE = """
                INSERT INTO Guideline (title, description, category, priority, related_to) VALUES (?, ?, ?, ?, ?)""";


        private InsertQuery() {
        }
    }

    /**
     * This holds all the update queries
     */
    public static class UpdateQuery {

        public static final String UPDATE_GUIDELINE = """
                UPDATE Guideline SET title = ?, description = ?, category = ?, priority = ?, related_to = ? WHERE guidance_id = ?;""";

        private UpdateQuery() {
        }
    }

    /**
     * This holds all  delete queries
     */
    public static class DeleteQuery {
        public static final String DELETE_GUIDELINE = """
                DELETE FROM Guideline WHERE guidance_id = ?;""";

        private DeleteQuery() {
        }
    }
}