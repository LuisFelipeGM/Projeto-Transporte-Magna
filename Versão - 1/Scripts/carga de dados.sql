-- Dados na tabela USUARIO
insert into transporte.T_TPU_USUARIO(NOME_USUARIO) values 
	('João Silva'),
	('Maria Santos'),
	('Pedro Rodrigues'),
	('Ana Oliveira'),
	('Carlos Almeida'),
	('Lúcia Costa'),
	('Luís Felipe'),
	('Cleusa Gibin'),
	('Fernanda Menezes'),
	('Bruna Scheuer'),
	('Nilverton Lopes'),
	('Vinnie Scheuer'),
	('Valdir da Silva'),
	('Pedro Henrique'),
	('Sérgio Augusto'),
	('Débora Lopes'),
	('Diogo Kahn'),
	('Matheus Leme'),
	('Lucas Guerra'),
	('Pedro Victor'),

	('João da Silva'),
	('Gabriel Dias'),
	('Thomas Silveira'),
	('Pedro Rodrigues'),
	('Nilverton Ribeiro'),
	('Gabriel Mendes'),
	('Breno Silva'),
	('Eduardo Ribeiro'),
	('André Silveira'),
	('Marta Holanda'),
	('Fabricio Carvalho'),
	('Luana Dias'),
	('Isadora Garcia'),
	('Ingrid Marques'),
	('Marco Aurelio'),
	('João Denver'),
	('John Luois'),
	('Clark Kent'),
	('Bruce Wayne'),
	('Miranda Matos'),
	('Matheus Silva'),
	('Rodrigo Goes'),
	('Roberto Carlos'),
	('Claudia Leite'),
	('Fernanda Oliveira'),
	('Diego Costa'),
	('Leonardo Rodrigues'),
	('Daniel Silva'),
	('Juliana Ferreira'),
	('Natália Souza'),
	('Jéssica Lima'),
	('Marina Ferreira'),
	('Lívia Martins'),
	('Helena Almeida'),
	('Diego Costa'),
	('Thiago Santos'),
	('Lucas Oliveira'),
	('Ana Oliveira'),
	('João da Silva'),
	('Ana Oliveira'),
	('Ricardo Santos'),
	('Maria Santos'),
	('Vitória Lima'),
	('Leonardo Rodrigues'),
	('Bruno Martins'),
	('Renato Santos');
	
-- select * from transporte.T_TPU_USUARIO;


-- Dados na tabela FUNCIONARIO
insert into transporte.T_TPU_FUNCIONARIO(CODIGO, FUNCAO, INICIO_HORA_TRABALHO, FIM_HORA_TRABALHO, SALARIO, ID_USUARIO) values
	(84562, 'Condutor', '04:00', '15:00', 2890.00, 21),
	(85684, 'Condutor', '08:00', '16:00', 2300.00, 22),
	(95178, 'Condutor', '08:00', '20:00', 2900.00, 23),
	(83617, 'Condutor', '07:00', '00:00', 3000.00, 24),
	(86481, 'Condutor', '08:00', '15:00', 4000.00, 25),
	(83628, 'Condutor', '08:00', '22:00', 2500.00, 26),
	(89617, 'Condutor', '08:00', '19:00', 2500.00, 27),
	(89618, 'Condutor', '08:00', '17:00', 2500.00, 28),
	(83678, 'Condutor', '08:00', '17:00', 3600.00, 29),
	(83918, 'Condutor', '08:00', '17:00', 7500.00, 30),
	(89641, 'Condutor', '08:00', '18:00', 4500.00, 31),
	(80126, 'Condutor', '08:00', '17:00', 5000.00, 32),
	(89106, 'Condutor', '08:00', '00:00', 2900.00, 33),
	(86318, 'Condutor', '08:00', '15:00', 2400.00, 34),
	(83108, 'Condutor', '08:00', '16:00', 2200.00, 35),
	(81904, 'Condutor', '08:00', '11:00', 3500.00, 36),
	(83089, 'Condutor', '08:00', '17:00', 3200.00, 37),
	(83106, 'Condutor', '08:00', '17:00', 2100.00, 38),
	(89604, 'Condutor', '08:00', '17:00', 2500.00, 39),
	(86048, 'Condutor', '08:00', '17:00', 4600.00, 40),
	(84981, 'Condutor', '08:00', '17:00', 3600.00, 41),
	(83614, 'Condutor', '08:00', '17:00', 2500.00, 42),
	(86931, 'Condutor', '08:00', '17:00', 2800.00, 43),
	(89647, 'Condutor', '08:00', '17:00', 2700.00, 44),
	(80498, 'Condutor', '08:00', '17:00', 2900.00, 45),
	(83018, 'Condutor', '08:00', '17:00', 3200.00, 46),
	(94015, 'Condutor', '08:00', '17:00', 3400.00, 47),
	(94826, 'Condutor', '08:00', '17:00', 3100.00, 48),
	(96185, 'Condutor', '08:00', '17:00', 3000.00, 49),
	(93158, 'Condutor', '08:00', '17:00', 2900.00, 50),
	(94725, 'Condutor', '08:00', '17:00', 2400.00, 51),
	(93651, 'Condutor', '08:00', '17:00', 2800.00, 52),
	(97623, 'Condutor', '08:00', '17:00', 3000.00, 53),
	(97462, 'Condutor', '08:00', '17:00', 3500.00, 54),
	(94513, 'Condutor', '08:00', '17:00', 2700.00, 55),
	(97638, 'Condutor', '08:00', '17:00', 2400.00, 56),
	(94328, 'Condutor', '08:00', '17:00', 2200.00, 57),
	(91235, 'Condutor', '08:00', '17:00', 2600.00, 58),
	(97652, 'Condutor', '08:00', '17:00', 2800.00, 59),
	(93641, 'Condutor', '08:00', '17:00', 2400.00, 60),
	(97462, 'Condutor', '08:00', '17:00', 2300.00, 61),
	(93654, 'Condutor', '08:00', '17:00', 2400.00, 62),
	(94328, 'Condutor', '08:00', '17:00', 2600.00, 63),
	(94132, 'Condutor', '08:00', '17:00', 2100.00, 64),
	(90418, 'Condutor', '08:00', '17:00', 2800.00, 65),
	(93175, 'Condutor', '08:00', '17:00', 2600.00, 66);

