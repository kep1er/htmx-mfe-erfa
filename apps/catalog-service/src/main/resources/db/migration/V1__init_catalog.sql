CREATE TABLE IF NOT EXISTS products (
    id BIGSERIAL PRIMARY KEY,
    sku VARCHAR(64) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    price_cents INTEGER NOT NULL CHECK (price_cents >= 0)
);

INSERT INTO products (sku, name, price_cents)
VALUES ('DEMO-MUG', 'Demo Mug', 1299),
       ('DEMO-HOODIE', 'Demo Hoodie', 4999),
       ('DEMO-STICKER', 'Demo Sticker Pack', 699)
ON CONFLICT (sku) DO NOTHING;
