CREATE TABLE Article
(
    article_id  SERIAL PRIMARY KEY,
    ratings     DOUBLE PRECISION NOT NULL,
    title       TEXT             NOT NULL,
    description TEXT             NOT NULL,
    author      TEXT             NOT NULL,
    media       TEXT             NOT NULL,
    is_active   BOOLEAN,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS booking;
CREATE TABLE booking
(
    booking_number               SERIAL PRIMARY KEY,
    booking_date                 TIMESTAMP        NOT NULL,
    pickup_location              VARCHAR(255)     NOT NULL,
    drop_off_location            VARCHAR(255)     NOT NULL,
    car_number                   VARCHAR(50)      NOT NULL,
    taxes                        NUMERIC(10, 2)   NOT NULL,
    distance                     DOUBLE PRECISION NOT NULL,
    estimatedTime               DOUBLE PRECISION NOT NULL,
    tax_without_cost             DOUBLE PRECISION NOT NULL,
    total_amount                 NUMERIC(10, 2)   NOT NULL,
    customer_registration_number VARCHAR(50)      NOT NULL,
    driver_id                    VARCHAR(50)      NOT NULL,
    status                       VARCHAR(50)      NOT NULL,
    created_date                 TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date                 TIMESTAMP
);

CREATE TABLE Customer
(
    registration_number SERIAL PRIMARY KEY,
    root_user_id        INTEGER,
    address             TEXT NOT NULL,
    nic                 TEXT NOT NULL UNIQUE,
    phone_number        TEXT NOT NULL
);

CREATE TABLE Driver
(
    driver_registration_number SERIAL PRIMARY KEY,
    driver_first_name          TEXT NOT NULL,
    driver_profile_picture     TEXT NOT NULL,
    driver_last_name           TEXT NOT NULL,
    driver_nic                 TEXT NOT NULL UNIQUE,
    phone_number               TEXT NOT NULL,
    email_address              TEXT,
    license_number             TEXT NOT NULL,
    license_expiry_date        DATE,
    driver_address             TEXT,
    vehicle_assigned           TEXT NOT NULL DEFAULT 'FALSE',
    driver_status              TEXT NOT NULL DEFAULT 'Active',
    emergency_contact          TEXT,
    date_of_birth              DATE NOT NULL,
    date_of_joining            DATE,
    license_images             JSONB -- Store images as JSON array
);


CREATE TABLE Guideline
(
    guidance_id SERIAL PRIMARY KEY,
    title       TEXT NOT NULL,
    description TEXT NOT NULL,
    category    TEXT NOT NULL,
    priority    TEXT NOT NULL,
    related_to  TEXT NOT NULL
);

CREATE TABLE Manager
(
    registration_number SERIAL PRIMARY KEY,
    root_user_id        INTEGER,
    address             TEXT NOT NULL,
    nic                 TEXT NOT NULL UNIQUE,
    phone_number        TEXT NOT NULL
);

CREATE TABLE Users
(
    id         SERIAL PRIMARY KEY,
    first_name TEXT NOT NULL,
    last_name  TEXT NOT NULL,
    email      TEXT NOT NULL UNIQUE,
    password   TEXT NOT NULL,
    role       TEXT NOT NULL
);

CREATE TABLE Vehicle
(
    id                      SERIAL PRIMARY KEY,
    registration_number     TEXT    NOT NULL,
    vehicle_image           TEXT    NOT NULL,
    make                    TEXT    NOT NULL,
    model                   TEXT    NOT NULL,
    year_of_manufacture     INTEGER NOT NULL,
    color                   TEXT,
    fuel_type               TEXT,
    engine_capacity         TEXT,
    chassis_number          TEXT    NOT NULL,
    vehicle_type            TEXT    NOT NULL,
    owner_name              TEXT    NOT NULL,
    owner_contact           TEXT    NOT NULL,
    owner_address           TEXT,
    insurance_provider      TEXT,
    insurance_policy_number TEXT,
    insurance_expiry_date   DATE,
    seating_capacity        INTEGER NOT NULL,
    license_plate_number    TEXT    NOT NULL,
    permit_type             TEXT,
    air_conditioning        BOOLEAN,
    additional_features     TEXT
);

