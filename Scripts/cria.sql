create schema transporte;

-- CRIANDO TABELA USUARIO
create table if not exists transporte.USUARIO(
	ID_USUARIO serial primary key,
	NOME_USUARIO varchar(255) not null CHECK (NOME_USUARIO <> ''),
	CPF integer not null,
	DATA_NASCIMENTO date not null,
	unique(CPF)
);


-- CRIANDO TABELA CARTAO
create type transporte.tipo_passageiro as enum('PASSE ESCOLAR', 'PASSE SENIOR', 'VALE TRANSPORTE', 'PASSE LIVRE', 'PASSE ESPECIAL');
create table if not exists transporte.CARTAO(
	ID_CARTAO serial primary key,
	NUMERO_CARTAO integer not null,
	TIPO_PASSAGEIRO transporte.tipo_passageiro not null,
	DATA_ATIVACAO date not null default now()::date,
	DATA_VALIDADE date not null default (now() + interval '1 year')::date,
	SALDO numeric(10,2) not null default 0.00,
	ID_USUARIO integer not null references transporte.USUARIO(ID_USUARIO) on delete cascade,
	unique(NUMERO_CARTAO)
);


-- CRIANDO TABELA CIDADE
create type transporte.zona_sp as enum('ZONA SUL', 'ZONA NORTE', 'ZONA LESTE', 'ZONA OESTE');
create table if not exists transporte.CIDADE(
	ID_CIDADE serial primary key,
	NOME_CIDADE varchar(255) not null CHECK (NOME_CIDADE <> ''),
	NUMERO_POPULACIONAL integer not null,
	ZONA_SP transporte.zona_sp not null
);


-- CRIANDO TABELA LINHA
create table if not exists transporte.LINHA(
	ID_LINHA serial primary key,
	NOME_LINHA varchar(255) not null CHECK (NOME_LINHA <> ''),
	NUMERO_LINHA integer not null,
	CONEXAO_LINHA boolean not null default false,
	unique(NUMERO_LINHA)
);


-- CRIANDO TABELA FEEDBACK
create table if not exists transporte.FEEDBACK(
	ID_FEEDBACK serial primary key,
	COMENTARIO varchar(500) not null CHECK (COMENTARIO <> ''),
	DATA_FEEDBACK date not null default now()::date,
	ID_LINHA integer not null references transporte.LINHA(ID_LINHA) on delete cascade,
	ID_USUARIO integer not null references transporte.USUARIO(ID_USUARIO) on delete cascade
);


-- CRIANDO TABELA ESTACAO
create table if not exists transporte.ESTACAO(
	ID_ESTACAO serial primary key,
	NOME_ESTACAO varchar(255) not null check (NOME_ESTACAO <> ''),
	HORARIO_ABERTURA time not null default '04:00'::time,
	HORARIO_FECHAMENTO time not null default '00:00'::time,
	ID_CIDADE integer not null references transporte.CIDADE(ID_CIDADE) on delete cascade,
	ID_LINHA integer not null references transporte.LINHA(ID_LINHA) on delete cascade
);


-- CRIANDO TABELA CARTAO ESTACAO
create table if not exists transporte.CARTAO_ESTACAO(
	ID_CARTAO_ESTACAO serial primary key,
	PRECO numeric(5,2) not null default 4.20,
	ID_CARTAO integer not null references transporte.CARTAO(ID_CARTAO) on delete cascade,
	ID_ESTACAO integer not null references transporte.ESTACAO(ID_ESTACAO) on delete cascade
);


-- CRIANDO TABELA TREM
create table if not exists transporte.TREM(
	ID_TREM serial primary key,
	CODIGO_IDENTIFICACAO varchar(255) not null check (CODIGO_IDENTIFICACAO <> ''),
	CAPACIDADE_PASSAGEIROS integer not null,
	ESTADO_MANUTENCAO boolean not null default false,
	ID_LINHA integer not null references transporte.LINHA(ID_LINHA) on delete cascade
);


-- CRIANDO TABELA FUNCIONARIO
create table if not exists transporte.FUNCIONARIO(
	ID_FUNCIONARIO serial primary key,
	NOME_COMPLETO varchar(255) not null check (NOME_COMPLETO <> ''),
	FUNCAO varchar(255) not null check (NOME_COMPLETO <> ''),
	INICIO_HORA_TRABALHO time not null,
	FIM_HORA_TRABALHO time not null,
	SALARIO numeric(10,2) not null default 1320.00
);


-- CRIANDO TABELA FUNCIONARIO_TREM
create table if not exists transporte.FUNCIONARIO_TREM(
	ID_FUNCIONARIO_TREM serial primary key,
	DATA_UTILIZADO date not null default now()::date,
	ID_FUNCIONARIO integer not null references transporte.FUNCIONARIO(ID_FUNCIONARIO) on delete cascade,
	ID_TREM integer not null references transporte.TREM(ID_TREM) on delete cascade
);


-- CRIANDO TABELA MANUTENCAO
create table if not exists transporte.MANUTENCAO(
	ID_MANUTENCAO serial primary key,
	DATA_INICIO date not null default now()::date,
	DATA_FIM date,
	ID_FUNCIONARIO integer not null references transporte.FUNCIONARIO(ID_FUNCIONARIO) on delete cascade,
	ID_TREM integer not null references transporte.TREM(ID_TREM) on delete cascade
);

