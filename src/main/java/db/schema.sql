CREATE DATABASE IF NOT EXISTS finance;

USE finance;

CREATE TABLE IF NOT EXISTS accountf (
    id CHAR(36) NOT NULL,
    username VARCHAR(255),
    email VARCHAR(255),
    passwordf VARCHAR(255),
    role ENUM('ADMIN', 'USER') NOT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS categories (
    account_id CHAR(36) NOT NULL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (account_id, name),
    CONSTRAINT fk_category_account
        FOREIGN KEY (account_id)
        REFERENCES accountf(id)
        ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS transactions (
    id INT NOT NULL AUTO_INCREMENT,
    account_id CHAR(36) NOT NULL,
    type ENUM('INCOME', 'EXPENSE') NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    datef DATE NOT NULL,
    descriptionf VARCHAR(255),
    categoryName VARCHAR(255),
    PRIMARY KEY (id),
    CONSTRAINT fk_transaction_account
        FOREIGN KEY (account_id)
        REFERENCES accountf(id)
        ON DELETE CASCADE
);
