DROP DATABASE IF EXISTS project;
CREATE DATABASE project;
USE project;

CREATE TABLE privileges (
    privilege_id int NOT NULL AUTO_INCREMENT,
    privilege enum('CUSTOMER','ADMIN','SUPER_ADMIN') NOT NULL,
    PRIMARY KEY (privilege_id)
);

CREATE TABLE users (
    user_id int unsigned NOT NULL AUTO_INCREMENT,
    fname varchar(25) NOT NULL,
    lname varchar(25) NOT NULL,
    email varchar(64) NOT NULL,
    phone varchar(15) NOT NULL,
    user_password varchar(255) DEFAULT NULL,
    privilege_id int NOT NULL,
    salt varchar(255) NOT NULL,
    PRIMARY KEY (user_id),
    UNIQUE KEY email (email),
    KEY privilege_id (privilege_id),
    CONSTRAINT FOREIGN KEY (privilege_id)
        REFERENCES privileges (privilege_id) 
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE category (
    category_id INT UNSIGNED AUTO_INCREMENT,
    category VARCHAR(255) NOT NULL,
    PRIMARY KEY (category_id)
);

CREATE TABLE products (
    product_id INT UNSIGNED AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    details TEXT NOT NULL,
    description TEXT NOT NULL,
    readme TEXT NOT NULL,
    amount INT NOT NULL CHECK(amount >= 0),
    price FLOAT NOT NULL CHECK(price >= 0),
    image BLOB NOT NULL,
    PRIMARY KEY (product_id)
);

CREATE TABLE product_categories (
    product_id INT UNSIGNED NOT NULL,
    category_id INT UNSIGNED NOT NULL,
    PRIMARY KEY (product_id),
    CONSTRAINT FOREIGN KEY (product_id)
        REFERENCES products(product_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE carts (
    cart_id INT UNSIGNED NOT NULL,
    product_id INT UNSIGNED NOT NULL,
    product_amount INT NOT NULL CHECK(product_amount >= 0),
    user_id INT UNSIGNED NOT NULL,
    PRIMARY KEY(cart_id),
    UNIQUE(cart_id, user_id),
    CONSTRAINT FOREIGN KEY (product_id)
        REFERENCES products(product_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

ALTER TABLE carts ADD (
    CONSTRAINT FOREIGN KEY (user_id)
        REFERENCES users(user_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE user_history (
    user_id INT UNSIGNED NOT NULL,
    product_id INT UNSIGNED NOT NULL,
    PRIMARY KEY (user_id),
    UNIQUE(user_id, product_id),
    CONSTRAINT FOREIGN KEY (user_id)
        REFERENCES users(user_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

ALTER TABLE user_history ADD (
    CONSTRAINT FOREIGN KEY (product_id)
        REFERENCES products(product_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE api_keys (
    user_id INT UNSIGNED NOT NULL,
    api_key VARCHAR(64) NOT NULL,
    PRIMARY KEY(api_key),
    CONSTRAINT FOREIGN KEY (user_id)
        REFERENCES users(user_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE payments (
    payment_id INT UNSIGNED AUTO_INCREMENT,
    order_id INT UNSIGNED NOT NULL,
    amount INT NOT NULL CHECK(amount >= 0),
    provider VARCHAR(100) NOT NULL,
    payment_status VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (payment_id),
    UNIQUE(payment_id, order_id)
);

CREATE TABLE order_details (
	order_id INT UNSIGNED AUTO_INCREMENT,
    user_id INT UNSIGNED NOT NULL,
    fname VARCHAR(25) NOT NULL,
    lname VARCHAR(25) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    country VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    apartment VARCHAR(255),
    postal_code VARCHAR(25) NOT NULL,
    company VARCHAR(100),
    payment_id INT UNSIGNED NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(order_id),
    CONSTRAINT FOREIGN KEY(user_id)
		REFERENCES users(user_id)
        ON UPDATE CASCADE
);

ALTER TABLE order_details ADD (
	CONSTRAINT FOREIGN KEY (payment_id)
		REFERENCES payments(payment_id)
        ON UPDATE CASCADE
);

CREATE TABLE order_products (
    id INT UNSIGNED AUTO_INCREMENT,
    order_id INT UNSIGNED NOT NULL,
    product_id INT UNSIGNED NOT NULL,
    product_amount INT NOT NULL,
    CHECK(product_amount >= 0),
    unit_price FLOAT NOT NULL,
    CHECK(unit_price >= 0),
    PRIMARY KEY (id),
    CONSTRAINT FOREIGN KEY (product_id)
        REFERENCES products(product_id)
        ON UPDATE CASCADE
);

ALTER TABLE order_products ADD (
    CONSTRAINT FOREIGN KEY (order_id)
        REFERENCES order_details(order_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);