-- Dados na tabela Usuario
insert into transporte.USUARIO(NOME_USUARIO, CPF, DATA_NASCIMENTO) values
	('João Silva', '12345678900', '1990-05-15'),
	('Maria Santos', '98765432100', '1985-11-20'),
	('Pedro Rodrigues', '45678912300', '1995-02-10'),
	('Ana Oliveira', '78912345600', '1988-08-03'),
	('Carlos Almeida', '32165498700', '1992-03-25'),
	('Lúcia Costa', '65432198700', '1987-09-12'),
	('Luís Felipe', '35168462948', '2003-09-01'),
	('Cleusa Gibin', '15793615742', '1966-06-29'),
	('Fernanda Menezes', '78623516849', '1992-03-17'),
	('Bruna Scheuer', '56791843185', '1990-05-09'),
	('Nilverton Lopes', '19487562389', '1992-07-16'),
	('Vinnie Scheuer', '89174832618', '1987-12-13'),
	('Valdir da Silva', '78321594658', '1965-10-08'),
	('Pedro Henrique', '78123548691', '2002-02-17'),
	('Sérgio Augusto', '35491623548', '1999-02-05'),
	('Débora Lopes', '78961387516', '1998-02-27'),
	('Diogo Kahn', '28135698426', '2000-04-18'),
	('Matheus Leme', '23654821569', '2001-08-26'),
	('Lucas Guerra', '35449815320', '1999-11-03'),
	('Pedro Victor', '30158648902', '2001-07-08');

--select * from transporte.USUARIO;


-- Dados na tabela CARTAO
insert into transporte.CARTAO(NUMERO_CARTAO, TIPO_PASSAGEIRO, SALDO, ID_USUARIO) values
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
	
--select * from transporte.CARTAO;
	
	
-- Dados na tabela CIDADE
insert into transporte.CIDADE(NOME_CIDADE, NUMERO_POPULACIONAL, ZONA_SP) values
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
	
--select * from transporte.CIDADE;
	

-- Dados na tabela LINHA 
insert into transporte.LINHA(NOME_LINHA, NUMERO_LINHA, CONEXAO_LINHA) values
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

--select * from transporte.LINHA;


-- Dados na tabela FEEDBACK
insert into transporte.FEEDBACK(COMENTARIO, NOTA, ID_LINHA, ID_USUARIO) values
	('Houve problemas na linha nessa manha mas resolveram rapido!', 4.2, 1, 1),
	('Atraso gigantesco na chegada do trem, demorei 1 hora para chegar na minha estação...', 3.5, 2, 2),
	('Atraso no horário.', 2.6, 3, 3),
	('Perfeito, não tive nenhum problema e a viagem foi perfeita', 5.0, 1, 4),
	('Demorou um pouco mas em geral foi bom', 4.2, 4, 5),
	('Confortável e rápido, gostei muito.', 4.5, 5, 6),
	('Falta de limpeza no veículo.', 2.4, 6, 7),
	('Atendimento excelente!', 4.3, 7, 8),
	('Muito Lotado', 3.1, 10, 9),
	('Confortável e rápido.', 5.0, 8, 10),
	('Atendimento Horrivel e Banheiros imundos', 0.0, 9, 11),
	('Atraso enorme, impossivel chegar no horario', 1.0, 11, 12),
	('Não tive nenhum problema', 4.9, 12, 13),
	('Escada rolante não estava funcionando', 4.2, 13, 14),
	('elevador demorado mas em geral tudo certo', 4.5, 10, 15),
	('Atendimento excelente!', 5.0, 12, 16),
	('Trem quebrou na linha e demorou 30 min para liberar a linha', 2.1, 6, 17),
	('Atraso no horario e escada rolante não funcionando', 2.9, 8, 18),
	('Falta de limpeza nos vagões', 3.7, 2, 19),
	('Bom serviço.', 4.3, 7, 20),
	('Falta de limpeza no veículo.', 1.0, 3, 8),
	('Mal Atendimento na compra do bilhete e trem demorado', 0.0, 1, 12),
	('Jovens estavam badernizando e nenhum segurança interveio', 3.5, 9, 5),
	('Rapido e prático!', 4.2, 10, 16),
	('Escada rolante quebrada mas em geral boa experiencia', 4.1, 2, 18),
	('Bom serviço.', 5.0, 12, 17),
	('Falta de limpeza no veículo.', 2.9, 9, 6),
	('Boa experiencia', 4.6, 11, 1),
	('Falta de limpeza no trem', 4.2, 5, 2),
	('Trem demorado', 3.9, 4, 9),
	('Atendimento excelente!', 5.0, 2, 13);
	
	--select * from transporte.FEEDBACK;


