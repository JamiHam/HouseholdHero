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
VALUES ('category.baked.goods.text'),
  ('category.bread.text'),
  ('category.candy.text'),
  ('category.canned.goods.text'),
  ('category.condiments.text'),
  ('category.dairy.text'),
  ('category.eggs.text'),
  ('category.fats.and.oils.text'),
  ('category.fish.text'),
  ('category.fruits.and.berries.text'),
  ('category.juice.text'),
  ('category.meat.text'),
  ('category.nuts.and.seeds.text'),
  ('category.other.drinks.text'),
  ('category.other.food.text'),
  ('category.plant.based.dairy.text'),
  ('category.plant.based.protein.text'),
  ('category.poultry.text'),
  ('category.rice.and.pasta.text'),
  ('category.shellfish.text'),
  ('category.soft.drinks.text'),
  ('category.sugars.and.honey.text'),
  ('category.vegetables.text');