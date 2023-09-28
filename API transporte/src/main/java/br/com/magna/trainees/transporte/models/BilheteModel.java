package br.com.magna.trainees.transporte.models;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_BILHETE")
public class BilheteModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private UUID codigo;

	@Column(nullable = false)
	private LocalDateTime dataGeracao;

	@Column(nullable = false)
	private boolean utilizado;

	public BilheteModel() {
		this.codigo = UUID.randomUUID();
		this.dataGeracao = LocalDateTime.now().withNano(0);
		this.utilizado = false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UUID getCodigo() {
		return codigo;
	}

	public void setCodigo(UUID codigo) {
		this.codigo = codigo;
	}

	public LocalDateTime getDataGeracao() {
		return dataGeracao;
	}

	public void setDataGeracao(LocalDateTime dataGeracao) {
		this.dataGeracao = dataGeracao;
	}

	public boolean isUtilizado() {
		return utilizado;
	}

	public void setUtilizado(boolean utilizado) {
		this.utilizado = utilizado;
	}

}
