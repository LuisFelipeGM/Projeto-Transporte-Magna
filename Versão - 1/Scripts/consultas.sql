
-- Funcionarios que Trabalharam em determinado Trem

select u.nome_usuario "Nome Completo", fun.funcao "Função", t.codigo_identificacao "Código Trem", con.data_utilizado "Data Utilizado"
	from transporte.t_tpu_funcionario fun
	inner join transporte.t_tpu_usuario u on u.id_usuario = fun.id_usuario
	inner join transporte.t_tpu_conducao con on con.id_funcionario = fun.id_funcionario
	inner join transporte.t_tpu_trem t on t.id_trem = con.id_trem; 

-----------------------------------------------------------------------------

-- Quantidade de Trem em Atividade e Quantidade de Trens em Atividade


select count(con.id_conducao) "Quantidade de Trens em Atividade" from transporte.t_tpu_conducao con;


-----------------------------------------------------------------------------

-- Quantidade de Pessoas usando o cartão por Estação

select e.nome, count(v.id_viagem) "Pessoas por Estação usando o cartão" 
	from transporte.t_tpu_estacao e
	inner join transporte.t_tpu_viagem v on v.id_estacao = e.id_estacao
group by 1
order by "Pessoas por Estação usando o cartão" desc ;


-----------------------------------------------------------------------------

-- Quantidade de Estações por Linha

select l.nome "Linha", count(e.id_linha) "Quantidade de Estações" 
	from transporte.t_tpu_linha l
	inner join transporte.t_tpu_estacao e on l.id_linha = e.id_linha
group by 1
order by "Quantidade de Estações" desc;


-----------------------------------------------------------------------------

-- Usuarios com saldo entre R$150 e R$320

select u.nome_usuario, c.numero, c.saldo 
	from transporte.t_tpu_usuario u
	inner join transporte.t_tpu_passageiro p on u.id_usuario = p.id_usuario
	inner join transporte.t_tpu_cartao c on c.id_passageiro = p.id_passageiro
	where c.saldo between 150.0 and 320.0;


-----------------------------------------------------------------------------

-- Quantidade de Trens por linha

select l.nome "Linha", count(t.id_trem) "Quantidade de Trens"
	from transporte.t_tpu_trem t 
	inner join transporte.t_tpu_linha l on l.id_linha = t.id_linha
group by 1
order by "Quantidade de Trens" desc;


