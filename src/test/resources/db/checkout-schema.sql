DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS cart_items;
DROP TABLE IF EXISTS carts;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(50) UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    role VARCHAR(30) NOT NULL,
    status VARCHAR(30) NOT NULL,
    password_hash VARCHAR(100) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    quantity INTEGER NOT NULL CHECK (quantity >= 0),
    price NUMERIC(19, 2) NOT NULL CHECK (price > 0),
    status VARCHAR(30) NOT NULL,
    type VARCHAR(30) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    weight NUMERIC(10, 3),
    CHECK ((type = 'PHYSICAL' AND weight > 0) OR (type = 'DIGITAL' AND weight IS NULL))
);

CREATE TABLE carts (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(30) NOT NULL CHECK (status IN ('ACTIVE', 'CHECKED_OUT'))
);

CREATE TABLE cart_items (
    id BIGSERIAL PRIMARY KEY,
    cart_id BIGINT NOT NULL REFERENCES carts(id),
    product_id BIGINT NOT NULL REFERENCES products(id),
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    UNIQUE (cart_id, product_id)
);

CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    cart_id BIGINT NOT NULL REFERENCES carts(id),
    status VARCHAR(30) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    sub_total NUMERIC(19, 2) NOT NULL CHECK (sub_total > 0),
    shipping_fee NUMERIC(19, 2) NOT NULL CHECK (shipping_fee >= 0),
    discount_amount NUMERIC(19, 2) NOT NULL CHECK (discount_amount >= 0),
    total_price NUMERIC(19, 2) NOT NULL CHECK (total_price >= 0),
    UNIQUE (user_id, cart_id),
    CHECK (discount_amount <= sub_total + shipping_fee),
    CHECK (total_price = sub_total + shipping_fee - discount_amount)
);

CREATE TABLE order_items (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL REFERENCES orders(id),
    product_id BIGINT NOT NULL REFERENCES products(id),
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    unit_price NUMERIC(19, 2) NOT NULL CHECK (unit_price > 0),
    UNIQUE (order_id, product_id)
);
