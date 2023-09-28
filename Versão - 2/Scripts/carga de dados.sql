-- Dados na tabela PASSAGEIRO
insert into transporte.TB_PASSAGEIRO(NOME, CPF, DATA_NASCIMENTO) values
	('João Silva', 12345678900, '1990-05-15'),
	('Maria Santos', 98765432100, '1985-11-20'),
	('Pedro Rodrigues', 45678912300, '1995-02-10'),
	('Ana Oliveira', 78912345600, '1988-08-03'),
	('Carlos Almeida', 32165498700, '1992-03-25'),
	('Lúcia Costa', 65432198700, '1987-09-12'),
	('Luís Felipe', 35168462948, '2003-09-01'),
	('Cleusa Gibin', 15793615742, '1966-06-29'),
	('Fernanda Menezes', 78623516849, '1992-03-17'),
	('Bruna Scheuer', 56791843185, '1990-05-09'),
	('Nilverton Lopes', 19487562389, '1992-07-16'),
	('Vinnie Scheuer', 89174832618, '1987-12-13'),
	('Valdir da Silva', 78321594658, '1965-10-08'),
	('Pedro Henrique', 78123548691, '2002-02-17'),
	('Sérgio Augusto', 35491623548, '1999-02-05'),
	('Débora Lopes', 78961387516, '1998-02-27'),
	('Diogo Kahn', 28135698426, '2000-04-18'),
	('Matheus Leme', 23654821569, '2001-08-26'),
	('Lucas Guerra', 35449815320, '1999-11-03'),
	('Pedro Victor', 30158648902, '2001-07-08');

-- select * from transporte.TB_PASSAGEIRO


-- Dados na tabela CARTAO
insert into transporte.TB_CARTAO(NUMERO, TIPO_PASSAGEIRO, FK_PASSAGEIRO) values
	(1235648, 'PASSE_ESPECIAL', 1),
	(1248965, 'PASSE_SENIOR', 2),
	(1481256, 'VALE_TRANSPORTE', 3),
	(1764512, 'PASSE_LIVRE', 4),
	(1364895, 'PASSE_SENIOR', 5),
	(1269845, 'PASSE_ESCOLAR', 6),
	(1367845, 'PASSE_SENIOR', 7),
	(1964512, 'PASSE_ESPECIAL', 8),
	(1781263, 'PASSE_ESCOLAR', 9),
	(1864215, 'PASSE_SENIOR', 10),
	(1983514, 'PASSE_ESCOLAR', 11),
	(1861245, 'VALE_TRANSPORTE', 12),
	(1648571, 'PASSE_ESPECIAL', 13),
	(1423578, 'PASSE_LIVRE', 14),
	(1364187, 'PASSE_SENIOR', 15),
	(1629845, 'VALE_TRANSPORTE', 16),
	(1127869, 'PASSE_LIVRE', 17),
	(1045306, 'PASSE_ESCOLAR', 18),
	(1127506, 'VALE_TRANSPORTE', 19),
	(1301570, 'PASSE_LIVRE', 20);

-- select * from transporte.TB_CARTAO
	

-- Dados na tabela BILHETE
insert into transporte.TB_BILHETE(CODIGO, UTILIZADO) values
	(gen_random_uuid(), false),
	(gen_random_uuid(), false),
	(gen_random_uuid(), false),
	(gen_random_uuid(), false),
	(gen_random_uuid(), false),
	(gen_random_uuid(), false),
	(gen_random_uuid(), false),
	(gen_random_uuid(), false),
	(gen_random_uuid(), true),
	(gen_random_uuid(), false),
	(gen_random_uuid(), false),
	(gen_random_uuid(), false),
	(gen_random_uuid(), false),
	(gen_random_uuid(), true),
	(gen_random_uuid(), false),
	(gen_random_uuid(), false),
	(gen_random_uuid(), true),
	(gen_random_uuid(), false),
	(gen_random_uuid(), false),
	(gen_random_uuid(), false);

-- select * from transporte.TB_BILHETE


-- Dados na tabela TIPO_PASSAGEM
insert into transporte.TB_TIPO_PASSAGEM(FK_CARTAO, FK_BILHETE) values
	(1, null),
	(2, null),
	(null,1),
	(null,2),
	(3, null),
	(4, null),
	(null,3),
	(null,4),
	(5, null),
	(6, null),
	(7, null),
	(8, null),
	(9, null),
	(null,5),
	(null,6),
	(null,7),
	(null,8),
	(10, null),
	(11, null),
	(null,9),
	(null,10),
	(12, null),
	(13, null),
	(14, null),
	(15, null),
	(16, null),
	(17, null),
	(null,11),
	(null,12),
	(null,13),
	(18, null),
	(null,14),
	(null,15),
	(null,16),
	(19, null),
	(null,17),
	(null,18),
	(null,19),
	(20, null),
	(null,20);
	
-- select * from transporte.TB_TIPO_PASSAGEM


-- Dados na tabela LINHA 
insert into transporte.TB_LINHA(NOME, NUMERO) values
	('Azul', 1),
	('Verde', 2),
	('Vermelho', 3),
	('Amarelo', 4),
	('Lilás', 5),
	('Rubi', 7),
	('Diamante', 8),
	('Esmeralda', 9),
	('Turquesa', 10),
	('Coral', 11),
	('Safira', 12),
	('Jade', 13),
	('Prata', 15);

-- select * from transporte.TB_LINHA;


