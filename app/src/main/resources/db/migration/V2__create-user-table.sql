CREATE TABLE "user"(
    id varchar(255) primary key,
    email varchar(255) null,
    password varchar(255) not null,
    enabled BOOLEAN not null,
    account_expired BOOLEAN not null,
    account_locked BOOLEAN not null,
    password_expired BOOLEAN not null
);
create unique index user_email_uindex on "user"(email);

