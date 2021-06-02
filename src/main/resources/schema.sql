CREATE TABLE country (
    id   INTEGER      NOT NULL AUTO_INCREMENT,
    name VARCHAR(128) NOT NULL,
    PRIMARY KEY (id)
);

create table ROOM
(
    ID INTEGER auto_increment
        primary key,
    PRICE VARCHAR(10) not null,
    ROOM_NUMBER INTEGER not null
);

create table RESERVATION
(
    ID INTEGER auto_increment
        primary key,
    CHECKIN DATE not null ,
    CHECKOUT DATE not null ,
    BOOKED_ROOM_ID INTEGER not null ,
    constraint reservation_fk_room_id
        foreign key (BOOKED_ROOM_ID) references ROOM (ID)
);