-- select * from transporte.T_TPU_FUNCIONARIO


-- Dados na tabela PASSAGEIRO
insert into transporte.T_TPU_PASSAGEIRO(CPF, DATA_NASCIMENTO, ID_USUARIO) values
	(12345678900, '1990-05-15', 1),
	(98765432100, '1985-11-20', 2),
	(45678912300, '1995-02-10', 3),
	(78912345600, '1988-08-03', 4),
	(32165498700, '1992-03-25', 5),
	(65432198700, '1987-09-12', 6),
	(35168462948, '2003-09-01', 7),
	(15793615742, '1966-06-29', 8),
	(78623516849, '1992-03-17', 9),
	(56791843185, '1990-05-09', 10),
	(19487562389, '1992-07-16', 11),
	(89174832618, '1987-12-13', 12),
	(78321594658, '1965-10-08', 13),
	(78123548691, '2002-02-17', 14),
	(35491623548, '1999-02-05', 15),
	(78961387516, '1998-02-27', 16),
	(28135698426, '2000-04-18', 17),
	(23654821569, '2001-08-26', 18),
	(35449815320, '1999-11-03', 19),
	(30158648902, '2001-07-08', 20);

-- select * from transporte.T_TPU_PASSAGEIRO


-- Dados na tabela CARTAO
insert into transporte.T_TPU_CARTAO(NUMERO, TIPO_PASSAGEIRO, SALDO, ID_PASSAGEIRO) values
	(1235648, 'PASSE ESPECIAL', 233.20, 1),
	(1248965, 'PASSE SENIOR', 568.00, 2),
	(1481256, 'VALE TRANSPORTE', 315.80, 3),
	(1764512, 'PASSE LIVRE', 362.20, 4),
	(1364895, 'PASSE SENIOR', 82.70, 5),
	(1269845, 'PASSE ESCOLAR', 75.98, 6),
	(1367845, 'PASSE SENIOR', 453.71, 7),
	(1964512, 'PASSE ESPECIAL', 329.45, 8),
	(1781263, 'PASSE ESCOLAR', 269.78, 9),
	(1864215, 'PASSE SENIOR', 124.60, 10),
	(1983514, 'PASSE ESCOLAR', 540.64, 11),
	(1861245, 'VALE TRANSPORTE', 41.96, 12),
	(1648571, 'PASSE ESPECIAL', 756.50, 13),
	(1423578, 'PASSE LIVRE', 143.36, 14),
	(1364187, 'PASSE SENIOR', 312.89, 15),
	(1629845, 'VALE TRANSPORTE', 245.76, 16),
	(1127869, 'PASSE LIVRE', 146.28, 17),
	(1045306, 'PASSE ESCOLAR', 289.75, 18),
	(1127506, 'VALE TRANSPORTE', 264.63, 19),
	(1301570, 'PASSE LIVRE', 239.10, 20);

-- select * from transporte.T_TPU_CARTAO
	

-- Dados na tabela COMPRA_BILHETE
insert into transporte.T_TPU_COMPRA_BILHETE(CODIGO, UTILIZADO) values
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

-- select * from transporte.T_TPU_COMPRA_BILHETE


