
CREATE DATABASE ArteMotos;
use ArteMotos;

-- -----------------------------------------------------
-- Tabela Usuario
-- -----------------------------------------------------
CREATE TABLE Usuario (
  idUsuario INT NOT NULL AUTO_INCREMENT,
  login VARCHAR(12) NOT NULL,
  senha VARCHAR(12) NOT NULL,
  PRIMARY KEY (idUsuario));


-- -----------------------------------------------------
-- Tabela Peca
-- -----------------------------------------------------
CREATE TABLE Peca (
  idPeca INT NOT NULL AUTO_INCREMENT,
  nomePeca VARCHAR(45) NOT NULL,
  descricao VARCHAR(100) NULL,
   valor  FLOAT NOT NULL,
  PRIMARY KEY ( idPeca ));


-- -----------------------------------------------------
-- Tabela Cliente
-- -----------------------------------------------------
CREATE TABLE  Cliente (
  idCliente INT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(60) NOT NULL,
  tipo VARCHAR(45) NULL,
  documento VARCHAR(45) NULL,
  DDD VARCHAR(4) NULL,
  telefone VARCHAR(45) NULL,
  email VARCHAR(45) NULL,
  rua VARCHAR(100) NULL,
  numero VARCHAR(10) NULL,
  cep VARCHAR(45) NULL,
  cidade VARCHAR(45) NULL,
  estado VARCHAR(45) NULL,
  PRIMARY KEY (idCliente));


-- -----------------------------------------------------
-- Table Venda
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Venda (
  idVenda INT primary key not NULL AUTO_INCREMENT,
  nomeCliente VARCHAR(100) NOT NULL,
  documento VARCHAR(45) NULL,
  nomePeca VARCHAR(45) NULL,
  valorPeca FLOAT NULL,
  quantidade INT NULL,
  dataVenda DATE NULL,
  valorTotal FLOAT NULL,
  idUsuario INT,
  idPeca INT,
  idCliente INT,
  CONSTRAINT fk_Venda_Usuario
    FOREIGN KEY (idUsuario)
    REFERENCES Usuario (idUsuario)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT fk_Venda_Peca1
    FOREIGN KEY (idPeca)
    REFERENCES Peca (idPeca)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT fk_Venda_Cliente1
    FOREIGN KEY (idCliente)
    REFERENCES Cliente (idCliente)
    ON DELETE SET NULL
    ON UPDATE CASCADE);