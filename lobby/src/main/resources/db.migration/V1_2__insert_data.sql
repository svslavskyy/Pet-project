INSERT INTO currency(currency_code) VALUES ('UAH');
INSERT INTO currency(currency_code) VALUES ('RUB');
INSERT INTO currency(currency_code) VALUES ('USD');
INSERT INTO currency(currency_code) VALUES ('EUR');
INSERT INTO currency(currency_code) VALUES ('CNY');

INSERT INTO country(country_code) VALUES ('UA');
INSERT INTO country(country_code) VALUES ('RU');
INSERT INTO country(country_code) VALUES ('US');
INSERT INTO country(country_code) VALUES ('EU');
INSERT INTO country(country_code) VALUES ('CN');

INSERT INTO game(lobby_game_id) VALUES('playup-happy-bananas');
INSERT INTO game(lobby_game_id) VALUES('playup-slot');
INSERT INTO game(lobby_game_id) VALUES('bestgames-gold-monkey');
INSERT INTO game(lobby_game_id) VALUES('bestgames-slot');
INSERT INTO game(lobby_game_id) VALUES('casino-black-pirate');

INSERT INTO game_currency(game_id, currency_id) VALUES(1,1);
INSERT INTO game_currency(game_id, currency_id) VALUES(1,2);
INSERT INTO game_currency(game_id, currency_id) VALUES(1,3);
INSERT INTO game_currency(game_id, currency_id) VALUES(2,1);
INSERT INTO game_currency(game_id, currency_id) VALUES(2,3);
INSERT INTO game_currency(game_id, currency_id) VALUES(3,3);
INSERT INTO game_currency(game_id, currency_id) VALUES(4,1);
INSERT INTO game_currency(game_id, currency_id) VALUES(4,4);
INSERT INTO game_currency(game_id, currency_id) VALUES(4,5);
INSERT INTO game_currency(game_id, currency_id) VALUES(5,2);
INSERT INTO game_currency(game_id, currency_id) VALUES(5,4);

INSERT INTO game_country(game_id, country_id) VALUES(1,1);
INSERT INTO game_country(game_id, country_id) VALUES(1,2);
INSERT INTO game_country(game_id, country_id) VALUES(1,3);
INSERT INTO game_country(game_id, country_id) VALUES(2,1);
INSERT INTO game_country(game_id, country_id) VALUES(2,3);
INSERT INTO game_country(game_id, country_id) VALUES(3,3);
INSERT INTO game_country(game_id, country_id) VALUES(4,1);
INSERT INTO game_country(game_id, country_id) VALUES(4,4);
INSERT INTO game_country(game_id, country_id) VALUES(4,5);
INSERT INTO game_country(game_id, country_id) VALUES(5,2);
INSERT INTO game_country(game_id, country_id) VALUES(5,4);

