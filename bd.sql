create database desafio_latam;

CREATE TABLE `desafio_latam`.`BOOK_TBL` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  `author` VARCHAR(45) NULL,
  `price` DOUBLE NULL,
  PRIMARY KEY (`id`));
