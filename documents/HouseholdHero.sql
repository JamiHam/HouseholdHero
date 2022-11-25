DROP DATABASE IF EXISTS HouseholdHero;
CREATE DATABASE HouseholdHero;
USE HouseholdHero;

CREATE TABLE Category
(
  category_ID INT NOT NULL AUTO_INCREMENT,
  type VARCHAR(40) NOT NULL,
  PRIMARY KEY (category_ID)
);

CREATE TABLE Status
(
  status_ID INT NOT NULL AUTO_INCREMENT,
  status VARCHAR(40) NOT NULL,
  PRIMARY KEY (status_ID)
);

CREATE TABLE Budget
(
  budget_ID INT NOT NULL AUTO_INCREMENT,
  planned_budget FLOAT NOT NULL,
  spent_budget FLOAT NOT NULL,
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  PRIMARY KEY (budget_ID)
);

CREATE TABLE Product
(
  product_ID INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(40) NOT NULL,
  price FLOAT NOT NULL,
  best_before DATE NOT NULL,
  category_ID INT NOT NULL,
  status_ID INT NOT NULL,
  budget_ID INT NOT NULL,
  PRIMARY KEY (product_ID),
  FOREIGN KEY (category_ID) REFERENCES Category(category_ID),
  FOREIGN KEY (status_ID) REFERENCES Status(status_ID),
  FOREIGN KEY (budget_ID) REFERENCES Budget(budget_ID)
);

INSERT INTO Status (status)
VALUES ('fridge'),
  ('used'),
  ('waste');

INSERT INTO Category (type)
VALUES ('baked goods'),
  ('bread'),
  ('candy'),
  ('canned goods'),
  ('condiments'),
  ('dairy'),
  ('eggs'),
  ('fats and oils'),
  ('fish'),
  ('fruits and berries'),
  ('juice'),
  ('meat'),
  ('nuts and seeds'),
  ('other drinks'),
  ('other food'),
  ('plant-based dairy alternatives'),
  ('plant-based protein'),
  ('poultry'),
  ('rice and pasta'),
  ('shellfish'),
  ('soft drinks'),
  ('sugars and honey'),
  ('vegetables');