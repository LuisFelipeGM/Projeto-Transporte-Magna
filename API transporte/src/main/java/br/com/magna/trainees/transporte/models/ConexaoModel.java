package br.com.magna.trainees.transporte.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "TB_CONEXAO")
public class ConexaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "FK_LINHA", foreignKey = @ForeignKey(name = "FK_LINHA_CONEXAO"))
    private LinhaModel linha;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "FK_ESTACAO", foreignKey = @ForeignKey(name = "FK_ESTACAO_CONEXAO"))
    @JsonIgnore
    private EstacaoModel estacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LinhaModel getLinha() {
        return linha;
    }

    public void setLinha(LinhaModel linha) {
        this.linha = linha;
    }

    public EstacaoModel getEstacao() {
        return estacao;
    }

    public void setEstacao(EstacaoModel estacao) {
        this.estacao = estacao;
    }
}
