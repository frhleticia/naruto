package com.db.naruto.model;

public class NinjaDeTaijutsu extends Personagem implements Ninja {
    public NinjaDeTaijutsu(String nome, int idade, String aldeia, int chakra) {
        super(nome, idade, aldeia, chakra);
    }

    @Override
    public void usarJutsu() {
        System.out.println(getNome()+" está usando um golpe de Taijutsu.");
    }

    @Override
    public void desviar() {
        System.out.println(getNome()+" está desviando de um ataque usando sua habilidade em Taijutsu.");
    }
}
