CREATE TABLE Restaurant(
    id INT PRIMARY KEY AUTO_INCREMENT,
    versie BIGINT
);

CREATE TABLE Gerecht (
    id INT PRIMARY KEY AUTO_INCREMENT,
    restaurant INT NOT NULL,
    naam VARCHAR(255) NOT NULL,
    prijs DECIMAL(10, 2) NOT NULL,
    gang ENUM('VOORGERECHT', 'HOOFDGERECHT', 'DESSERT', 'BIJGERECHT'),
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
    PRIMARY KEY (gerecht, ingredient),
    FOREIGN KEY (gerecht) REFERENCES Gerecht(id),
    FOREIGN KEY (ingredient) REFERENCES Ingredient(id)
);

CREATE TABLE Winkelmand (
    id INT PRIMARY KEY AUTO_INCREMENT,
    restaurant INT NOT NULL,
    FOREIGN KEY (restaurant) REFERENCES Restaurant(id)
);

CREATE TABLE Winkelmand_Regel (
    volgnummer INT NOT NULL,
    winkelmand INT NOT NULL,
    gerecht INT NOT NULL,
    PRIMARY KEY (winkelmand, gerecht, volgnummer),
    FOREIGN KEY (winkelmand) REFERENCES Winkelmand(id),
    FOREIGN KEY (gerecht) REFERENCES Gerecht(id)
);

CREATE TABLE Tafel (
    id INT PRIMARY KEY AUTO_INCREMENT,
    tafel_nummer INT NOT NULL,
    rekening DOUBLE,
    restaurant INT NOT NULL,
    UNIQUE (restaurant, tafel_nummer),
    FOREIGN KEY (restaurant) REFERENCES Restaurant(id)
);

CREATE TABLE Bestelling (
    -- ID is nodig vanwege geen support voor composite keys in Spring Data JDBC:
    -- https://github.com/spring-projects/spring-data-relational/issues/574
    id INT PRIMARY KEY AUTO_INCREMENT,
    bestelnummer INT NOT NULL,
    status ENUM('OPEN', 'BEREIDEN', 'SERVEREN', 'BEZORGD') NOT NULL,
    tafel INT NOT NULL,
    FOREIGN KEY (tafel) REFERENCES Tafel(id)
);

CREATE TABLE Bestel_Regel (
    bestelling INT NOT NULL,
    gerecht INT NOT NULL,
    aantal INT NOT NULL,
    prijs DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (bestelling, gerecht),
    FOREIGN KEY (bestelling) REFERENCES Bestelling(id),
    FOREIGN KEY (gerecht) REFERENCES Gerecht(id)
);