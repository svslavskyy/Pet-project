CREATE TABLE IF NOT EXISTS provider
(
    ID SERIAL PRIMARY KEY,
    provider_name character varying(255) NOT NULL,
    provider_url character varying(255) NOT NULL,
    is_blocked boolean default false
);

CREATE TABLE IF NOT EXISTS game 
(
    ID BIGSERIAL PRIMARY KEY,
    internal_id character varying(255) NOT NULL,
    external_id character varying(255) NOT NULL,
    provider_id integer NOT NULL,
    is_blocked boolean default false
);

ALTER TABLE game
    ADD FOREIGN KEY(provider_id)
    REFERENCES provider(ID);

CREATE INDEX index_provider_name
    ON provider(provider_name);

CREATE INDEX index_game_title
    ON game(internal_id);