-- Dados na tabela ESTACAO
insert into transporte.ESTACAO(NOME_ESTACAO, ID_CIDADE, ID_LINHA) values
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

-- select * from transporte.ESTACAO;

-- Dados na tabela CARTAO_ESTACAO
insert into transporte.CARTAO_ESTACAO (PRECO, ID_CARTAO, ID_ESTACAO) values
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
	(0.00, 11, 32),
	(4.40, 1, 56),
	(2.20, 2, 42),
	(0.00, 2, 21),
	(4.40, 7, 23),
	(2.20, 8, 26),
	(4.40, 9, 41),
	(0.00, 10, 36),
	(4.40, 1, 10),
	(4.40, 1, 1),
	(0.00, 3, 23),
	(2.20, 4, 45),
	(4.40, 1, 53),
	(4.40, 6, 32),
	(2.20, 7, 12),
	(4.40, 8, 41),
	(4.40, 8, 3),
	(2.20, 9, 9),
	(0.00, 10, 43),
	(2.20, 9, 34),
	(0.00, 3, 32),
	(4.40, 1, 56),
	(2.20, 2, 42),
	(0.00, 2, 21),
	(4.40, 7, 23),
	(2.20, 8, 26),
	(4.40, 9, 41),
	(0.00, 10, 36);

-- select * from transporte.CARTAO_ESTACAO;


-- Dados na tabela TREM
insert into transporte.TREM (CODIGO_IDENTIFICACAO, CAPACIDADE_PASSAGEIROS, ESTADO_MANUTENCAO, ID_LINHA) values 
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

-- select * from transporte.TREM;

