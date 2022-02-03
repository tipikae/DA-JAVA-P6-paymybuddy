/* Inserting data */
USE `paymybuddy`;

-- -----------------------------------------
-- Table user
-- -----------------------------------------
INSERT INTO `user` (`email`, `firstname`, `lastname`, `password`) VALUES
('alice@alice.com', 'Alice', 'ALICE', '$2y$10$KjnxTMtbUHDlsTl5AocESutLywbeWh1omEvQuCAYR1rNmrelDzvoW'),
('bob@bob.com', 'Bob', 'BOB', '$2y$10$vT5VrEfCbrwtNOVHNLTFQeRPONIjZan9MXHxFSTN8yGxymU9m5IUe'),
('hector@hector.com', 'Hector', 'HECTOR', '$2y$10$13EnXZmZykUqZPweqcwrse0Ec.meN6C4ZiioaWxcktbqTKqmCrfCi');

-- -----------------------------------------
-- Table role
-- -----------------------------------------
INSERT INTO `role` (`role`) VALUES
('USER');


-- -----------------------------------------
-- Table users_roles
-- -----------------------------------------
INSERT INTO `users_roles` (`id_user`, `role`) VALUES
(1, 'USER'),
(2, 'USER'),
(3, 'USER');


-- -----------------------------------------
-- Table account
-- -----------------------------------------
INSERT INTO `account` (`id_user`, `date_created`, `balance`) VALUES
(1, NOW(), 1000.0),
(2, NOW(), 2000.0),
(3, NOW(), 3000.0);


-- -----------------------------------------
-- Table connection
-- -----------------------------------------
INSERT INTO `connection` (`id_user_src`, `id_user_dest`) VALUES
(1, 2),
(1, 3);


-- -----------------------------------------
-- Table operation
-- -----------------------------------------
INSERT INTO `operation` (`number`, `date_operation`, `amount`, `type`, `description`, `fee`, `id_account`, `id_src_connection`, `id_dest_connection`) VALUES
(1, NOW(), 1000.0, 'DEP', 'Deposit from bank account', 0, 1, NULL, NULL),
(2, NOW(), 2000.0, 'DEP', 'Deposit from bank account', 0, 2, NULL, NULL),
(3, NOW(), 3000.0, 'DEP', 'Deposit from bank account', 0, 3, NULL, NULL),
(4, NOW(), 10.0, 'TRA', 'Movie tickets', 0.05, 1, 1, 2),
(5, NOW(), 20.0, 'TRA', 'Restaurant bill share', 0.1, 1, 1, 3);

