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




	
	
