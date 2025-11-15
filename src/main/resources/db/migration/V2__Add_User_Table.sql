CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(50) NOT NULL,
                       first_name VARCHAR(100),
                       last_name VARCHAR(100)
);

--  (Şifre: 'password')

INSERT INTO users (username, password, role, first_name, last_name)
VALUES ('admin', '$2a$10$f/gS.gY/mN0HCmBLK.1lI.xPM.b1L9kM364lPcLQYpIFd7S.fCrqy', 'ADMIN', 'Admin', 'User');