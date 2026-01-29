package com.db.naruto.controller;

import com.db.naruto.dto.ChakraRequest;
import com.db.naruto.dto.JutsuRequest;
import com.db.naruto.dto.PersonagemRequest;
import com.db.naruto.model.Personagem;
import com.db.naruto.service.PersonagemService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/personagens")
public class PersonagemController {
    private final PersonagemService personagemService;

    public PersonagemController(PersonagemService personagemService) {
        this.personagemService = personagemService;
    }

    @PostMapping
    public void criarPersonagem(@RequestBody PersonagemRequest req){
        personagemService.criarPersonagem(req);
    }

    @PostMapping("/{id}/jutsus")
    public void adicionarJutsu(@PathVariable Integer id, @RequestBody JutsuRequest jutsuNome){
        personagemService.adicionarJutsu(id, jutsuNome);
    }

    @PostMapping("/{id}/chakras")
    public void aumentarChakra(@PathVariable Integer id, @RequestBody ChakraRequest aumentaChakra){
        personagemService.aumentarChakra(id, aumentaChakra);
    }

    @GetMapping("/{id}")
    public Personagem listarPersonagem(@PathVariable Integer id){
        return personagemService.buscarPersonagemPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletarPersonagem(@PathVariable Integer id){
        personagemService.deletarPersonagem(id);
    }
}
