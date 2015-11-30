CREATE DATABASE IF NOT EXISTS `test`;
USE `test`;

CREATE TABLE IF NOT EXISTS `user_roles` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) NOT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `UK_182xa1gitcxqhaq6nn3n2kmo3` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

INSERT INTO `user_roles` (`role_id`, `name`) VALUES
	(2, 'admin'),
	(3, 'guard'),
	(1, 'user');
