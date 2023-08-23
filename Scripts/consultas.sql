
-- Relatório sobre os Feedbacks das Linhas feitas por Usuarios

select u.nome_usuario "Nome Usuario", f.nota "Nota", f.comentario "Comentario", l.numero_linha "Numero Linha", l.nome_linha "Linha" 
	from transporte.usuario u
	inner join transporte.feedback f on u.id_usuario = f.id_usuario
	inner join transporte.linha l on f.id_linha = l.id_linha ;

-----------------------------------------------------------------------------

-- Funcionarios que Trabalharam em determinado Trem

select fun.nome_completo "Nome Completo", fun.funcao "Função", t.codigo_identificacao "Código Trem", ft.data_utilizado "Data Utilizado"
	from transporte.funcionario fun
	inner join transporte.funcionario_trem ft on ft.id_funcionario = fun.id_funcionario
	inner join transporte.trem t on t.id_trem = ft.id_trem; 

-----------------------------------------------------------------------------

-- Funcionarios que estão fazendo Manutenção nos Trem

select fun.nome_completo "Nome Completo", fun.funcao "Função", t.codigo_identificacao "Código Trem", 
	m.data_inicio "Inicio Manutenção", m.data_fim "Fim Manutenção"
	from transporte.funcionario fun
	inner join transporte.manutencao m on m.id_funcionario = fun.id_funcionario
	inner join transporte.trem t on t.id_trem = m.id_trem; 
	

-----------------------------------------------------------------------------

-- Quantidade de Trem em Atividade e Quantidade de Trens em Manutenção


select count(m.id_manutencao) "Quantidade de Trens em Manutenção" from transporte.manutencao m;

select count(ft.id_funcionario_trem) "Quantidade de Trens em Atividade" from transporte.funcionario_trem ft;


-----------------------------------------------------------------------------

-- Quantidade de Pessoas usando o cartão por Estação

select e.nome_estacao, count(ce.id_cartao_estacao) "Pessoas por Estação usando o cartão" 
	from transporte.estacao e
	inner join transporte.cartao_estacao ce on ce.id_estacao = e.id_estacao
group by 1
order by "Pessoas por Estação usando o cartão" desc ;


-----------------------------------------------------------------------------

-- Quantidade de Estações por Linha

select l.nome_linha "Linha", count(e.id_linha) "Quantidade de Estações" 
	from transporte.linha l
	inner join transporte.estacao e on l.id_linha = e.id_linha
group by 1
order by "Quantidade de Estações" desc;


-----------------------------------------------------------------------------

-- Usuarios com saldo entre 

select u.nome_usuario, c.numero_cartao, c.saldo 
	from transporte.usuario u
	inner join transporte.cartao c on c.id_usuario = u.id_usuario 
where c.saldo between 150.0 and 320.0;


-----------------------------------------------------------------------------

-- Quantidade de Trens por linha

select l.nome_linha "Linha", count(t.id_trem) "Quantidade de Trens"
	from transporte.trem t 
	inner join transporte.linha l on l.id_linha = t.id_linha
group by 1
order by "Quantidade de Trens" desc;


