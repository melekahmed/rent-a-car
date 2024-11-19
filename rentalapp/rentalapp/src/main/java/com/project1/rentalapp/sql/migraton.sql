-- Създаване на таблица за автомобили
CREATE TABLE cars (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      model VARCHAR(255) NOT NULL,
                      location VARCHAR(255) NOT NULL,
                      price_per_day DECIMAL(10, 2) NOT NULL,
                      is_deleted BOOLEAN DEFAULT FALSE
);

-- Създаване на таблица за оферти
CREATE TABLE offers (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        customer_name VARCHAR(255) NOT NULL,
                        car_id BIGINT NOT NULL,
                        rental_days INT NOT NULL CHECK (rental_days > 0),
                        total_price DECIMAL(10, 2) NOT NULL,
                        status VARCHAR(20) NOT NULL CHECK (status IN ('активна', 'приета', 'отменена')),
                        is_deleted BOOLEAN DEFAULT FALSE,
                        FOREIGN KEY (car_id) REFERENCES cars(id)
);

-- Вмъкване на данни за автомобили
INSERT INTO cars (location, model, price_per_day) VALUES

                                                      ('София', 'Toyota Corolla', 50.00),
                                                      ('Пловдив', 'Ford Focus', 45.00),
                                                      ('Варна', 'BMW 3 Series', 100.00),
                                                      ('Бургас', 'Audi A4', 80.00);

-- Вмъкване на данни за оферти
INSERT INTO offers (customer_name, car_id, rental_days, total_price, status) VALUES
                                                                                 ('Иван Петров', 1, 3, 150.00, 'активна'),  -- car_id за Toyota Corolla
                                                                                 ('Мария Георгиева', 2, 2, 90.00, 'приета'), -- car_id за Ford Focus
                                                                                 ('Петър Иванов', 3, 5, 500.00, 'активна');  -- car_id за BMW 3 Series

-- Листване на активни автомобили
SELECT * FROM cars WHERE is_deleted = FALSE;

-- Листване на активни оферти
SELECT * FROM offers WHERE is_deleted = FALSE