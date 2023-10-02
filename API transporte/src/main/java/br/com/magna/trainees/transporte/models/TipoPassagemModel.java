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
@Table(name = "TB_TIPO_PASSAGEM")
public class TipoPassagemModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_CARTAO", foreignKey = @ForeignKey(name = "FK_CARTAO_TIPO_PASSAGEM"))
	@JsonIgnore
	private CartaoModel cartao;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_BILHETE", foreignKey = @ForeignKey(name = "FK_BILHETE_TIPO_PASSAGEM"))
	@JsonIgnore
	private BilheteModel bilhete;
	
	@OneToOne(mappedBy = "tipoPassagem")
	private ViagemModel viagem;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CartaoModel getCartao() {
		return cartao;
	}

	public void setCartao(CartaoModel cartao) {
		this.cartao = cartao;
	}

	public BilheteModel getBilhete() {
		return bilhete;
	}

	public void setBilhete(BilheteModel bilhete) {
		this.bilhete = bilhete;
	}

	public ViagemModel getViagem() {
		return viagem;
	}

	public void setViagem(ViagemModel viagem) {
		this.viagem = viagem;
	}
	
	

}