-- Dados na tabela TIPO_PASSAGEM
insert into transporte.T_TPU_TIPO_PASSAGEM (ID_CARTAO, ID_BILHETE) values
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
	
-- select * from transporte.T_TPU_TIPO_PASSAGEM


-- Dados na tabela CIDADE
insert into transporte.T_TPU_CIDADE(NOME, NUMERO_POPULACIONAL, ZONA_SP) values
	('São Paulo', 12330781, 'ZONA NORTE'),
	('Santo André', 721368, 'ZONA SUL'),
	('Ribeirão Preto', 711825, 'ZONA SUL'),
	('São José dos Campos', 729737, 'ZONA LESTE'),
	('Jundiaí', 423006, 'ZONA SUL'),
	('Osasco', 699944, 'ZONA OESTE'),
	('Carapicuiba', 1986452, 'ZONA OESTE'),
	('Guarulhos', 1392764, 'ZONA NORTE'),
	('Itapevi', 240961, 'ZONA SUL'),
	('Cotia', 253608, 'ZONA OESTE'),
	('Barueri', 276982, 'ZONA OESTE'),
	('Campinas', 3656363, 'ZONA NORTE');
	
--select * from transporte.T_TPU_CIDADE;
	

-- Dados na tabela LINHA 
insert into transporte.T_TPU_LINHA(NOME, NUMERO, CONEXAO) values
	('Azul', 1, true),
	('Verde', 2, true),
	('Vermelho', 3, true),
	('Amarelo', 4, true),
	('Lilás', 5, false),
	('Rubi', 7, true),
	('Diamante', 8, true),
	('Esmeralda', 9, true),
	('Turquesa', 10, false),
	('Coral', 11, true),
	('Safira', 12, true),
	('Jade', 13, true),
	('Prata', 15, false);

-- select * from transporte.T_TPU_LINHA;

-- Dados na tabela ESTACAO
insert into transporte.T_TPU_ESTACAO(NOME, ID_CIDADE, ID_LINHA) values
	('Tucuruvi', 1, 1),
	('Santana', 1, 1),
	('Tietê', 1, 1),
	('Luz', 2, 1),
	('Sé', 2, 1),
	('Liberdade', 3, 1),
	('Paraíso', 3, 1),
	('Vila Mariana', 4, 1),
	('Jabaquara', 5, 1),
	('Paulista', 6, 2),
	('Paraiso', 6, 2),
	('Triaton - Masp', 7, 2),
	('Alto do Ipiranga', 7, 2),
	('Barra Funda', 8, 3),
	('República', 8, 3),
	('Anhangabaú', 9, 3),
	('Pedro II', 8, 3),
	('Penha', 4, 3),
	('Itaquera', 9, 3),
	('Vila Sônia', 9, 4),
	('Morumbi', 9, 4),
	('Butantã', 10, 4),
	('Faria Lima', 10, 4),
	('Mackenzie', 10, 5),
	('Capão Redondo', 10, 5),
	('Brooklin', 11, 5),
	('Moema', 11, 5),
	('Lapa', 11, 7),
	('Jaraguá', 12, 7),
	('Perus', 12, 7),
	('Botujuru', 12, 7),
	('Jundiaí', 10, 7),
	('Itapevi', 9, 8),
	('Barueri', 1, 8),
	('Carapicuiba', 2, 8),
	('Osasco', 6, 8),
	('Barra Funda', 4, 8),
	('Ceasa', 12, 9),
	('Pinheiros', 11, 9),
	('Vila Olímpia', 10, 9),
	('Morumbi', 10, 9),
	('Mooca', 9, 10),
	('Ipiranga', 8, 10),
	('Utinga', 5, 10),
	('Capuava', 4, 10),
	('Rio Grande da Serra', 6, 10),
	('Luz', 12, 11),
	('Brás', 7, 11),
	('Guaianases', 9, 11),
	('Poá', 11, 11),
	('Mogi das Cruzes', 5, 11),
	('USP Leste', 8, 12),
	('Itaim Paulista', 9, 12),
	('Jardim Romano', 10, 12),
	('Itaquaquecetuba', 12, 12),
	('Calmon Viana', 4, 12),
	('Eng Goulart', 4, 13),
	('Guarulhos - Cecap', 5, 13),
	('Aeroporto Guarulhos', 7, 13),
	('Vila Prudente', 7, 13),
	('Oratório', 8, 13),
	('Vila União', 3, 13),
	('Jardim Colonial', 4, 13);

-- select * from transporte.T_TPU_ESTACAO;


