package br.com.magna.trainees.transporte.models;

import jakarta.persistence.*;

import java.sql.Time;

@Entity
@Table(name = "TB_ESTACAO")
public class EstacaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Time horarioAbertura;

    @Column(nullable = false)
    private Time horarioFechamento;

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

    public Time getHorarioAbertura() {
        return horarioAbertura;
    }

    public void setHorarioAbertura(Time horarioAbertura) {
        this.horarioAbertura = horarioAbertura;
    }

    public Time getHorarioFechamento() {
        return horarioFechamento;
    }

    public void setHorarioFechamento(Time horarioFechamento) {
        this.horarioFechamento = horarioFechamento;
    }
}
