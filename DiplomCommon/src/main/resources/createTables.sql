CREATE TABLE `news` (
	`id` INT(10) NOT NULL AUTO_INCREMENT,
	`short_text` VARCHAR(1000) NOT NULL,
	`full_text` VARCHAR(5000) NOT NULL,
	`title` VARCHAR(500) NOT NULL,
	`creation_date` TIMESTAMP NOT NULL,
	`modification_date` TIMESTAMP NOT NULL,
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `tag` (
	`id` INT(10) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(100) NULL DEFAULT NULL,
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `author` (
	`id` INT(10) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(100) NULL DEFAULT NULL,
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `comment` (
	`id` INT(10) NOT NULL AUTO_INCREMENT,
	`text` VARCHAR(1000) NOT NULL,
	`creation_date` TIMESTAMP NOT NULL,
	`news_id` INT(10) NULL DEFAULT NULL,
	PRIMARY KEY (`id`),
	INDEX `FK_comment_news` (`news_id`),
	CONSTRAINT `FK_comment_news` FOREIGN KEY (`news_id`) REFERENCES `news` (`id`) ON DELETE CASCADE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `news_author` (
	`news_id` INT(10) NOT NULL,
	`author_id` INT(10) NOT NULL,
	INDEX `FK__news` (`news_id`),
	INDEX `FK__author` (`author_id`),
	CONSTRAINT `FK__news` FOREIGN KEY (`news_id`) REFERENCES `news` (`id`) ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT `FK__author` FOREIGN KEY (`author_id`) REFERENCES `author` (`id`) ON UPDATE RESTRICT ON DELETE RESTRICT
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `news_tag` (
	`news_id` INT(10) NOT NULL,
	`tag_id` INT(10) NOT NULL,
	INDEX `FK_news_tag_news` (`news_id`),
	INDEX `FK_news_tag_tag` (`tag_id`),
	CONSTRAINT `FK_news_tag_news` FOREIGN KEY (`news_id`) REFERENCES `news` (`id`) ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT `FK_news_tag_tag` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON UPDATE RESTRICT ON DELETE RESTRICT
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `user` (
	`login` VARCHAR(50) NOT NULL,
	`password` VARCHAR(50) NULL DEFAULT NULL,
	`id` INT(10) NOT NULL AUTO_INCREMENT,
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `user_role` (
	`id` INT(10) NOT NULL AUTO_INCREMENT,
	`role` VARCHAR(10) NOT NULL,
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;
