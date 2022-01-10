CREATE TABLE user_role(
    "user" varchar(255) REFERENCES "user"(id),
    "role" varchar(255) REFERENCES role(id),
    PRIMARY KEY("user", role)
)
