CREATE TABLE user_role(
    id_user_id varchar(255) REFERENCES "user"(id),
    id_role_id varchar(255) REFERENCES role(id),
    PRIMARY KEY(id_user_id, id_role_id)
)
