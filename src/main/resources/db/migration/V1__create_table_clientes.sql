-- DROP TABLE CLIENTE CASCADE CONSTRAINTS;
-- DROP TABLE ESPECIFICACAO CASCADE CONSTRAINTS;
-- DROP TABLE ATENDENTE CASCADE CONSTRAINTS;
-- DROP TABLE SCRIPT CASCADE CONSTRAINTS;
-- DROP TABLE PLANO CASCADE CONSTRAINTS;
-- DROP TABLE VENDA CASCADE CONSTRAINTS;

CREATE TABLE ATENDENTE(
    id_atendente INTEGER PRIMARY KEY,
    nome_atendente VARCHAR(50),
    email_atendente VARCHAR(50),
    cpf_atendente VARCHAR(11),
    setor VARCHAR(20),
    senha VARCHAR,
    perfil_atendente VARCHAR(30)
);

CREATE TABLE PLANO(
    nome_plano VARCHAR(30),
    id_plano INTEGER PRIMARY KEY,
    descricao_plano VARCHAR(100),
    valor_plano NUMBER(10,2)
);

CREATE TABLE SCRIPT(
    id_script INTEGER PRIMARY KEY,
    descricao_script CLOB,
    id_plano INTEGER,
    FOREIGN KEY (id_plano) REFERENCES PLANO(id_plano) ON DELETE SET NULL
);

CREATE TABLE CLIENTE(
    id_cliente INTEGER PRIMARY KEY,
    nome_cliente VARCHAR(50),
    cpf_cliente VARCHAR(11),
    dt_nascimento DATE,
    genero VARCHAR(10),
    cep CHAR(8),
    telefone VARCHAR(11),
    email VARCHAR(50),
    perfil_cliente VARCHAR(30)
);

CREATE TABLE ESPECIFICACAO(
    id_especificacao INTEGER PRIMARY KEY,
    tipo_cartao_credito VARCHAR(10),
    gasto_mensal NUMBER(10,2),
    viaja_frequentemente NUMBER(1),
    interesses VARCHAR(50),
    profissao VARCHAR(30), 
    renda_mensal NUMBER(10,2),
    dependentes NUMBER(1),
    id_cliente INTEGER,
    FOREIGN KEY (id_cliente) REFERENCES CLIENTE(id_cliente) ON DELETE CASCADE
);

CREATE TABLE VENDA(
    id_venda INTEGER PRIMARY KEY,
    id_atendente INTEGER,
    id_cliente INTEGER,
    id_script INTEGER,
    id_plano INTEGER,
    FOREIGN KEY (id_atendente) REFERENCES ATENDENTE(id_atendente) ON DELETE SET NULL,
    FOREIGN KEY (id_cliente) REFERENCES CLIENTE(id_cliente) ON DELETE SET NULL,
    FOREIGN KEY (id_script) REFERENCES SCRIPT(id_script) ON DELETE SET NULL,
    FOREIGN KEY (id_plano) REFERENCES PLANO(id_plano) ON DELETE SET NULL
);

--Sequences for table IDs
CREATE SEQUENCE ATENDENTE_SEQ  START WITH 22 INCREMENT BY 50;
CREATE SEQUENCE PLANO_SEQ START WITH 22 INCREMENT BY 50;
CREATE SEQUENCE SCRIPT_SEQ START WITH 22 INCREMENT BY 50;
CREATE SEQUENCE CLIENTE_SEQ START WITH 22 INCREMENT BY 50;
CREATE SEQUENCE ESPECIFICACAO_SEQ START WITH 22 INCREMENT BY 50;
CREATE SEQUENCE VENDA_SEQ START WITH 22 INCREMENT BY 50;

-- Inserindo dados

INSERT INTO ATENDENTE (id_atendente, nome_atendente, email_atendente, cpf_atendente, setor, senha, perfil_atendente)
VALUES (11, 'Marcos Garrido','marcos@gmail.com' , '38387788805', 'Comercial', '$2a$12$Ij5EUFMEeF/x8i/JwpEk0uwF6LLvl5Qgk8NN0hl7nwL2JMpNrMgfO', 'Regular'); --'pass123'
INSERT INTO ATENDENTE (id_atendente, nome_atendente, email_atendente, cpf_atendente, setor, senha, perfil_atendente)
VALUES (12, 'Izabelly Oliveira','izabelly@gmail.com' , '50958416818', 'Comercial', '$2a$12$0MDnFmSxvUvfXnCL/gfZSO51a1C/Va3zkpMO01nzeuuLzjlK06jG6', 'Regular'); --'pass234'

INSERT INTO PLANO (nome_plano, id_plano, descricao_plano, valor_plano)
VALUES ('Premium', 1, 'Pacote premium,', 50.00);
INSERT INTO PLANO (nome_plano, id_plano, descricao_plano, valor_plano)
VALUES ('Basic', 2, 'Pacote Basico,', 20.00);

INSERT INTO SCRIPT (id_script, descricao_script, id_plano)
VALUES (1000, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 1);
INSERT INTO SCRIPT (id_script, descricao_script, id_plano)
VALUES (2000, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 2);

-- INSERT INTO CLIENTE (id_cliente, nome_cliente, cpf_cliente, dt_nascimento, genero, cep, telefone, email, perfil_cliente)
-- VALUES (1, 'Gabriel Cirillo', '234567891', TO_DATE('06-04-2004', 'DD-MM-YYYY'), 'Masculino', '09123380', '11979980903', 'cirilo@gmail.com', 'Influencia');
-- INSERT INTO CLIENTE (id_cliente, nome_cliente, cpf_cliente, dt_nascimento, genero, cep, telefone, email, perfil_cliente)
-- VALUES (2, 'Anna Soares', '45678921', TO_DATE('06-04-2000', 'DD-MM-YYYY'), 'Feminino', '09080360', '11961674481', 'anna@gmail.com', 'Dominancia');

INSERT INTO CLIENTE (id_cliente, nome_cliente, cpf_cliente, dt_nascimento, genero, cep, telefone, email, perfil_cliente)
VALUES ('1','Gabriel Cirillo', '23456789123', '2004-06-04', 'Masculino', '09123380', '11979980903', 'cirilo@gmail.com', 'Influencia');
INSERT INTO CLIENTE (id_cliente, nome_cliente, cpf_cliente, dt_nascimento, genero, cep, telefone, email, perfil_cliente)
VALUES ('2','Anna Soares', '4567892123', '2000-06-04', 'Feminino', '09080360', '11961674481', 'anna@gmail.com', 'Dominancia');


INSERT INTO ESPECIFICACAO (id_especificacao, tipo_cartao_credito, gasto_mensal, viaja_frequentemente, interesses, profissao, renda_mensal, dependentes, id_cliente)
VALUES (105, 'Black', 20000.00, 1, 'Jogos, Shows, restaurantes, viagens', 'Desenvolvedor Java', 30000.00, 2, 1);
INSERT INTO ESPECIFICACAO (id_especificacao, tipo_cartao_credito, gasto_mensal, viaja_frequentemente, interesses, profissao, renda_mensal, dependentes, id_cliente)
VALUES (108, 'Silver', 2000.00, 0, 'Shows, Livros, Academia', 'Desenvolvedora Front', 5000.00, 1, 2);

INSERT INTO VENDA (id_venda, id_atendente, id_cliente, id_script, id_plano)
VALUES (101, 11, 1, 1000, 1);
INSERT INTO VENDA (id_venda, id_atendente, id_cliente, id_script, id_plano)
VALUES (202, 12, 2, 2000, 2);