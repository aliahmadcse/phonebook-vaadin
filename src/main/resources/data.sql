CREATE TABLE phonebook (
    id INT AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(255),
    lastName VARCHAR(255),
    street VARCHAR(255),
    city VARCHAR(255),
    country VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(255),
    lastUpdatedAt TIMESTAMP
);


INSERT INTO phonebook (firstName, lastName, street, city, country, phone, email, lastUpdatedAt)
VALUES
('John', 'Doe', '123 Main St', 'Cityville', 'Countryland', '09123456789', 'john.doe@example.com', CURRENT_TIMESTAMP),
('Jane', 'Smith', '456 Oak St', 'Townsville', 'Countryland', '09234567890', 'jane.smith@example.com', CURRENT_TIMESTAMP),
('Bob', 'Johnson', '789 Pine St', 'Villagetown', 'Countryland', '09345678901', 'bob.johnson@example.com', CURRENT_TIMESTAMP),
('Alice', 'Brown', '321 Cedar St', 'Cityville', 'Countryland', '09456789012', 'alice.brown@example.com', CURRENT_TIMESTAMP),
('Tom', 'Davis', '654 Birch St', 'Townsville', 'Countryland', '09556789013', 'tom.davis@example.com', CURRENT_TIMESTAMP),
('Sara', 'Miller', '987 Walnut St', 'Villagetown', 'Countryland', '09656789014', 'sara.miller@example.com', CURRENT_TIMESTAMP);




