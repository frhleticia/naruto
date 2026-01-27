package com.db.naruto.service;

import com.db.naruto.model.Personagem;
import com.db.naruto.repository.PersonagemRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonagemService {
    private final PersonagemRepository repository;

    public PersonagemService(PersonagemRepository repository) {
        this.repository = repository;
    }

    public void salvarPersonagem(Personagem personagem){
        repository.saveAndFlush(personagem);
    }

    public void deletarPersonagem(Personagem personagem){
        repository.delete(personagem);
    }

    public Personagem buscarPersonagemPorId(Integer id){
        return repository.findById(id).orElseThrow(
                () -> new RuntimeException("Personagem não encontrado")
        );
    }

    public void adicionarJutsu(Integer id, String jutsu){
        Personagem personagem = buscarPersonagemPorId(id);

        for (int i=0; i < personagem.getQtdJutsus(); i++){
            if (personagem.getJutsus()[i].equalsIgnoreCase(jutsu)){
                throw new RuntimeException("Jutsu já aprendido");
            }

            if (personagem.getQtdJutsus() > 11){
                throw new RuntimeException("Limite de jutsus atingido");
            }

            personagem.getJutsus()[personagem.getQtdJutsus()] = jutsu;
            personagem.setQtdJutsus(personagem.getQtdJutsus() + 1);
        }
    }
}
