CREATE TABLE `user` (
	`login` VARCHAR(50) NOT NULL,
	`password` VARCHAR(50) NULL DEFAULT NULL,
	`id` VARCHAR(50) NOT NULL DEFAULT '',
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `tag` (
	`id` INT(10) NOT NULL DEFAULT '0',
	`name` VARCHAR(50) NULL DEFAULT NULL,
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `author` (
	`id` INT(10) NOT NULL DEFAULT '0',
	`name` VARCHAR(50) NULL DEFAULT NULL,
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `comment` (
	`id` INT(10) NOT NULL DEFAULT '0',
	`text` INT(10) NULL DEFAULT NULL,
	`creation_date` INT(10) NULL DEFAULT NULL,
	`news_id` INT(10) NULL DEFAULT NULL,
	PRIMARY KEY (`id`),
	INDEX `FK_comment_news` (`news_id`),
	CONSTRAINT `FK_comment_news` FOREIGN KEY (`news_id`) REFERENCES `news` (`id`) ON DELETE CASCADE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `news_author` (
	`news_id` INT(10) NULL DEFAULT NULL,
	`author_id` INT(10) NULL DEFAULT NULL,
	INDEX `FK__news` (`news_id`),
	INDEX `FK__author` (`author_id`),
	CONSTRAINT `FK__author` FOREIGN KEY (`author_id`) REFERENCES `author` (`id`) ON DELETE CASCADE,
	CONSTRAINT `FK__news` FOREIGN KEY (`news_id`) REFERENCES `news` (`id`) ON DELETE CASCADE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `news_tag` (
	`news_id` INT(10) NULL DEFAULT NULL,
	`tag_id` INT(10) NULL DEFAULT NULL,
	INDEX `FK_news_tag_tag` (`tag_id`),
	INDEX `FK_news_tag_news` (`news_id`),
	CONSTRAINT `FK_news_tag_news` FOREIGN KEY (`news_id`) REFERENCES `news` (`id`),
	CONSTRAINT `FK_news_tag_tag` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE CASCADE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `user` (
	`login` VARCHAR(50) NOT NULL,
	`password` VARCHAR(50) NULL DEFAULT NULL,
	`id` VARCHAR(50) NOT NULL DEFAULT '',
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `user_role` (
	`id` INT(10) NOT NULL,
	`role` VARCHAR(10) NOT NULL
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;
