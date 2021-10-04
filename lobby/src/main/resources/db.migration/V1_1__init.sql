CREATE TABLE IF NOT EXISTS country
(
    ID SERIAL PRIMARY KEY,
    country_code character varying(2) NOT NULL
);

CREATE TABLE IF NOT EXISTS currency
(
    ID SERIAL PRIMARY KEY,
    currency_code character varying(3) NOT NULL
);

CREATE TABLE IF NOT EXISTS game
(
    ID BIGSERIAL PRIMARY KEY,
    lobby_game_id character varying(255) NOT NULL,
    is_blocked boolean default false
);

CREATE TABLE IF NOT EXISTS game_currency
(
    game_id bigint NOT NULL,
    currency_id integer NOT NULL
);

CREATE TABLE IF NOT EXISTS game_country
(
    game_id bigint NOT NULL,
    country_id integer NOT NULL
);

ALTER TABLE game_currency
    ADD FOREIGN KEY(game_id)
    REFERENCES game(ID);

ALTER TABLE game_currency
    ADD FOREIGN KEY(currency_id)
    REFERENCES currency(ID);

ALTER TABLE game_country
    ADD FOREIGN KEY(game_id)
    REFERENCES game(ID);

ALTER TABLE game_country
    ADD FOREIGN KEY(country_id)
    REFERENCES country(ID);

CREATE INDEX index_lobby_game_id_title
    ON game(lobby_game_id);

