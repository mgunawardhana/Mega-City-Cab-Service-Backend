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

INSERT INTO Article (ratings, title, description, author, media, is_active)
VALUES (4.5, 'AI in Future', 'Discussion on AI impact', 'John Doe', 'image1.jpg', TRUE),
       (3.8, 'Tech Innovations', 'Latest tech trends', 'Jane Smith', 'image2.jpg', FALSE);

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


-- Insert Sample Data
INSERT INTO booking (booking_date, pickup_location, drop_off_location, car_number, taxes, distance,
                     estimatedTime, tax_without_cost, total_amount, customer_registration_number, driver_id, status)
VALUES ('2025-02-12 10:00:00', 'Colombo', 'Kandy', 'ABC1234', 150.50, 115.75, 2.5, 120.00, 270.50, 'CUST001',
        'DRIVER001', 'PENDING'),
       ('2025-02-13 14:30:00', 'Galle', 'Matara', 'XYZ5678', 100.00, 80.25, 1.8, 85.00, 185.00, 'CUST002', 'DRIVER002',
        'COMPETED'),
       ('2025-02-14 09:15:00', 'Jaffna', 'Trincomalee', 'JKL9101', 200.75, 150.00, 3.2, 170.00, 370.75, 'CUST003',
        'DRIVER003', 'PENDING'),
       ('2025-02-15 17:45:00', 'Negombo', 'Anuradhapura', 'DEF4321', 180.25, 130.50, 2.9, 160.00, 340.25, 'CUST004',
        'DRIVER004', 'PENDING');


CREATE TABLE Customer
(
    registration_number SERIAL PRIMARY KEY,
    root_user_id        INTEGER,
    address             TEXT NOT NULL,
    nic                 TEXT NOT NULL UNIQUE,
    phone_number        TEXT NOT NULL
);

INSERT INTO Customer (root_user_id, address, nic, phone_number)
VALUES (1, '789 Street', '456789123V', '111222333'),
       (2, '101 Road', '789123456V', '444555666');

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
    vehicle_assigned           TEXT          DEFAULT 'FALSE',
    driver_status              TEXT NOT NULL DEFAULT 'Active',
    emergency_contact          TEXT,
    date_of_birth              DATE NOT NULL,
    date_of_joining            DATE
);

INSERT INTO Driver (driver_first_name, driver_profile_picture, driver_last_name, driver_nic, phone_number,
                    email_address, license_number, license_expiry_date, driver_address, vehicle_assigned, driver_status,
                    emergency_contact, date_of_birth, date_of_joining)
VALUES ('Mike', 'driver1.jpg', 'Johnson', '555666777V', '999888777', 'mike@mail.com', 'LN12345', '2030-01-01',
        '12 Driver St', 'TRUE', 'Active', '123456789', '1985-05-12', '2020-06-15'),
       ('Sara', 'driver2.jpg', 'Williams', '888999000V', '777666555', 'sara@mail.com', 'LN67890', '2032-12-31',
        '34 Taxi Ave', 'FALSE', 'Active', '987654321', '1990-09-25', '2021-08-20');

CREATE TABLE Guideline
(
    guidance_id SERIAL PRIMARY KEY,
    title       TEXT NOT NULL,
    description TEXT NOT NULL,
    category    TEXT NOT NULL,
    priority    TEXT NOT NULL,
    related_to  TEXT NOT NULL
);

INSERT INTO Guideline (title, description, category, priority, related_to)
VALUES ('Safety First', 'Follow safety protocols', 'Safety', 'High', 'Drivers'),
       ('Customer Service', 'Provide excellent service', 'Service', 'Medium', 'Bookings');

CREATE TABLE Manager
(
    registration_number SERIAL PRIMARY KEY,
    root_user_id        INTEGER,
    address             TEXT NOT NULL,
    nic                 TEXT NOT NULL UNIQUE,
    phone_number        TEXT NOT NULL
);

INSERT INTO Manager (root_user_id, address, nic, phone_number)
VALUES (3, '234 Business St', '321654987V', '888999111'),
       (4, '567 Admin Rd', '654321987V', '666777888');

CREATE TABLE Users
(
    id         SERIAL PRIMARY KEY,
    first_name TEXT NOT NULL,
    last_name  TEXT NOT NULL,
    email      TEXT NOT NULL UNIQUE,
    password   TEXT NOT NULL,
    role       TEXT NOT NULL
);

INSERT INTO Users (first_name, last_name, email, password, role)
VALUES ('Admin', 'User', 'admin@example.com', 'securepassword', 'ADMIN'),
       ('Guest', 'User', 'guest@example.com', 'guestpassword', 'USER');

CREATE TABLE Vehicle
(
    id                      SERIAL PRIMARY KEY,
    registration_number     TEXT    NOT NULL UNIQUE,
    vehicle_image           TEXT    NOT NULL,
    make                    TEXT    NOT NULL,
    model                   TEXT    NOT NULL,
    year_of_manufacture     INTEGER NOT NULL,
    color                   TEXT,
    fuel_type               TEXT,
    engine_capacity         TEXT,
    chassis_number          TEXT    NOT NULL UNIQUE,
    vehicle_type            TEXT    NOT NULL,
    owner_name              TEXT    NOT NULL,
    owner_contact           TEXT    NOT NULL,
    owner_address           TEXT,
    insurance_provider      TEXT,
    insurance_policy_number TEXT,
    insurance_expiry_date   DATE,
    seating_capacity        INTEGER NOT NULL,
    license_plate_number    TEXT    NOT NULL UNIQUE,
    permit_type             TEXT,
    air_conditioning        BOOLEAN,
    vehicle_photo           TEXT,
    additional_features     TEXT
);

INSERT INTO Vehicle (registration_number, vehicle_image, make, model, year_of_manufacture, color, fuel_type,
                     engine_capacity, chassis_number, vehicle_type, owner_name, owner_contact, owner_address,
                     insurance_provider, insurance_policy_number, insurance_expiry_date, seating_capacity,
                     license_plate_number, permit_type, air_conditioning, vehicle_photo, additional_features)
VALUES ('REG123', 'car1.jpg', 'Toyota', 'Corolla', 2019, 'White', 'Petrol', '1800cc', 'CH123456789', 'Sedan',
        'John Doe', '123456789', '123 Street', 'ABC Insurance', 'POL12345', '2026-05-20', 5, 'LP123', 'Private', TRUE,
        'photo1.jpg', 'GPS, Sunroof'),
       ('REG456', 'car2.jpg', 'Honda', 'Civic', 2020, 'Black', 'Diesel', '2000cc', 'CH987654321', 'Sedan', 'Jane Doe',
        '987654321', '456 Avenue', 'XYZ Insurance', 'POL67890', '2027-08-15', 5, 'LP456', 'Commercial', FALSE,
        'photo2.jpg', 'Leather seats, Bluetooth');
