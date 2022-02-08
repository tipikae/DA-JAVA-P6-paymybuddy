/* Setting up DB */
DROP DATABASE IF EXISTS `paymybuddy`;
CREATE DATABASE `paymybuddy` CHARSET = utf8 COLLATE = utf8_general_ci;
USE `paymybuddy`;

-- -----------------------------------------
-- Table user
-- -----------------------------------------
CREATE TABLE `user` (
	`id` integer(11) AUTO_INCREMENT, 
	`email` varchar(50) NOT NULL,
	`firstname` varchar(50) NOT NULL,
	`lastname` varchar(50) NOT NULL,
	`password` varchar(100) NOT NULL,
	CONSTRAINT `user_pk` PRIMARY KEY (`id`),
	CONSTRAINT `user_uq` UNIQUE (`email`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

INSERT INTO `user` (`email`, `firstname`, `lastname`, `password`) VALUES
('alice@alice.com', 'Alice', 'ALICE', '$2y$10$KjnxTMtbUHDlsTl5AocESutLywbeWh1omEvQuCAYR1rNmrelDzvoW'),
('bob@bob.com', 'Bob', 'BOB', '$2y$10$vT5VrEfCbrwtNOVHNLTFQeRPONIjZan9MXHxFSTN8yGxymU9m5IUe'),
('hector@hector.com', 'Hector', 'HECTOR', '$2y$10$13EnXZmZykUqZPweqcwrse0Ec.meN6C4ZiioaWxcktbqTKqmCrfCi');

-- -----------------------------------------
-- Table role
-- -----------------------------------------
CREATE TABLE `role` (
	`role` varchar(10) NOT NULL,
	CONSTRAINT `role_pk` PRIMARY KEY (`role`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

INSERT INTO `role` (`role`) VALUES
('USER');


-- -----------------------------------------
-- Table users_roles
-- -----------------------------------------
CREATE TABLE `users_roles` (
	`id_user` integer(11) NOT NULL,
	`role` varchar(10) NOT NULL,
	CONSTRAINT `users_roles_pk` PRIMARY KEY (`id_user`, `role`),
	CONSTRAINT `user_users_roles_fk` FOREIGN KEY (`id_user`) REFERENCES `user`(`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `role_users_roles_fk` FOREIGN KEY (`role`) REFERENCES `role`(`role`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

INSERT INTO `users_roles` (`id_user`, `role`) VALUES
(1, 'USER'),
(2, 'USER'),
(3, 'USER');


-- -----------------------------------------
-- Table account
-- -----------------------------------------
CREATE TABLE `account` (
	`id_user` integer(11) NOT NULL,
	`date_created` date NOT NULL,
	`balance` decimal(10,2) NOT NULL,
	CONSTRAINT `account_pk` PRIMARY KEY (`id_user`),
	CONSTRAINT `user_account_fk` FOREIGN KEY (`id_user`) REFERENCES `user`(`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

INSERT INTO `account` (`id_user`, `date_created`, `balance`) VALUES
(1, NOW(), 1000.0),
(2, NOW(), 2000.0),
(3, NOW(), 3000.0);


-- -----------------------------------------
-- Table connection
-- -----------------------------------------
CREATE TABLE `connection` (
	`id_user_src` integer(11) NOT NULL,
	`id_user_dest` integer(11) NOT NULL,
	CONSTRAINT `connection_pk` PRIMARY KEY (`id_user_src`, `id_user_dest`),
	CONSTRAINT `src_user_connection_fk` FOREIGN KEY (`id_user_src`) REFERENCES `user`(`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `dest_user_connection_fk` FOREIGN KEY (`id_user_dest`) REFERENCES `user`(`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

INSERT INTO `connection` (`id_user_src`, `id_user_dest`) VALUES
(1, 2),
(1, 3);


-- -----------------------------------------
-- Table operation
-- -----------------------------------------
CREATE TABLE `operation` (
	`number` integer(11) AUTO_INCREMENT,
	`date_operation` date NOT NULL,
	`amount` decimal(10,2) NOT NULL,
	`type` varchar(3) NOT NULL,
	`description` varchar(128) DEFAULT NULL,
	`fee` decimal(10,2) DEFAULT 0,
	`id_account` integer(11) NOT NULL,
	`id_src_connection` integer(11) DEFAULT NULL,
	`id_dest_connection` integer(11) DEFAULT NULL,
	CONSTRAINT `operation_pk` PRIMARY KEY (`number`),
	CONSTRAINT `account_operation_fk` FOREIGN KEY (`id_account`) REFERENCES `account`(`id_user`) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `src_connection_operation_fk` FOREIGN KEY (`id_src_connection`) REFERENCES `connection`(`id_user_src`) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `dest_connection_operation_fk` FOREIGN KEY (`id_dest_connection`) REFERENCES `connection`(`id_user_dest`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

INSERT INTO `operation` (`number`, `date_operation`, `amount`, `type`, `description`, `fee`, `id_account`, `id_src_connection`, `id_dest_connection`) VALUES
(1, NOW(), 1000.0, 'DEP', 'Deposit from bank account', 0, 1, NULL, NULL),
(2, NOW(), 2000.0, 'DEP', 'Deposit from bank account', 0, 2, NULL, NULL),
(3, NOW(), 3000.0, 'DEP', 'Deposit from bank account', 0, 3, NULL, NULL),
(4, NOW(), 10.0, 'TRA', 'Movie tickets', 0.05, 1, 1, 2),
(5, NOW(), 20.0, 'TRA', 'Restaurant bill share', 0.1, 1, 1, 3);

