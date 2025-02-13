-- Create Database (Comando executado no Workbench do MySQL)
-- CREATE DATABASE db_projedata

-- Tabela Funcionários
CREATE TABLE funcionarios (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    data_nascimento DATE NOT NULL,
    salario DECIMAL(9,2) NOT NULL,
    funcao VARCHAR(100) NOT NULL,
    is_active TINYINT NOT NULL,

    PRIMARY KEY (id)
)