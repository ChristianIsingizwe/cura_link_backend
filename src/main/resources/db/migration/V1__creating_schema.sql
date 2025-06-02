-- V1__create_hospital_schema.sql

-- Users table
CREATE TABLE users (
    id              SERIAL PRIMARY KEY,
    full_name       VARCHAR(255),
    email           VARCHAR(255) NOT NULL UNIQUE,
    password        VARCHAR(255) NOT NULL,
    birth_date      TIMESTAMP,
    role            VARCHAR(50)
);

-- Doctors table
CREATE TABLE doctors (
    id              SERIAL PRIMARY KEY,
    full_name       VARCHAR(255),
    specialization  VARCHAR(255) NOT NULL
);

-- Appointments table
CREATE TABLE appointments (
    id                SERIAL PRIMARY KEY,
    appointment_time  TIMESTAMP,
    approved          BOOLEAN NOT NULL DEFAULT FALSE,
    patient_id        INTEGER NOT NULL,
    doctor_id         INTEGER NOT NULL,
        CONSTRAINT fk_appointment_patient
            FOREIGN KEY (patient_id)
                REFERENCES users (id)
                ON DELETE CASCADE,
        CONSTRAINT fk_appointment_doctor
            FOREIGN KEY (doctor_id)
                REFERENCES doctors (id)
                ON DELETE SET NULL
);

-- Indexes for faster lookups
CREATE INDEX idx_appointments_patient ON appointments (patient_id);
CREATE INDEX idx_appointments_doctor  ON appointments (doctor_id);
