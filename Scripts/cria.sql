create schema transporte;

-- CRIANDO TABELA USUARIO
create table if not exists transporte.T_TPU_USUARIO(
	ID_USUARIO serial primary key,
	NOME_USUARIO varchar(255) not null CHECK (NOME_USUARIO <> '')
);

-- CRIANDO TABELA FUNCIONARIO
create table if not exists transporte.T_TPU_FUNCIONARIO(
	ID_FUNCIONARIO serial primary key,
	CODIGO integer not null,
	FUNCAO varchar(255) not null check (FUNCAO <> ''),
	SALARIO numeric(10,2) not null default 1320.00,
	INICIO_HORA_TRABALHO time not null,
	FIM_HORA_TRABALHO time not null,
	ID_USUARIO integer not null references transporte.T_TPU_USUARIO(ID_USUARIO) on delete cascade
);

-- CRIANDO TABELA PASSAGEIRO
create table if not exists transporte.T_TPU_PASSAGEIRO(
	ID_PASSAGEIRO serial primary key,
	CPF integer not null,
	DATA_NASCIMENTO date not null,
	ID_USUARIO integer not null references transporte.T_TPU_USUARIO(ID_USUARIO) on delete cascade,
	unique(CPF)
);


-- CRIANDO TABELA CARTAO
create type transporte.tipo_passageiro as enum('PASSE ESCOLAR', 'PASSE SENIOR', 'VALE TRANSPORTE', 'PASSE LIVRE', 'PASSE ESPECIAL');
create table if not exists transporte.T_TPU_CARTAO(
	ID_CARTAO serial primary key,
	NUMERO integer not null,
	TIPO_PASSAGEIRO transporte.tipo_passageiro not null,
	DATA_ATIVACAO date not null default now()::date,
	DATA_VALIDADE date not null default (now() + interval '1 year')::date,
	SALDO numeric(10,2) not null default 0.00,
	ID_PASSAGEIRO integer not null references transporte.T_TPU_PASSAGEIRO(ID_PASSAGEIRO) on delete cascade,
	unique(NUMERO)
);

-- CRIANDO TABELA COMPRA_BILHETE
create table if not exists transporte.T_TPU_COMPRA_BILHETE(
	ID_BILHETE serial primary key,
	CODIGO UUID not null default gen_random_uuid(),
	DATA_GERACAO date not null default now()::date,
	UTILIZADO boolean not null default false
);

-- CRIANDO TABELA TIPO_PASSAGEM
create table if not exists transporte.T_TPU_TIPO_PASSAGEM(
	ID_TIPO_PASSAGEM serial primary key,
	ID_CARTAO integer references transporte.T_TPU_PASSAGEIRO(ID_PASSAGEIRO) on delete cascade,
	ID_BILHETE integer references transporte.T_TPU_COMPRA_BILHETE(ID_BILHETE) on delete cascade
	CONSTRAINT UM_TIPO CHECK (
        (ID_BILHETE IS NOT NULL AND ID_CARTAO IS NULL) OR
        (ID_BILHETE IS NULL AND ID_CARTAO IS NOT NULL)
    )
);


-- CRIANDO TABELA CIDADE
create type transporte.zona_sp as enum('ZONA SUL', 'ZONA NORTE', 'ZONA LESTE', 'ZONA OESTE');
create table if not exists transporte.T_TPU_CIDADE(
	ID_CIDADE serial primary key,
	NOME varchar(255) not null CHECK (NOME <> ''),
	NUMERO_POPULACIONAL integer not null,
	ZONA_SP transporte.zona_sp not null
);


-- CRIANDO TABELA LINHA
create table if not exists transporte.T_TPU_LINHA(
	ID_LINHA serial primary key,
	NOME varchar(255) not null CHECK (NOME <> ''),
	NUMERO integer not null,
	CONEXAO boolean not null default false,
	unique(NUMERO)
);


-- CRIANDO TABELA ESTACAO
create table if not exists transporte.T_TPU_ESTACAO(
	ID_ESTACAO serial primary key,
	NOME varchar(255) not null check (NOME <> ''),
	HORARIO_ABERTURA time not null default '04:00'::time,
	HORARIO_FECHAMENTO time not null default '00:00'::time,
	ID_CIDADE integer not null references transporte.T_TPU_CIDADE(ID_CIDADE) on delete cascade,
	ID_LINHA integer not null references transporte.T_TPU_LINHA(ID_LINHA) on delete cascade
);


-- CRIANDO TABELA VIAGEM
create table if not exists transporte.T_TPU_VIAGEM(
	ID_VIAGEM serial primary key,
	PRECO numeric(5,2) not null default 4.20,
	ID_TIPO_PASSAGEM integer not null references transporte.T_TPU_TIPO_PASSAGEM(ID_TIPO_PASSAGEM) on delete cascade,
	ID_ESTACAO integer not null references transporte.T_TPU_ESTACAO(ID_ESTACAO) on delete cascade
);


-- CRIANDO TABELA TREM
create table if not exists transporte.T_TPU_TREM(
	ID_TREM serial primary key,
	CODIGO_IDENTIFICACAO varchar(10) not null check (CODIGO_IDENTIFICACAO <> ''),
	CAPACIDADE_PASSAGEIROS integer not null,
	ESTADO_MANUTENCAO boolean not null default false,
	ID_LINHA integer not null references transporte.T_TPU_LINHA(ID_LINHA) on delete cascade
);



-- CRIANDO TABELA CONDUCAO
create table if not exists transporte.T_TPU_CONDUCAO(
	ID_CONDUCAO serial primary key,
	DATA_UTILIZADO date not null default now()::date,
	ID_FUNCIONARIO integer not null references transporte.T_TPU_FUNCIONARIO(ID_FUNCIONARIO) on delete cascade,
	ID_TREM integer not null references transporte.T_TPU_TREM(ID_TREM) on delete cascade
);
