package br.com.magna.trainees.transporte.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "TB_CIDADE")
public class CidadeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String nome;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "FK_ESTADO", foreignKey = @ForeignKey(name = "FK_ESTADO_CIDADE"))
    private EstadoModel estado;

    @OneToOne(mappedBy = "cidade")
    @JsonIgnore
    private BairroModel bairro;

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

    public EstadoModel getEstado() {
        return estado;
    }

    public void setEstado(EstadoModel estado) {
        this.estado = estado;
    }

    public BairroModel getBairro() {
        return bairro;
    }

    public void setBairro(BairroModel bairro) {
        this.bairro = bairro;
    }
}