-- Dados na tabela VIAGEM
insert into transporte.T_TPU_VIAGEM (PRECO, ID_TIPO_PASSAGEM, ID_ESTACAO) values
	(4.40, 1, 10),
	(4.40, 2, 1),
	(0.00, 3, 23),
	(2.20, 4, 45),
	(4.40, 5, 53),
	(4.40, 6, 32),
	(2.20, 7, 12),
	(4.40, 8, 41),
	(4.40, 8, 3),
	(2.20, 9, 9),
	(0.00, 10, 43),
	(2.20, 11, 34),
	(0.00, 12, 32),
	(4.40, 13, 56),
	(2.20, 14, 42),
	(0.00, 15, 21),
	(4.40, 16, 23),
	(2.20, 17, 26),
	(4.40, 18, 41),
	(0.00, 20, 36),
	(4.40, 21, 10),
	(4.40, 22, 1),
	(0.00, 23, 23),
	(2.20, 24, 45),
	(4.40, 25, 53),
	(4.40, 26, 32),
	(2.20, 27, 12),
	(4.40, 28, 41),
	(4.40, 29, 3),
	(2.20, 30, 9),
	(0.00, 31, 43),
	(2.20, 32, 34),
	(0.00, 33, 32),
	(4.40, 34, 56),
	(2.20, 35, 42),
	(0.00, 36, 21),
	(4.40, 37, 23),
	(2.20, 38, 26),
	(4.40, 39, 41),
	(0.00, 40, 36);

-- select * from transporte.T_TPU_VIAGEM;


-- Dados na tabela TREM
insert into transporte.T_TPU_TREM (CODIGO_IDENTIFICACAO, CAPACIDADE_PASSAGEIROS, ESTADO_MANUTENCAO, ID_LINHA) values 
	('RB456AT168', 2000, false, 1),
	('RB785AT892', 2000, false, 1),
	('RB238AT543', 2000, true, 1),
	('RB987AT341', 2000, false, 1),
	('RB654AT789', 2000, true, 1),
	('RB123AT456', 2000, false, 2),
	('RB890AT123', 2000, false, 2),
	('RB567AT234', 2000, false, 2),
	('RB456AT678', 2000, false, 2),
	('RB987AT321', 2000, true, 2),
	('RB234AT567', 2000, false, 3),
	('RB789AT890', 2000, false, 3),
	('RB876AT345', 2000, false, 3),
	('RB345AT678', 2000, true, 3),
	('RB654AT123', 2000, false, 4),
	('RB321AT987', 2000, false, 4),
	('RB123AT890', 2000, false, 4),
	('RB567AT432', 2000, false, 4),
	('RB890AT567', 2000, false, 5),
	('AB123CD456', 2000, false, 5),
	('EF789GH012', 2000, false, 5),
	('IJ345KL678', 2000, false, 5),
	('MN901OP234', 2000, false, 5),
	('QR567ST890', 2000, false, 6),
	('UV123WX456', 2000, true, 6),
	('YZ789AB012', 2000, false, 6),
	('CD345EF678', 2000, false, 6),
	('GH901IJ234', 2000, false, 7),
	('KL567MN890', 2000, false, 7),
	('OP123QR456', 2000, false, 7),
	('ST789UV012', 2000, false, 7),
	('WX345YZ678', 2000, false, 8),
	('AB901CD234', 2000, false, 8),
	('EF567GH890', 2000, false, 8),
	('IJ123KL456', 2000, true, 9),
	('MN789OP012', 2000, false, 9),
	('QR345ST678', 2000, false, 9),
	('UV901WX234', 2000, false, 9),
	('YZ567AB890', 2000, false, 10),
	('CD123EF456', 2000, false, 10),
	('GH789IJ012', 2000, false, 10),
	('KL345MN678', 2000, false, 11),
	('OP901QR234', 2000, false, 11),
	('ST567UV890', 2000, false, 11),
	('WX123YZ456', 2000, true, 11),
	('AB789CD012', 2000, false, 12),
	('EF345GH678', 2000, false, 12),
	('IJ901KL234', 2000, true, 12),
	('MN567OP890', 2000, false, 13),
	('QR123ST456', 2000, false, 13);

-- select * from transporte.T_TPU_TREM;



-- Dados na tabela FUNCIONARIO_TREM
insert into transporte.T_TPU_CONDUCAO(ID_FUNCIONARIO, ID_TREM) values
	(1, 1),(2, 2),(3, 4),(4, 6),(5, 7),(6, 8),(7, 9),(8, 11),(9, 12),
	(10, 13),(11, 15),(12, 16),(13, 17),(14, 18),(15, 19),(16, 20),(17, 21),(18, 22),
	(19, 23),(20, 24),(21, 26),(22, 27),(23, 28),(24, 29),(25, 30),(26, 31),(27, 32),
	(28, 33),(29, 34),(30, 36),(31, 37),(32, 38),(33, 39),(34, 40),(35, 41),(36, 42),
	(37, 43),(38, 44),(39, 46),(40, 47),(41, 48),(42, 49),(43, 50);
	
-- select * from transporte.T_TPU_CONDUCAO;









	
	
	