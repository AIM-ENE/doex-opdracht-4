create table Ensemble (
    id int auto_increment primary key,
    name varchar(255)
);

create table Member (
    id int auto_increment primary key,
    mail_address varchar(255),
    name varchar(255)
);

create table Participant(
    ensemble int,
    member int,
    foreign key (ensemble) references Ensemble(id),
    foreign key (member) references Member(id)
);


