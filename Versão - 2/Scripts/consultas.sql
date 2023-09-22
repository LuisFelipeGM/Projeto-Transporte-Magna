
-----------------------------------------------------------------------------

-- Quantidade de Viagens por Estação

select e.nome, count(v.id) "Viagens por estação" 
	from transporte.tb_estacao e
	inner join transporte.tb_viagem v on v.fk_estacao = e.id
group by 1
order by "Viagens por estação" desc ;


-----------------------------------------------------------------------------

-- Quantidade de conexoes por Estação

select e.nome "Estação", count(c.id) "Quantidade de Conexões" 
	from transporte.tb_conexao c
	inner join transporte.tb_estacao e on e.id = c.fk_estacao 
group by 1
order by "Quantidade de Conexões" desc;

-----------------------------------------------------------------------------

-- Passageiros Viagem por estação

select p.nome, c.numero, c.tipo_passageiro, e.nome
	from transporte.tb_passageiro p
	inner join transporte.tb_cartao c on p.id = c.fk_passageiro
	left join transporte.tb_tipo_passagem tp on c.id = tp.fk_cartao
	inner join transporte.tb_viagem v on v.id = tp.fk_cartao
	inner join transporte.tb_estacao e on e.id = v.fk_estacao 


