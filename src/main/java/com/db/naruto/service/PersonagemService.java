package com.db.naruto.service;

import com.db.naruto.dto.ChakraRequest;
import com.db.naruto.dto.JutsuRequest;
import com.db.naruto.dto.PersonagemRequest;
import com.db.naruto.model.Personagem;
import com.db.naruto.repository.PersonagemRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonagemService {
    private final PersonagemRepository repository;

    public PersonagemService(PersonagemRepository repository) {
        this.repository = repository;
    }

    public Personagem criarPersonagem(PersonagemRequest req){
        Personagem personagem = new Personagem(
                req.nome(), req.idade(), req.aldeia(), req.chakra());

        return repository.save(personagem);
    }

    public void deletarPersonagem(Integer id){
        Personagem personagem = buscarPersonagemPorId(id);
        repository.delete(personagem);
    }

    public Personagem buscarPersonagemPorId(Integer id){
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personagem não encontrado"));
    }

    public void adicionarJutsu(Integer id, JutsuRequest jutsu){
        Personagem personagem = buscarPersonagemPorId(id);

        if (personagem.getQtdJutsus() > 11){
            throw new RuntimeException("Limite de jutsus atingido");
        }

        for (int i=0; i < personagem.getQtdJutsus(); i++){
            if (personagem.getJutsus()[i]
                    .equalsIgnoreCase(jutsu.nome())){
                throw new RuntimeException("Jutsu já aprendido");
            }
        }

        personagem.getJutsus()[personagem.getQtdJutsus()] = jutsu.nome();
        personagem.setQtdJutsus(personagem.getQtdJutsus() + 1);

        repository.save(personagem);
    }

    public void aumentarChakra(Integer id, ChakraRequest aumentaChakra){
        if (aumentaChakra.numero() <= 0) {
            throw new RuntimeException("Quantidade de chakra inválida");
        }

        Personagem personagem = buscarPersonagemPorId(id);
        personagem.setChakra(personagem.getChakra() + aumentaChakra.numero());

        repository.save(personagem);
    }
}
