INSERT INTO provider(provider_name,provider_url) VALUES('playup','https://playup.com.ua');
INSERT INTO provider(provider_name,provider_url) VALUES('bestgames','https://bestgames.com');
INSERT INTO provider(provider_name,provider_url) VALUES('casino','http://casino.com');


INSERT INTO game(internal_id, external_id, provider_id) VALUES('playup-happy-bananas', 'happy-bananas',  1);
INSERT INTO game(internal_id, external_id, provider_id) VALUES('playup-slot', 'slot', 1);
INSERT INTO game(internal_id, external_id, provider_id) VALUES('bestgames-gold-monkey', 'gold-monkey', 2);
INSERT INTO game(internal_id, external_id, provider_id) VALUES('bestgames-slot', 'slot', 2);
INSERT INTO game(internal_id, external_id, provider_id) VALUES('bananas-next', 'next', 2);
INSERT INTO game(internal_id, external_id, provider_id) VALUES('casino-black-pirate', 'black-pirate', 3);