-- Dados na tabela FUNCIONARIO
insert into transporte.FUNCIONARIO(NOME_COMPLETO, FUNCAO, INICIO_HORA_TRABALHO, FIM_HORA_TRABALHO, SALARIO) values
	('João da Silva', 'Condutor', '04:00', '15:00', 2890.00),
	('Gabriel Dias', 'Condutor', '08:00', '16:00', 2300.00),
	('Thomas Silveira', 'Condutor', '08:00', '20:00', 2900.00),
	('Pedro Rodrigues', 'Condutor', '07:00', '00:00', 3000.00),
	('Nilverton Ribeiro', 'Condutor', '08:00', '15:00', 4000.00),
	('Gabriel Mendes', 'Condutor', '08:00', '22:00', 2500.00),
	('Breno Silva', 'Condutor', '08:00', '19:00', 2500.00),
	('Eduardo Ribeiro', 'Condutor', '08:00', '17:00', 2500.00),
	('André Silveira', 'Condutor', '08:00', '17:00', 3600.00),
	('Marta Holanda', 'Condutor', '08:00', '17:00', 7500.00),
	('Fabricio Carvalho', 'Condutor', '08:00', '18:00', 4500.00),
	('Luana Dias', 'Condutor', '08:00', '17:00', 5000.00),
	('Isadora Garcia', 'Condutor', '08:00', '00:00', 2900.00),
	('Ingrid Marques', 'Condutor', '08:00', '15:00', 2400.00),
	('Marco Aurelio', 'Condutor', '08:00', '16:00', 2200.00),
	('João Denver', 'Condutor', '08:00', '11:00', 3500.00),
	('John Luois', 'Condutor', '08:00', '17:00', 3200.00),
	('Clark Kent', 'Condutor', '08:00', '17:00', 2100.00),
	('Bruce Wayne', 'Condutor', '08:00', '17:00', 2500.00),
	('Miranda Matos', 'Condutor', '08:00', '17:00', 4600.00),
	('Matheus Silva', 'Condutor', '08:00', '17:00', 3600.00),
	('Rodrigo Goes', 'Condutor', '08:00', '17:00', 2500.00),
	('Roberto Carlos', 'Condutor', '08:00', '17:00', 2800.00),
	('Claudia Leite', 'Condutor', '08:00', '17:00', 2700.00),
	('Fernanda Oliveira', 'Condutor', '08:00', '17:00', 2900.00),
	('Diego Costa', 'Condutor', '08:00', '17:00', 3200.00),
	('Leonardo Rodrigues', 'Condutor', '08:00', '17:00', 3400.00),
	('Daniel Silva', 'Condutor', '08:00', '17:00', 3100.00),
	('Juliana Ferreira', 'Condutor', '08:00', '17:00', 3000.00),
	('Natália Souza', 'Condutor', '08:00', '17:00', 2900.00),
	('Jéssica Lima', 'Condutor', '08:00', '17:00', 2400.00),
	('Marina Ferreira', 'Condutor', '08:00', '17:00', 2800.00),
	('Lívia Martins', 'Condutor', '08:00', '17:00', 3000.00),
	('Helena Almeida', 'Condutor', '08:00', '17:00', 3500.00),
	('Diego Costa', 'Condutor', '08:00', '17:00', 2700.00),
	('Thiago Santos', 'Condutor', '08:00', '17:00', 2400.00),
	('Lucas Oliveira', 'Condutor', '08:00', '17:00', 2200.00),
	('Ana Oliveira', 'Condutor', '08:00', '17:00', 2600.00),
	('João da Silva', 'Condutor', '08:00', '17:00', 2800.00),
	('Ana Oliveira', 'Condutor', '08:00', '17:00', 2400.00),
	('Ricardo Santos', 'Condutor', '08:00', '17:00', 2300.00),
	('Maria Santos', 'Condutor', '08:00', '17:00', 2400.00),
	('Vitória Lima', 'Condutor', '08:00', '17:00', 2600.00),
	('Leonardo Rodrigues', 'Condutor', '08:00', '17:00', 2100.00),
	('Laura Mendes', 'Condutor', '08:00', '17:00', 2800.00),
	('Renata Lima', 'Condutor', '08:00', '17:00', 2600.00),
	('Ricardo Santos', 'Condutor', '08:00', '17:00', 2400.00),
	('Juliana Ferreira', 'Condutor', '08:00', '17:00', 2100.00),
	('Leonardo Rodrigues', 'Condutor', '08:00', '17:00', 2200.00),
	('Bruno Martins', 'Condutor', '08:00', '17:00', 2800.00),
	('Renato Santos', 'Condutor', '08:00', '17:00', 3600.00),
	('Daniel Silva', 'Suporte', '08:00', '17:00', 2500.00),
	('Eduarda Rodrigues', 'Suporte', '08:00', '17:00', 2500.00),
	('Thiago Santos', 'Suporte', '08:00', '17:00', 2500.00),
	('André Almeida', 'Suporte', '08:00', '17:00', 2500.00),
	('Beatriz Costa', 'Suporte', '08:00', '17:00', 2500.00),
	('Leonardo Rodrigues', 'Suporte', '08:00', '17:00', 2500.00),
	('Vitória Lima', 'Suporte', '08:00', '17:00', 2500.00),
	('Maria Santos', 'Suporte', '08:00', '17:00', 2500.00),
	('Luís Ferreira', 'Suporte', '08:00', '17:00', 2500.00),
	('André Almeida', 'Suporte', '08:00', '17:00', 2500.00),
	('Camila Almeida', 'Suporte', '08:00', '17:00', 2500.00),
	('Laura Mendes', 'Suporte', '08:00', '17:00', 2500.00),
	('Marcelo Santos', 'Suporte', '08:00', '17:00', 2500.00),
	('Daniel Silva', 'Suporte', '08:00', '17:00', 2500.00),
	('Felipe Almeida', 'Suporte', '08:00', '17:00', 2500.00),
	('Júlia Mendes', 'Suporte', '08:00', '17:00', 2500.00),
	('Gustavo Lima', 'Suporte', '08:00', '17:00', 2500.00);

-- select * from transporte.FUNCIONARIO;


-- Dados na tabela FUNCIONARIO_TREM
insert into transporte.FUNCIONARIO_TREM(ID_FUNCIONARIO, ID_TREM) values
	(1, 1),(2, 2),(3, 4),(4, 6),(5, 7),(6, 8),(7, 9),(8, 11),(9, 12),
	(10, 13),(11, 15),(12, 16),(13, 17),(14, 18),(15, 19),(16, 20),(17, 21),(18, 22),
	(19, 23),(20, 24),(21, 26),(22, 27),(23, 28),(24, 29),(25, 30),(26, 31),(27, 32),
	(28, 33),(29, 34),(30, 36),(31, 37),(32, 38),(33, 39),(34, 40),(35, 41),(36, 42),
	(37, 43),(38, 44),(39, 46),(40, 47),(41, 48),(42, 49),(43, 50);
	
-- select * from transporte.FUNCIONARIO_TREM;


-- Dados na tabela FUNCIONARIO_TREM
insert into transporte.MANUTENCAO(DATA_FIM, ID_FUNCIONARIO, ID_TREM) values
	(null, 52, 3), (null, 53, 5), (now()::date, 54, 10), (null, 55, 25), (null, 56, 35),
	(null, 57, 45), (null, 58, 48);

--select * from transporte.MANUTENCAO;








	
	
	