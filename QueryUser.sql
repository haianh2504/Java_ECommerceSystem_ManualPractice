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

ALTER TABLE products
ADD COLUMN weight NUMERIC(10,3);

ALTER TABLE users
ADD CONSTRAINT email UNIQUE (email);

ALTER TABLE products
ADD CONSTRAINT product_name UNIQUE (product_name);

CREATE TYPE order_status_enum AS ENUM('PENDING_PAYMENT','SUCCESSFUL', 'CANCELLED');

CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY ,
    user_id BIGINT NOT NULL REFERENCES users(id),
    status order_status_enum NOT NULL DEFAULT 'PENDING_PAYMENT',
    created_at timestamptz NOT NULL DEFAULT now(),
    sub_total DECIMAL(10,2) NOT NULL,
    shipping_fee DECIMAL(10,2) NOT NULL DEFAULT 0,
    discount_amount DECIMAL(10,2) NOT NULL DEFAULT 0,
    total_price DECIMAL(10,2) NOT NULL
);

CREATE TABLE order_items(
    id BIGSERIAL PRIMARY KEY ,
    order_id BIGINT NOT NULL REFERENCES orders(id),
    product_id BIGINT NOT NULL REFERENCES products(id),
    quantity INT NOT NULL DEFAULT 1,
    unit_price DECIMAL(10,2) NOT NULL,
    UNIQUE (order_id, product_id)
);

CREATE TABLE carts (
    id BIGSERIAL PRIMARY KEY ,
    user_id BIGINT NOT NULL UNIQUE REFERENCES users(id),
    created_at timestamptz NOT NULL DEFAULT now()
);

CREATE TABLE cart_items (
    id BIGSERIAL PRIMARY KEY ,
    cart_id BIGINT NOT NULL REFERENCES carts(id),
    product_id BIGINT NOT NULL REFERENCES products(id),
    quantity INT NOT NULL DEFAULT 1,
    UNIQUE (cart_id, product_id)
);

alter type order_status_enum ADD VALUE 'FAILED';

-- 1. Thêm cột cart_id vào bảng orders (thay INT bằng UUID hoặc BIGINT tùy thiết kế)
ALTER TABLE orders
    ADD COLUMN cart_id BIGINT;

-- 2. Tạo ràng buộc UNIQUE cho cặp (user_id, cart_id)
ALTER TABLE orders
    ADD CONSTRAINT uq_orders_user_cart UNIQUE (user_id, cart_id);

ALTER TABLE orders
ADD CONSTRAINT fk_orders_users
FOREIGN KEY (user_id)
REFERENCES users(id)
ON DELETE CASCADE -- KHI xoá user đó thì mọi đơn hàng liên quan tới sẽ ra đi

ALTER TABLE orders
ADD CONSTRAINT fk_orders_carts
FOREIGN KEY (cart_id)
REFERENCES carts(id)

CREATE TYPE cart_status_enum AS ENUM('ACTIVE','CHECKED_OUT');
ALTER TABLE carts
ADD COLUMN status cart_status_enum;
ALTER TABLE carts
ALTER COLUMN status SET NOT NULL;

ALTER TABLE products DROP CONSTRAINT product_name;
ALTER TABLE