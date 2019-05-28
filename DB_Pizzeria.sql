CREATE SCHEMA `PIZZERIA` ;
CREATE TABLE `PIZZERIA`.`Pizze` (
  `nome` VARCHAR(45),
  `ingrediente` VARCHAR(45),
  `prezzo` INT NULL,
  PRIMARY KEY (`nome`));
