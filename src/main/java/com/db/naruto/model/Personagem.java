package com.db.naruto.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Personagem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String nome;
    private int idade;
    private int chakra;
    private String[] jutsus;
    private int qtdJutsus;

    public Personagem(String nome, int idade, int chakra, String[] jutsus, int qtdJutsus) {
        this.nome = nome;
        this.idade = idade;
        this.chakra = chakra;
        this.jutsus = new String[10];
        this.qtdJutsus = 0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public int getChakra() {
        return chakra;
    }

    public void setChakra(int chakra) {
        this.chakra = chakra;
    }

    public String[] getJutsus() {
        return jutsus;
    }

    public void setJutsus(String[] jutsus) {
        this.jutsus = jutsus;
    }

    public int getQtdJutsus() {
        return qtdJutsus;
    }

    public void setQtdJutsus(int qtdJutsus) {
        this.qtdJutsus = qtdJutsus;
    }
}
