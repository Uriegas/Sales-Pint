--id is barcode
CREATE TABLE IF NOT EXISTS products (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL UNIQUE,
    description TEXT NOT NULL,
    price REAL NOT NULL
);

CREATE TABLE IF NOT EXISTS offers (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL,
    type TEXT NOT NULL,
    product_id INTEGER NOT NULL,
    FOREIGN KEY(product_id) REFERENCES products(id)
);

--Order id is a unique identifier for the order
CREATE TABLE IF NOT EXISTS sales (
    id INTEGER PRIMARY KEY,
    order_id INTEGER NOT NULL,
    product_id INTEGER NOT NULL,
    quantity INTEGER NOT NULL,
    total_price REAL NOT NULL,
    date TEXT NOT NULL,
    promotion_id INTEGER,
    FOREIGN KEY(product_id) REFERENCES products(id),
    FOREIGN KEY(order_id) REFERENCES orders(id)
);
-- An order is a bunch of sales
CREATE TABLE IF NOT EXISTS orders (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    date TEXT NOT NULL
);

-- CREATE TRIGGER IF NOT EXISTS insert_sale AFTER DELETE ON products
-- Theres no procedures in SQLITE >:|

-- BEGIN TRANSACTION;
--     INSERT INTO products(id, name, description, price) VALUES(1, 'Coffee', 'Coffee', 1.99);

--> TICKET
-- Get a ticket for the sale (Get all sales of order_id = <order_id>)
SELECT products.name, products.price, sales.quantity, sales.total_price
FROM products INNER JOIN sales
ON products.id = sales.product_id
WHERE sales.order_id = <order_id>;

-- Get header of ticket (id and date)
SELECT * FROM orders WHERE id = <order_id>;

-- Get total of ticket
SELECT SUM(total_price) FROM sales WHERE order_id = <order_id>;

