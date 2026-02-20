-- Initial schema for orders-api

CREATE TABLE product (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(255),
  price NUMERIC(19,2)
);

CREATE TABLE customer (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(255),
  phone VARCHAR(255) NOT NULL,
  city VARCHAR(255),
  street VARCHAR(255),
  latitude DOUBLE PRECISION,
  longitude DOUBLE PRECISION
);

CREATE TABLE merchant (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(255),
  merchant_code VARCHAR(255) NOT NULL,
  city VARCHAR(255),
  street VARCHAR(255),
  latitude DOUBLE PRECISION,
  longitude DOUBLE PRECISION
);

CREATE TABLE courier (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(255),
  vehicle VARCHAR(255),
  city VARCHAR(255),
  street VARCHAR(255),
  latitude DOUBLE PRECISION,
  longitude DOUBLE PRECISION
);

CREATE TABLE orders (
  id UUID PRIMARY KEY,
  created_at TIMESTAMPTZ,
  updated_at TIMESTAMPTZ,
  discount_percent NUMERIC(5,2),
  status SMALLINT,
  subtotal NUMERIC(19,2),
  tax_percent NUMERIC(5,2),
  total NUMERIC(19,2),
  courier_id BIGINT,
  customer_id BIGINT,
  merchant_id BIGINT
);

CREATE TABLE order_item (
  id BIGSERIAL PRIMARY KEY,
  quantity INTEGER NOT NULL,
  unit_price NUMERIC(19,2),
  product_id BIGINT,
  order_id UUID
);


ALTER TABLE orders
  ADD CONSTRAINT FK_orders_courier FOREIGN KEY (courier_id) REFERENCES courier(id);
ALTER TABLE orders
  ADD CONSTRAINT FK_orders_customer FOREIGN KEY (customer_id) REFERENCES customer(id);
ALTER TABLE orders
  ADD CONSTRAINT FK_orders_merchant FOREIGN KEY (merchant_id) REFERENCES merchant(id);

ALTER TABLE order_item
  ADD CONSTRAINT FK_orderitem_product FOREIGN KEY (product_id) REFERENCES product(id);

ALTER TABLE order_item
  ADD CONSTRAINT FK_orderitem_order FOREIGN KEY (order_id) REFERENCES orders(id);

-- Constraints and indexes
ALTER TABLE merchant ADD CONSTRAINT UK_merchant_code UNIQUE (merchant_code);
ALTER TABLE customer ADD CONSTRAINT UK_customer_phone UNIQUE (phone);
