package br.com.magna.trainees.transporte.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "TB_BAIRRO")
public class BairroModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String nome;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "FK_CIDADE", foreignKey = @ForeignKey(name = "FK_CIDADE_BAIRRO"))
    private CidadeModel cidade;

    @OneToMany(mappedBy = "bairro", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private List<EnderecoModel> endereco;

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

    public CidadeModel getCidade() {
        return cidade;
    }

    public void setCidade(CidadeModel cidade) {
        this.cidade = cidade;
    }

}
