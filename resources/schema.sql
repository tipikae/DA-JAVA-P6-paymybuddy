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

-- -----------------------------------------
-- Table role
-- -----------------------------------------
CREATE TABLE `role` (
	`role` varchar(10) NOT NULL,
	CONSTRAINT `role_pk` PRIMARY KEY (`role`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;


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

