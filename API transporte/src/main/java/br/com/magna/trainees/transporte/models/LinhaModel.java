package br.com.magna.trainees.transporte.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "TB_LINHA")
public class LinhaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private int numero;

    @OneToMany(mappedBy = "linha")
    private List<ConexaoModel> conexao;

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

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }



}