CREATE TYPE user_role_enum AS ENUM('ADMIN', 'NORMAL_USER');

CREATE TYPE user_status_enum AS ENUM('ACTIVE', 'PENDING','BANNED');

CREATE TABLE users(
    id BIGSERIAL PRIMARY KEY ,
    name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(50),
    email VARCHAR(50) NOT NULL,
    role public.user_role_enum NOT NULL ,
    status public.user_status_enum NOT NULL
);

ALTER TABLE users
ADD COLUMN password_hash VARCHAR(100),
ADD COLUMN created_at TIMESTAMP;

ALTER TABLE users
ALTER COLUMN name TYPE VARCHAR(255),
ALTER COLUMN email TYPE VARCHAR(255);

ALTER TABLE users
ALTER COLUMN created_at TYPE timestamptz,
    ALTER COLUMN created_at SET NOT NULL,
    ALTER COLUMN created_at SET DEFAULT CURRENT_TIMESTAMP;
