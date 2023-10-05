package br.com.magna.trainees.transporte.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_VIAGEM")
public class ViagemModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "FK_ESTACAO", foreignKey = @ForeignKey(name = "FK_ESTACAO_VIAGEM"))
	private EstacaoModel estacao;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "FK_TIPO_PASSAGEM", foreignKey = @ForeignKey(name = "FK_TIPO_PASSAGEM_VIAGEM"))
	@JsonIgnore
	private TipoPassagemModel tipoPassagem;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EstacaoModel getEstacao() {
		return estacao;
	}

	public void setEstacao(EstacaoModel estacao) {
		this.estacao = estacao;
	}


	public void setTipoPassagem(TipoPassagemModel tipoPassagem) {
		this.tipoPassagem = tipoPassagem;
	}
	
	

}
