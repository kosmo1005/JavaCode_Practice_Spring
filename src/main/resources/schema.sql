DROP TABLE IF EXISTS good;
DROP TABLE IF EXISTS user_order;
DROP TABLE IF EXISTS my_user;

CREATE TABLE my_user (
                        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                        name VARCHAR(255) NOT NULL,
                        email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE user_order (
                         id UUID PRIMARY KEY,
                         user_id UUID NOT NULL,
                         status VARCHAR(50) NOT NULL,
                         total_price DECIMAL(19,2),
                         FOREIGN KEY (user_id) REFERENCES my_user(id) ON DELETE CASCADE
);

CREATE TABLE good (
                      id UUID PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      price DECIMAL(19,2) NOT NULL,
                      order_id UUID,
                      FOREIGN KEY (order_id) REFERENCES user_order(id) ON DELETE CASCADE
);
