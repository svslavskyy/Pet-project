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

CREATE TABLE IF NOT EXISTS player
(
    ID BIGSERIAL PRIMARY KEY,
    email character varying(50) UNIQUE NOT NULL,
    display_name character varying(50) NOT NULL,
    password character varying(255) NOT NULL,
    currency_id integer NOT NULL,
    country_id integer NOT NULL,
    is_blocked boolean default false
);

ALTER TABLE player
    ADD FOREIGN KEY(currency_id)
    REFERENCES currency(ID);

ALTER TABLE player
    ADD FOREIGN KEY(country_id)
    REFERENCES country(ID);

CREATE INDEX index_player_name
    ON player(display_name);

CREATE INDEX index_player_currency
    ON player(currency_id);