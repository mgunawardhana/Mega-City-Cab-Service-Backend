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
                SELECT * FROM guideline LIMIT ? OFFSET ?;""";

        public static final String FETCH_GUIDELINE_BY_ID = """
                SELECT * FROM guideline WHERE guidance_id = ?""";

        public static final String FETCH_VEHICLE_BY_ID = """
                SELECT * FROM vehicles WHERE id = ?""";

        public static final String FETCH_ALL_VEHICLE = """
                SELECT * FROM vehicles LIMIT ? OFFSET ?;""";

        public static final String GET_DRIVER_BY_NIC = """
                SELECT * FROM driver WHERE driver_nic = ?;""";

        public static final String FETCH_ALL_DRIVERS = """
                SELECT * FROM driver""";

        public static final String GET_ALL_CUSTOMERS = """
                SELECT * FROM customer""";

        public static final String GET_CUSTOMER_BY_ID = """
                SELECT * FROM customer WHERE registration_number = ?""";

        public static final String GET_CUSTOMER_BY_NIC = """
                SELECT * FROM customer WHERE nic = ?""";

        public static final String GET_MANAGER_BY_ID = """
                SELECT * FROM manager WHERE registration_number = ?""";

        public static final String GET_ALL_MANAGERS = """
                SELECT * FROM manager""";

        public static final String GET_ALL_BOOKINGS = """
                SELECT * FROM booking""";

        public static final String GET_BOOKING_BY_ID = """
                SELECT * FROM booking WHERE booking_number = ?""";

        public static final String FIND_CUSTOMER_BY_ROOT_USER_ID = """
                SELECT registration_number, root_user_id, address, nic, phone_number FROM customer WHERE root_user_id = ?""";

        public static final String FIND_MANAGER_BY_ROOT_USER_ID = """
                SELECT registration_number, root_user_id, address, nic, phone_number FROM manager WHERE root_user_id = ?""";


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
                INSERT INTO driver (driver_first_name, driver_profile_picture, driver_last_name, driver_nic, phone_number, email_address, license_number, license_expiry_date, driver_address, vehicle_assigned, driver_status, emergency_contact, date_of_birth, date_of_joining) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)""";

        public static final String ADD_NEW_CUSTOMER = """
                INSERT INTO customer (root_user_id, address, nic, phone_number) VALUES (?, ?, ?, ?)""";

        public static final String ADD_NEW_MANAGER = """
                INSERT INTO manager (root_user_id, address, nic, phone_number) VALUES (?, ?, ?, ?)""";

        public static final String ADD_NEW_BOOKING = """
                INSERT INTO booking (destination_details, booking_date, pickup_location, drop_off_location, car_number, fare, taxes, discount, total_amount, customer_registration_number, customer_name, driver_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)""";

        public static final String INSERT_DOCTOR = """
                INSERT INTO instructor ( first_name, last_name, email, phone_number, date_of_birth, specialization, license_number, license_expiry_date, issuing_country, qualifications, registration_authority, registration_id, nationality, years_of_experience, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);""";


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
                UPDATE customer SET root_user_id = ?, address = ?, nic = ?, phone_number = ? WHERE registration_number = ?""";

        public static final String UPDATE_MANAGER = """
                UPDATE manager SET root_user_id = ?, address = ?, nic = ?, phone_number = ? WHERE registration_number = ?;
                """;

        public static final String UPDATE_BOOKING = """
                UPDATE booking SET destination_details = ?, booking_date = ?, pickup_location = ?, drop_off_location = ?, car_number = ?, fare = ?, taxes = ?, discount = ?, total_amount = ?, customer_registration_number = ?, customer_name = ? WHERE booking_number = ?"";
                """;

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
                DELETE FROM customer WHERE registration_number = ?""";

        public static final String DELETE_MANAGER = """
                DELETE FROM manager WHERE registration_number = ?""";

        public static final String DELETE_BOOKING_BY_ID = """
                DELETE FROM booking WHERE booking_number = ?""";

        private DeleteQuery() {
        }
    }
}