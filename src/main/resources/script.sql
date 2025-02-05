CREATE TABLE Driver (
                        driver_registration_number SERIAL PRIMARY KEY,
                        driver_first_name VARCHAR(255) NOT NULL,
                        driver_profile_picture TEXT,
                        driver_last_name VARCHAR(255) NOT NULL,
                        driver_nic VARCHAR(255) NOT NULL,
                        phone_number VARCHAR(255) NOT NULL,
                        email_address VARCHAR(255),
                        license_number VARCHAR(255) NOT NULL,
                        license_expiry_date DATE,
                        driver_address VARCHAR(255),
                        vehicle_assigned VARCHAR(255) DEFAULT 'FALSE',
                        driver_status VARCHAR(255) NOT NULL DEFAULT 'Active',
                        emergency_contact VARCHAR(255),
                        date_of_birth DATE NOT NULL,
                        date_of_joining DATE
);

CREATE TABLE _ARTICLE
(
    article_id  SERIAL PRIMARY KEY,
    discount    DOUBLE PRECISION            NOT NULL,
    title       TEXT,
    description TEXT,
    author      TEXT,
    media       TEXT,
    is_active   BOOLEAN,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS Customer CASCADE;
CREATE TABLE Customer
(
    registration_number SERIAL PRIMARY KEY,
    root_user_id                 INTEGER,
    address            VARCHAR(255),
    nic                VARCHAR(20),
    phone_number                VARCHAR(15) NOT NULL
);

DROP TABLE IF EXISTS Manager CASCADE ;
CREATE TABLE Manager
(
    registration_number SERIAL PRIMARY KEY,
    root_user_id                INTEGER,
    address             VARCHAR(255),
    nic                 VARCHAR(50),
    phone_number                VARCHAR(50) NOT NULL,
    created_at                  TIMESTAMP,
    updated_at                  TIMESTAMP
);


CREATE TABLE vehicles (
                          id SERIAL PRIMARY KEY,
                          registration_number VARCHAR NOT NULL UNIQUE,
                          vehicle_image TEXT,
                          make VARCHAR NOT NULL,
                          model VARCHAR NOT NULL,
                          year_of_manufacture INT NOT NULL,
                          color VARCHAR,
                          fuel_type VARCHAR,
                          engine_capacity VARCHAR,
                          chassis_number VARCHAR NOT NULL UNIQUE,
                          vehicle_type VARCHAR NOT NULL,
                          owner_name VARCHAR NOT NULL,
                          owner_contact VARCHAR NOT NULL,
                          owner_address VARCHAR,
                          insurance_provider VARCHAR,
                          insurance_policy_number VARCHAR,
                          insurance_expiry_date DATE,
                          seating_capacity INT NOT NULL,
                          license_plate_number VARCHAR NOT NULL UNIQUE,
                          permit_type VARCHAR,
                          air_conditioning BOOLEAN,
                          vehicle_photo VARCHAR,
                          additional_features VARCHAR
);

