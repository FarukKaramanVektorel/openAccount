
CREATE TABLE customers (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           first_name VARCHAR(255),
                           last_name VARCHAR(255),
                           phone VARCHAR(255),
                           note VARCHAR(255),
                           balance DECIMAL(38, 2),
                           active BOOLEAN DEFAULT TRUE
);


CREATE TABLE movements (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           movement_date DATE,
                           transaction_type INTEGER,
                           amount DECIMAL(38, 2),
                           active BOOLEAN DEFAULT TRUE,
                           customer_id BIGINT,
                           CONSTRAINT fk_movement_customer
                               FOREIGN KEY (customer_id)
                                   REFERENCES customers(id)
);