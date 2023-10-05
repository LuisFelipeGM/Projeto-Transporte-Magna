package br.com.magna.trainees.transporte.models;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.magna.trainees.transporte.enums.TipoPassageiro;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "TB_CARTAO")
public class CartaoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long numero;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TipoPassageiro tipoPassageiro;

	@Column(nullable = false)
	private LocalDate dataAtivacao = LocalDate.now();

	@Column(nullable = false)
	private LocalDate dataValidade = LocalDate.now().plusYears(1);

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "FK_PASSAGEIRO", foreignKey = @ForeignKey(name = "FK_PASSAGEIRO_CARTAO"))
	@JsonIgnore
	private PassageiroModel passageiro;
	
	@OneToMany(mappedBy = "cartao")
	private List<TipoPassagemModel> tipoPassagens;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public TipoPassageiro getTipoPassageiro() {
		return tipoPassageiro;
	}

	public void setTipoPassageiro(TipoPassageiro tipoPassageiro) {
		this.tipoPassageiro = tipoPassageiro;
	}

	public LocalDate getDataAtivacao() {
		return dataAtivacao;
	}

	public void setDataAtivacao(LocalDate dataAtivacao) {
		this.dataAtivacao = dataAtivacao;
	}

	public LocalDate getDataValidade() {
		return dataValidade;
	}

	public void setDataValidade(LocalDate dataValidade) {
		this.dataValidade = dataValidade;
	}


	public void setPassageiro(PassageiroModel passageiro) {
		this.passageiro = passageiro;
	}

	public List<TipoPassagemModel> getTipoPassagens() {
		return tipoPassagens;
	}

	public void setTipoPassagens(List<TipoPassagemModel> tipoPassagens) {
		this.tipoPassagens = tipoPassagens;
	}

	
	
	

}
