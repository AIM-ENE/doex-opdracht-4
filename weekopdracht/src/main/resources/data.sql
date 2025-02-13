-- Inserting data into Restaurant table
INSERT INTO Restaurant(versie) VALUES (1);

-- Inserting data into Gerecht table
INSERT INTO Gerecht (restaurant, naam, prijs, gang) VALUES (1, 'Spaghetti_Bolognese', 9.99, 'HOOFDGERECHT');
INSERT INTO Gerecht (restaurant, naam, prijs, gang) VALUES (1, 'Caesar_Salad', 8.49, 'HOOFDGERECHT');
INSERT INTO Gerecht (restaurant, naam, prijs, gang) VALUES (1, 'Margherita_Pizza', 10.99, 'HOOFDGERECHT');

-- Inserting data into Ingredient table
INSERT INTO Ingredient (naam) VALUES ('Spaghetti');
INSERT INTO Ingredient (naam) VALUES ('Tomato Sauce');
INSERT INTO Ingredient (naam) VALUES ('Ground Beef');
INSERT INTO Ingredient (naam) VALUES ('Lettuce');
INSERT INTO Ingredient (naam) VALUES ('Croutons');
INSERT INTO Ingredient (naam) VALUES ('Parmesan Cheese');
INSERT INTO Ingredient (naam) VALUES ('Pizza Dough');
INSERT INTO Ingredient (naam) VALUES ('Mozzarella Cheese');
--
-- Inserting data into GerechtIngredient table
INSERT INTO Dosering (gerecht, ingredient, hoeveelheid) VALUES (1, 1, 200); -- 200 grams of spaghetti for Spaghetti Bolognese
INSERT INTO Dosering (gerecht, ingredient, hoeveelheid) VALUES (1, 2, 300); -- 300 grams of tomato sauce for Spaghetti Bolognese
INSERT INTO Dosering (gerecht, ingredient, hoeveelheid) VALUES (1, 3, 400); -- 400 grams of ground beef for Spaghetti Bolognese

INSERT INTO Dosering (gerecht, ingredient, hoeveelheid) VALUES (2, 4, 150); -- 150 grams of lettuce for Caesar Salad
INSERT INTO Dosering (gerecht, ingredient, hoeveelheid) VALUES (2, 5, 50); -- 50 grams of croutons for Caesar Salad
INSERT INTO Dosering (gerecht, ingredient, hoeveelheid) VALUES (2, 6, 30); -- 30 grams of Parmesan cheese for Caesar Salad

INSERT INTO Dosering (gerecht, ingredient, hoeveelheid) VALUES (3, 7, 1); -- 1 pizza dough for Margherita Pizza
INSERT INTO Dosering (gerecht, ingredient, hoeveelheid) VALUES (3, 2, 200); -- 200 grams of tomato sauce for Margherita Pizza
INSERT INTO Dosering (gerecht, ingredient, hoeveelheid) VALUES (3, 8, 300); -- 300 grams of mozzarella cheese for Margherita Pizza

-- Inserting data into Tafel table
INSERT INTO Tafel(restaurant, tafel_nummer) VALUES ( 1, 1 );
INSERT INTO Tafel(restaurant, tafel_nummer) VALUES ( 1, 3 );
INSERT INTO Tafel(restaurant, tafel_nummer) VALUES ( 1, 4 );
INSERT INTO Tafel(restaurant, tafel_nummer) VALUES ( 1, 5 );