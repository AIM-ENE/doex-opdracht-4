create table product (
    id int auto_increment primary key,
    versie int,
    naam varchar(255),
    aantal_centen integer,
    valuta varchar(255)
);

create table bestelling (
    id int auto_increment primary key,
    versie int,

    -- Bestelling -> Geld
    aantal_centen int,
    valuta varchar(255),

    status varchar(255)
);

create table bestelregel (
    bestelling int,
    product int,
    aantal int,

    -- Bestelregel -> Geld
    aantal_centen integer,
    valuta varchar(255),

    -- Bestelling -> Bestelregel
    foreign key (bestelling) references bestelling(id),

    -- Bestelregel -> Product
    foreign key (product) references product(id)
);

