create schema transporte;


-- CRIANDO TABELA PASSAGEIRO
create table if not exists transporte.TB_PASSAGEIRO(
	ID serial primary key,
	NOME varchar(255) not null CHECK (NOME <> ''),
	CPF varchar(11) not null,
	DATA_NASCIMENTO date not null,
	unique(CPF)
);


-- CRIANDO TABELA CARTAO
create type transporte.tipo_passageiro as enum('PASSE ESCOLAR', 'PASSE SENIOR', 'VALE TRANSPORTE', 'PASSE LIVRE', 'PASSE ESPECIAL');
create table if not exists transporte.TB_CARTAO(
	ID serial primary key,
	NUMERO integer not null,
	TIPO_PASSAGEIRO transporte.tipo_passageiro not null,
	DATA_ATIVACAO date not null default now()::date,
	DATA_VALIDADE date not null default (now() + interval '1 year')::date,
	FK_PASSAGEIRO integer not null references transporte.TB_PASSAGEIRO(ID) on delete cascade,
	unique(NUMERO)
);

-- CRIANDO TABELA BILHETE
create table if not exists transporte.TB_BILHETE(
	ID serial primary key,
	CODIGO UUID not null default gen_random_uuid(),
	DATA_GERACAO date not null default now()::date,
	UTILIZADO boolean not null default false
);

-- CRIANDO TABELA TIPO_PASSAGEM
create table if not exists transporte.TB_TIPO_PASSAGEM(
	ID serial primary key,
	FK_CARTAO integer references transporte.TB_CARTAO(ID) on delete cascade,
	FK_BILHETE integer references transporte.TB_BILHETE(ID) on delete cascade
	CONSTRAINT UM_TIPO CHECK (
        (FK_BILHETE IS NOT NULL AND FK_CARTAO IS NULL) OR
        (FK_BILHETE IS NULL AND FK_CARTAO IS NOT NULL)
    )
);


-- CRIANDO TABELA LINHA
create table if not exists transporte.TB_LINHA(
	ID serial primary key,
	NOME varchar(255) not null CHECK (NOME <> ''),
	NUMERO integer not null,
	unique(NUMERO)
);


-- CRIANDO TABELA ESTACAO
create table if not exists transporte.TB_ESTACAO(
	ID serial primary key,
	NOME varchar(255) not null check (NOME <> ''),
	HORARIO_ABERTURA time not null default '04:00'::time,
	HORARIO_FECHAMENTO time not null default '00:00'::time
);

-- CRIANDO TABELA CONEXAO
create table if not exists transporte.TB_CONEXAO(
	ID serial primary key,
	FK_LINHA integer not null references transporte.TB_LINHA(ID) on delete cascade,
	FK_ESTACAO integer not null references transporte.TB_ESTACAO(ID) on delete cascade
);


-- CRIANDO TABELA VIAGEM
create table if not exists transporte.TB_VIAGEM(
	ID serial primary key,
	FK_TIPO_PASSAGEM integer not null references transporte.TB_TIPO_PASSAGEM(ID) on delete cascade,
	FK_ESTACAO integer not null references transporte.TB_ESTACAO(ID) on delete cascade
);


-- CRIANDO TABELA ESTADO
create table if not exists transporte.TB_ESTADO(
	ID serial primary key,
	NOME varchar(30) not null check (NOME <> ''),
	SIGLA varchar(2) not null check (SIGLA <> '')
);


-- CRIANDO TABELA CIDADE
create table if not exists transporte.TB_CIDADE(
	ID serial primary key,
	NOME varchar(60) not null check (NOME <> ''),
	FK_ESTADO integer not null references transporte.TB_ESTADO(ID) on delete cascade
);


-- CRIANDO TABELA BAIRRO
create table if not exists transporte.TB_BAIRRO(
	ID serial primary key,
	NOME varchar(60) not null check (NOME <> ''),
	FK_CIDADE integer not null references transporte.TB_CIDADE(ID) on delete cascade
);


-- CRIANDO TABELA ENDERECO
create table if not exists transporte.TB_ENDERECO(
	ID serial primary key,
	LOGRADOURO varchar(100) not null check (LOGRADOURO <> ''),
	NUMERO integer not null,
	COMPLEMENTO varchar(100) check (LOGRADOURO <> ''),
	FK_ESTACAO integer not null references transporte.TB_ESTACAO(ID) on delete cascade,
	FK_BAIRRO integer not null references transporte.TB_BAIRRO(ID) on delete cascade
);


