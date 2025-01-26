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

INSERT INTO Driver (
    driver_first_name,
    driver_profile_picture,
    driver_last_name,
    driver_nic,
    phone_number,
    email_address,
    license_number,
    license_expiry_date,
    driver_address,
    vehicle_assigned,
    driver_status,
    emergency_contact,
    date_of_birth,
    date_of_joining
) VALUES
      ('John', NULL, 'Doe', '987654321V', '1234567890', 'john.doe@example.com', 'LN001', '2030-12-31', '123 Main Street, City A', 'FALSE', 'Active', '0987654321', '1990-01-01', '2023-01-15'),
      ('Jane', NULL, 'Smith', '123456789V', '9876543210', 'jane.smith@example.com', 'LN002', '2028-11-15', '456 Elm Street, City B', 'FALSE', 'Active', '0123456789', '1985-05-21', '2020-05-10'),
      ('Michael', NULL, 'Brown', '753951456V', '7894561230', 'michael.brown@example.com', 'LN003', '2025-06-20', '789 Oak Street, City C', 'FALSE', 'Active', '9638527410', '1988-03-15', '2022-07-01'),
      ('Emma', NULL, 'Davis', '852456123V', '3216549870', 'emma.davis@example.com', 'LN004', '2031-09-25', '321 Pine Street, City D', 'FALSE', 'Active', '7418529630', '1992-11-05', '2021-03-20');

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
    customer_registration_number SERIAL PRIMARY KEY,
    root_user_id                 INTEGER,
    customer_address            VARCHAR(255),
    customer_nic                VARCHAR(20),
    phone_number                VARCHAR(15) NOT NULL
);

CREATE TABLE Manager
(
    manager_registration_number SERIAL PRIMARY KEY,
    root_user_id                INTEGER,
    manager_address             VARCHAR(255),
    manager_nic                 VARCHAR(50),
    phone_number                VARCHAR(50) NOT NULL,
    created_at                  TIMESTAMP,
    updated_at                  TIMESTAMP
);