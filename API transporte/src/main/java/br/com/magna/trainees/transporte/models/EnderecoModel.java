package br.com.magna.trainees.transporte.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "TB_ENDERECO")
public class EnderecoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String logradouro;

    @Column(nullable = false)
    private int numero;

    @Column(length = 100)
    private String complemento;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "FK_ESTACAO", foreignKey = @ForeignKey(name = "FK_ESTACAO_ENDERECO"))
    @JsonIgnore
    private EstacaoModel estacao;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "FK_BAIRRO", foreignKey = @ForeignKey(name = "FK_BAIRRO_ENDERECO"))
    private BairroModel bairro;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public EstacaoModel getEstacao() {
        return estacao;
    }

    public void setEstacao(EstacaoModel estacao) {
        this.estacao = estacao;
    }

    public BairroModel getBairro() {
        return bairro;
    }

    public void setBairro(BairroModel bairro) {
        this.bairro = bairro;
    }
}
