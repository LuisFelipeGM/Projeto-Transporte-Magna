package br.com.magna.trainees.transporte.models;

import java.time.LocalDate;
import java.time.Period;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_PASSAGEIRO")
public class PassageiroModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 255)
	private String nome;

	@Column(nullable = false, length = 12)
	private String cpf;

	@Column(nullable = false)
	private LocalDate dataNascimento;

	@JsonProperty("idade")
	public int idade() {
		return Period.between(dataNascimento, LocalDate.now()).getYears();
	}

	@OneToOne(mappedBy = "passageiro", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private CartaoModel cartao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public CartaoModel getCartao() {
		return cartao;
	}

	public void setCartao(CartaoModel cartao) {
		this.cartao = cartao;
	}

}