-- Dados na tabela ESTACAO
insert into transporte.TB_ESTACAO(NOME) values
	('Tucuruvi'),
	('Santana'),
	('Tietê'),
	('Luz'),
	('Sé'),
	('Liberdade'),
	('Paraíso'),
	('Vila Mariana'),
	('Jabaquara'),
	('Paulista'),
	('Triaton - Masp'),
	('Alto do Ipiranga'),
	('Barra Funda'),
	('República'),
	('Anhangabaú'),
	('Pedro II'),
	('Penha'),
	('Itaquera'),
	('Vila Sônia'),
	('Morumbi'),
	('Butantã'),
	('Faria Lima'),
	('Mackenzie'),
	('Capão Redondo'),
	('Brooklin'),
	('Moema'),
	('Lapa'),
	('Jaraguá'),
	('Perus'),
	('Botujuru'),
	('Jundiaí'),
	('Itapevi'),
	('Barueri'),
	('Carapicuiba'),
	('Osasco'),
	('Barra Funda'),
	('Ceasa'),
	('Pinheiros'),
	('Vila Olímpia'),
	('Morumbi'),
	('Mooca'),
	('Ipiranga'),
	('Utinga'),
	('Capuava'),
	('Rio Grande da Serra'),
	('Luz'),
	('Brás'),
	('Guaianases'),
	('Poá'),
	('Mogi das Cruzes'),
	('USP Leste'),
	('Itaim Paulista'),
	('Jardim Romano'),
	('Itaquaquecetuba'),
	('Calmon Viana'),
	('Eng Goulart'),
	('Guarulhos - Cecap'),
	('Aeroporto Guarulhos'),
	('Vila Prudente'),
	('Oratório'),
	('Vila União'),
	('Jardim Colonial');

-- select * from transporte.TB_ESTACAO;


-- Dados na tabela CONEXAO
insert into transporte.TB_CONEXAO (FK_LINHA, FK_ESTACAO) values
	(1, 1),
	(1, 2),
	(2, 3),
	(2, 4),
	(3, 5),
	(3, 6),
	(4, 7),
	(4, 8),
	(5, 9),
	(6, 10),
	(6, 11),
	(7, 12),
	(7, 13),
	(8, 14),
	(8, 15),
	(9, 16),
	(9, 17),
	(10, 18),
	(10, 19),
	(11, 20),
	(11, 21),
	(12, 22),
	(12, 23),
	(13, 24),
	(13, 25);



-- Dados na tabela VIAGEM
insert into transporte.TB_VIAGEM (FK_TIPO_PASSAGEM, FK_ESTACAO) values
	(1, 10),
	(2, 1),
	(3, 23),
	(4, 45),
	(5, 53),
	(6, 32),
	(7, 12),
	(8, 41),
	(8, 3),
	(9, 9),
	(10, 43),
	(11, 34),
	(12, 32),
	(13, 56),
	(14, 42),
	(15, 21),
	(16, 23),
	(17, 26),
	(18, 41),
	(20, 36),
	(21, 10),
	(22, 1),
	(23, 23),
	(24, 45),
	(25, 53),
	(26, 32),
	(27, 12),
	(28, 41),
	(29, 3),
	(30, 9),
	(31, 43),
	(32, 34),
	(33, 32),
	(34, 56),
	(35, 42),
	(36, 21),
	(37, 23),
	(38, 26),
	(39, 41),
	(40, 36);

-- select * from transporte.TB_VIAGEM;


-- Dados na tabela ESTADO
insert into transporte.TB_ESTADO(NOME, SIGLA) values
	('Acre', 'AC'),
	('Amapá', 'AP'),
	('Amazonas', 'AM'),
	('Pará', 'PA'),
	('Rondônia', 'RO'),
	('Roraima', 'RR'),
	('Tocantins', 'TO'),
	('Alagoas', 'AL'),
	('Bahia', 'BA'),
	('Ceará', 'CE'),
	('Maranhão', 'MA'),
	('Paraíba', 'PB'),
	('Pernambuco', 'PE'),
	('Piauí', 'PI'),
	('Rio Grande do Norte', 'RN'),
	('Sergipe', 'SE'),
	('Espírito Santo', 'ES'),
	('Minas Gerais', 'MG'),
	('Rio de Janeiro', 'RJ'),
	('São Paulo', 'SP'),
	('Paraná', 'PR'),
	('Rio Grande do Sul', 'RS'),
	('Santa Catarina', 'SC'),
	('Distrito Federal', 'DF'),
	('Goiás', 'GO'),
	('Mato Grosso', 'MT'),
	('Mato Grosso do Sul', 'MS');

-- select * from transporte.TB_ESTADO;


--Dados na tabela CIDADE
insert into transporte.TB_CIDADE(NOME, FK_ESTADO) values
	('Itapevi', 20),
	('Jandira', 20),
	('Barueri', 20),
	('Carapicuíba', 20),
	('Osasco', 20),
	('São Paulo', 20),
	('Caieiras', 20),
	('Franco da Rocha', 20),
	('Francisco Morato', 20),
	('Campo Limpo Paulista', 20),
	('Várzea Paulista', 20),
	('Jundiaí', 20),
	('Guarulhos', 20),
	('Itapevi', 20),
	('Itaquaquecetuba', 20),
	('Mogi das Cruzes', 20),
	('Suzano', 20),
	('São Caetano do Sul', 20),
	('Santo André', 20),
	('Mauá', 20),
	('Ribeirão Pires', 20),
	('Rio Grande da Serra', 20);
-- select * from transporte.TB_ESTADO;




	
	
	