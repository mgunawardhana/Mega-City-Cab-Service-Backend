package com.megacity.backend.constant;

public class SqlQuery {

    private SqlQuery() {
    }

    /**
     * This holds all the select queries
     */
    public static class SelectQuery {

        public static final String FETCH_ALL_GUIDELINE = """
                SELECT * FROM guideline;""";

        public static final String FETCH_GUIDELINE_BY_ID = """
                SELECT * FROM guideline WHERE guidance_id = ?""";

        public static final String FETCH_VEHICLE_BY_ID = """
                SELECT * FROM vehicles WHERE id = ?""";

        public static final String FETCH_ALL_VEHICLE = """
                SELECT * FROM vehicles;""";

        private SelectQuery() {
        }
    }

    /**
     * This holds all the insert queries
     */
    public static class InsertQuery {

        public static final String ADD_NEW_GUIDELINE = """
                INSERT INTO guideline (title, description, category, priority, related_to) VALUES (?, ?, ?, ?, ?)""";

        public static final String ADD_NEW_VEHICLE = """
                INSERT INTO vehicles (registration_number,make,model,year_of_manufacture,color,fuel_type,engine_capacity,chassis_number,vehicle_type,owner_name,owner_contact,owner_address,insurance_provider,insurance_policy_number,insurance_expiry_date,seating_capacity,license_plate_number,permit_type,air_conditioning,vehicle_photo, additional_features) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)""";


        private InsertQuery() {
        }
    }

    /**
     * This holds all the update queries
     */
    public static class UpdateQuery {

        public static final String UPDATE_GUIDELINE = """
                UPDATE guideline SET title = ?, description = ?, category = ?, priority = ?, related_to = ? WHERE guidance_id = ?;""";

        public static final String UPDATE_VEHICLE = """
                UPDATE vehicles SET registration_number = ?, make = ?, model = ?, year_of_manufacture = ?, color = ?, fuel_type = ?, engine_capacity = ?, chassis_number = ?, vehicle_type = ?, owner_name = ?, owner_contact = ?, owner_address = ?, insurance_provider = ?, insurance_policy_number = ?, insurance_expiry_date = ?, seating_capacity = ?, license_plate_number = ?, permit_type = ?, air_conditioning = ?, vehicle_photo = ?, additional_features = ? WHERE id = ?""";

        private UpdateQuery() {
        }
    }

    /**
     * This holds all  delete queries
     */
    public static class DeleteQuery {

        public static final String DELETE_GUIDELINE = """
                DELETE FROM guideline WHERE guidance_id = ?;""";

        public static final String DELETE_VEHICLE = """
                DELETE FROM vehicles WHERE id = ?""";

        private DeleteQuery() {
        }
    }
}