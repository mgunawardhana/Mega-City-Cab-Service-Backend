CREATE TABLE driver
(
    driver_registration_number SERIAL PRIMARY KEY,
    driver_first_name          VARCHAR(255)                  NOT NULL,
    driver_last_name           VARCHAR(255)                  NOT NULL,
    driver_nic                 VARCHAR(255)                  NOT NULL,
    phone_number               VARCHAR(255)                  NOT NULL,
    email_address              VARCHAR(255),
    license_number             VARCHAR(255)                  NOT NULL,
    license_expiry_date        DATE,
    driver_address             VARCHAR(255),
    vehicle_assigned           VARCHAR(255) DEFAULT 'FALSE',
    driver_status              VARCHAR(255) DEFAULT 'Active' NOT NULL,
    emergency_contact          VARCHAR(255),
    date_of_birth              DATE                          NOT NULL,
    date_of_joining            DATE
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

CREATE TABLE Customer
(
    customer_registration_number SERIAL PRIMARY KEY,
    root_user_id                 INTEGER,
    customer_address             VARCHAR(255),
    customer_nic                 VARCHAR(50),
    phone_number                 VARCHAR(50) NOT NULL,
    created_at                   TIMESTAMP,
    updated_at                   TIMESTAMP
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
