CREATE TABLE Restaurant(
    id INT PRIMARY KEY AUTO_INCREMENT,
    versie BIGINT
);

CREATE TABLE Gerecht (
    id INT PRIMARY KEY AUTO_INCREMENT,
    restaurant INT NOT NULL,
    naam VARCHAR(255) NOT NULL,
    prijs DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (restaurant) REFERENCES Restaurant(id)
);

CREATE TABLE Ingredient (
    id INT PRIMARY KEY AUTO_INCREMENT,
    naam VARCHAR(255) NOT NULL
);

CREATE TABLE Dosering (
    gerecht INT NOT NULL,
    ingredient INT NOT NULL,
    hoeveelheid INT NOT NULL,
    UNIQUE (gerecht, ingredient),
    FOREIGN KEY (gerecht) REFERENCES Gerecht(id),
    FOREIGN KEY (ingredient) REFERENCES Ingredient(id)
);

CREATE TABLE Winkelmand (
    id INT PRIMARY KEY AUTO_INCREMENT,
    restaurant INT NOT NULL,
    datum_tijd DATETIME,
    FOREIGN KEY (restaurant) REFERENCES Restaurant(id)
);

CREATE TABLE Winkelmand_Gerecht (
    id INT PRIMARY KEY AUTO_INCREMENT,
    winkelmand INT NOT NULL,
    gerecht INT NOT NULL,
    UNIQUE (winkelmand, gerecht),
    FOREIGN KEY (winkelmand) REFERENCES Winkelmand(id),
    FOREIGN KEY (gerecht) REFERENCES Gerecht(id)
);

CREATE TABLE Tafel (
    id INT PRIMARY KEY AUTO_INCREMENT,
    tafel_nummer INT NOT NULL,
    rekening DOUBLE,
    restaurant INT NOT NULL,
    FOREIGN KEY (restaurant) REFERENCES Restaurant(id)
);

CREATE TABLE Bestelling (
    id INT PRIMARY KEY AUTO_INCREMENT,
    status INT NOT NULL,
    tafel INT NOT NULL,
    FOREIGN KEY (tafel) REFERENCES Tafel(id)
);

CREATE TABLE Bestelling_Gerecht (
    id INT PRIMARY KEY AUTO_INCREMENT,
    bestelling INT NOT NULL,
    gerecht INT NOT NULL,
    FOREIGN KEY (bestelling) REFERENCES Bestelling(id),
    FOREIGN KEY (gerecht) REFERENCES Gerecht(id)
);