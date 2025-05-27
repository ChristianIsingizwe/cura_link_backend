BEGIN;

-- Parent table for all users
CREATE TABLE users (
                       id               BIGSERIAL       PRIMARY KEY,
                       full_name        VARCHAR(255)    NOT NULL,
                       email            VARCHAR(255)    NOT NULL UNIQUE,
                       password         VARCHAR(255)    NOT NULL
);

-- Doctor table inherits id, full_name, email, password
CREATE TABLE doctors (
                         specialization      VARCHAR(255)    NOT NULL,
                         years_experience    INTEGER         NOT NULL,
                         CONSTRAINT doctors_pkey PRIMARY KEY (id)
) INHERITS (users);

-- Patient table inherits id, full_name, email, password
CREATE TABLE patients (
                          age                 INTEGER         NOT NULL,
                          CONSTRAINT patients_pkey PRIMARY KEY (id)
) INHERITS (users);

CREATE TABLE appointments (
                              id                  BIGSERIAL       PRIMARY KEY,
                              patient_id          BIGINT          NOT NULL,
                              doctor_id           BIGINT          NOT NULL,
                              appointment_time    TIMESTAMP       NOT NULL,
                              approved            BOOLEAN         NOT NULL DEFAULT FALSE,
                              CONSTRAINT fk_appointment_patient
                                  FOREIGN KEY (patient_id)
                                      REFERENCES patients (id)
                                      ON DELETE CASCADE,
                              CONSTRAINT fk_appointment_doctor
                                  FOREIGN KEY (doctor_id)
                                      REFERENCES doctors (id)
                                      ON DELETE RESTRICT
);

COMMIT;
