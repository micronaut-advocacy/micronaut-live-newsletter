CREATE TABLE role(
    id varchar(255) primary key,
    authority varchar(255) null
);
create unique index role_authority_uindex on role (authority);
