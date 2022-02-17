INSERT INTO console (id, name, price, quantity, rating, release_year) VALUES (1, "PS5", 499, 12, 4.8, "2020-11-19");
INSERT INTO console (id, name, price, quantity, rating, release_year) VALUES (2, "Xbox Series X", 499, 85, 4.5, "2020-11-10");
INSERT INTO console (id, name, price, quantity, rating, release_year) VALUES (3, "PS4", 199, 545, 4.3, "2013-11-15");
INSERT INTO console (id, name, price, quantity, rating, release_year) VALUES (4, "Xbox One", 199, 660, 4.2, "2013-11-22");
INSERT INTO console (id, name, price, quantity, rating, release_year) VALUES (5, "Nintendo Switch", 269, 1980, 4.3, "2017-03-03");


INSERT INTO game (id, name, price, quantity, rating, release_year, offline_players_number, online_players_number)
VALUES (6, "Call Of Duty : Vanguard", 70, 1870, 3.9, "2021-11-05", 2, 28);
INSERT INTO game (id, name, price, quantity, rating, release_year, offline_players_number, online_players_number)
VALUES (7, "Assassin's Creed Valhalla", 35, 2568, 4.1, "2020-11-10", 1, 1);
INSERT INTO game (id, name, price, quantity, rating, release_year, offline_players_number, online_players_number)
VALUES (8, "Légendes Pokémon : Arceus", 50, 4400, 4.5, "2022-01-28", 1, 2);

INSERT INTO games_on_consoles
VALUES (6,1), (6,2), (6,3), (6,4), (7,1), (7,2), (7,3), (7,4), (8,5);