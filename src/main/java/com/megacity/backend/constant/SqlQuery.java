package com.megacity.backend.constant;

public class SqlQuery {

    private SqlQuery() {
    }

    /**
     * This holds all the select queries
     */
    public static class SelectQuery {

        public static final String SELECT_ARTICLES = """
                SELECT article_id, discount, title, description, author, media, is_active, created_at, updated_at FROM _article LIMIT ? OFFSET ?""";

        public static final String SELECT_ARTICLE_BY_ID = """
                SELECT article_id, discount, title, description, author, media, is_active, created_at, updated_at FROM _article WHERE article_id = ?""";

        public static final String FETCH_ALL_GUIDELINE = """
                SELECT * FROM guideline;""";

        public static final String FETCH_GUIDELINE_BY_ID = """
                SELECT * FROM guideline WHERE guidance_id = ?""";

        public static final String FETCH_VEHICLE_BY_ID = """
                SELECT * FROM vehicles WHERE id = ?""";

        public static final String FETCH_ALL_VEHICLE = """
                SELECT * FROM vehicles;""";

        public static final String GET_DRIVER_BY_NIC = """
                SELECT * FROM driver WHERE driver_nic = ?;""";

        public static final String FETCH_ALL_DRIVERS = """
                SELECT * FROM driver""";

        public static final String GET_ALL_CUSTOMERS = """
                SELECT * FROM customer""";

        public static final String GET_CUSTOMER_BY_ID = """
                SELECT * FROM customer WHERE customer_registration_number = ?""";

        public static final String GET_CUSTOMER_BY_NIC = """
                SELECT * FROM customer WHERE customer_nic = ?""";

        private SelectQuery() {
        }
    }

    /**
     * This holds all the insert queries
     */
    public static class InsertQuery {

        public static final String INSERT_ARTICLE = """
                INSERT INTO _article ( discount, title, description, author, media, is_active ) VALUES (?, ?, ?, ?, ?, ?);""";

        public static final String ADD_NEW_GUIDELINE = """
                INSERT INTO guideline (title, description, category, priority, related_to) VALUES (?, ?, ?, ?, ?)""";

        public static final String ADD_NEW_VEHICLE = """
                INSERT INTO vehicles (registration_number,make,model,year_of_manufacture,color,fuel_type,engine_capacity,chassis_number,vehicle_type,owner_name,owner_contact,owner_address,insurance_provider,insurance_policy_number,insurance_expiry_date,seating_capacity,license_plate_number,permit_type,air_conditioning,vehicle_photo, additional_features) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)""";

        public static final String ADD_NEW_DRIVER = """
                INSERT INTO driver (driver_first_name, driver_last_name, driver_nic, phone_number, email_address, license_number, license_expiry_date, driver_address, vehicle_assigned, driver_status, emergency_contact, date_of_birth, date_of_joining) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)""";

        public static final String ADD_NEW_CUSTOMER = """
                INSERT INTO customer (root_user_id, customer_address, customer_nic, phone_number) VALUES (?, ?, ?, ?)""";


        private InsertQuery() {
        }
    }

    /**
     * This holds all the update queries
     */
    public static class UpdateQuery {

        public static final String UPDATE_ARTICLE = """
                UPDATE _article SET discount = ?, title = ?, description = ?, author = ?, media = ?, is_active = ? WHERE article_id = ?""";

        public static final String UPDATE_GUIDELINE = """
                UPDATE guideline SET title = ?, description = ?, category = ?, priority = ?, related_to = ? WHERE guidance_id = ?;""";

        public static final String UPDATE_VEHICLE = """
                UPDATE vehicles SET registration_number = ?, make = ?, model = ?, year_of_manufacture = ?, color = ?, fuel_type = ?, engine_capacity = ?, chassis_number = ?, vehicle_type = ?, owner_name = ?, owner_contact = ?, owner_address = ?, insurance_provider = ?, insurance_policy_number = ?, insurance_expiry_date = ?, seating_capacity = ?, license_plate_number = ?, permit_type = ?, air_conditioning = ?, vehicle_photo = ?, additional_features = ? WHERE id = ?""";

        public static final String UPDATE_DRIVER = """
                UPDATE driver SET driver_first_name = ?, driver_last_name = ?, driver_nic = ?, phone_number = ?, email_address = ?, license_number = ?, license_expiry_date = ?, driver_address = ?, vehicle_assigned = ?, driver_status = ?, emergency_contact = ?, date_of_birth = ?, date_of_joining = ? WHERE driver_registration_number = ?""";

        public static final String UPDATE_CUSTOMER = """
                UPDATE customer SET root_user_id = ?, customer_address = ?, customer_nic = ?, phone_number = ? WHERE customer_registration_number = ?""";

        private UpdateQuery() {
        }
    }

    /**
     * This holds all  delete queries
     */
    public static class DeleteQuery {

        public static final String DELETE_ARTICLE = """
                DELETE FROM _article WHERE article_id = ?""";

        public static final String DELETE_GUIDELINE = """
                DELETE FROM guideline WHERE guidance_id = ?;""";

        public static final String DELETE_VEHICLE = """
                DELETE FROM vehicles WHERE id = ?""";

        public static final String DELETE_DRIVER_BY_NIC = """
                DELETE FROM driver WHERE driver_nic = ?""";

        public static final String DELETE_CUSTOMER_BY_ID = """
                DELETE FROM customer WHERE customer_registration_number = ?""";

        private DeleteQuery() {
        }
    }
}