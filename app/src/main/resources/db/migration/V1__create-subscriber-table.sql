CREATE TABLE subscriber(
    id varchar(255) primary key,
    name varchar(255) null,
    email varchar(255) not null,
    status varchar(255) not null
);
CREATE TABLE newsletter(
    id varchar(255) primary key,
    name varchar(255) null
)