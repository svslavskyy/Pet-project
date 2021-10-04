CREATE TABLE IF NOT EXISTS session
(
    ID BIGSERIAL PRIMARY KEY,
    session_key character varying(255) UNIQUE NOT NULL,
    player_id bigint NOT NULL,
    creation_timestamp timestamp without time zone NOT NULL,
    expiration_timestamp timestamp without time zone NOT NULL
);

ALTER TABLE session
    ADD FOREIGN KEY(player_id)
    REFERENCES player(ID);