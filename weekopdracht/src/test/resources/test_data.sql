-- Inserting data into Menu table
INSERT INTO Restaurant(versie) VALUES (1);

-- Inserting data into Gerecht table
INSERT INTO Gerecht (restaurant, naam, prijs) VALUES (1, 'rib-eye', 20.00);
INSERT INTO Gerecht (restaurant, naam, prijs) VALUES (1, 'zalm', 20.00);
INSERT INTO Gerecht (restaurant, naam, prijs) VALUES (1, 'salade', 20.00);

-- Inserting data into Ingredient table
INSERT INTO Ingredient (naam) VALUES ('rib-eye');
INSERT INTO Ingredient (naam) VALUES ('zalm');
INSERT INTO Ingredient (naam) VALUES ('saus');
INSERT INTO Ingredient (naam) VALUES ('salade');
--
-- Inserting data into GerechtIngredient table
INSERT INTO Dosering (gerecht, ingredient, hoeveelheid) VALUES (1, 1, 100);

INSERT INTO Dosering (gerecht, ingredient, hoeveelheid) VALUES (2, 2, 100);
INSERT INTO Dosering (gerecht, ingredient, hoeveelheid) VALUES (2, 3, 100);

INSERT INTO Dosering (gerecht, ingredient, hoeveelheid) VALUES (3, 4, 100);

-- Inserting data into Tafel table
INSERT INTO Tafel(restaurant, tafel_nummer) VALUES ( 1, 1 );
INSERT INTO Tafel(restaurant, tafel_nummer) VALUES ( 1, 3 );
INSERT INTO Tafel(restaurant, tafel_nummer) VALUES ( 1, 4 );
INSERT INTO Tafel(restaurant, tafel_nummer) VALUES ( 1, 5 );