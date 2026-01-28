package com.db.naruto.controller;

import com.db.naruto.dto.PersonagemRequest;
import com.db.naruto.model.Personagem;
import com.db.naruto.service.PersonagemService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/personagem")
public class PersonagemController {
    private final PersonagemService personagemService;

    public PersonagemController(PersonagemService personagemService) {
        this.personagemService = personagemService;
    }

    @PostMapping
    public void criarPersonagem(@RequestBody PersonagemRequest req){
        personagemService.criarPersonagem(req);
    }


}
