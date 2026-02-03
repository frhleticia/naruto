package com.db.naruto;

import com.db.naruto.dto.ChakraRequest;
import com.db.naruto.dto.JutsuRequest;
import com.db.naruto.dto.PersonagemRequest;
import com.db.naruto.model.Personagem;
import com.db.naruto.repository.PersonagemRepository;
import com.db.naruto.service.PersonagemService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class PersonagemServiceIntegrationTest {

    @Autowired
    private PersonagemService personagemService;

    @Autowired
    private PersonagemRepository personagemRepository;

    @Test
    void deveCriarPersonagemQuandoDadosValidos() {
        Personagem resultado = personagemService.criarPersonagem(
                new PersonagemRequest("Naruto", 16, "Konoha", 100));

        assertNotNull(resultado);
        assertNotNull(resultado.getId());
        assertEquals("Naruto", resultado.getNome());
    }

    @Test
    void deveDeletarPersonagemQuandoExistente() {
        Personagem personagem = personagemService.criarPersonagem(
                new PersonagemRequest("Naruto", 16, "Konoha", 100));

        assertNotNull(personagem);

        personagemService.deletarPersonagem(personagem.getId());

        assertTrue(personagemRepository.findAll().isEmpty());
    }

    @Test
    void deveBuscarPersonagemPorIdQuandoExistente() {
        Personagem personagem = personagemService.criarPersonagem(
                new PersonagemRequest("Naruto", 16, "Konoha", 100));

        Personagem resultado = personagemService.buscarPersonagemPorId(personagem.getId());

        assertNotNull(resultado);
        assertEquals("Naruto", resultado.getNome());
    }

    @Test
    void deveLancarErroQuandoBuscarPersonagemInexistente() {
        assertThrows(RuntimeException.class,
                () -> personagemService.buscarPersonagemPorId(999));
    }

    @Test
    void deveAdicionarJutsuQuandoDadoValido() {
        Personagem personagem = personagemService.criarPersonagem(
                new PersonagemRequest("Naruto", 16, "Konoha", 100));

        personagemService.adicionarJutsu(personagem.getId(),
                new com.db.naruto.dto.JutsuRequest("Rasengan"));

        Personagem resultado = personagemService.buscarPersonagemPorId(personagem.getId());

        assertEquals(1, resultado.getQtdJutsus());
        assertEquals("Rasengan", resultado.getJutsus()[0]);
    }

    @Test
    void deveLancarExcecaoQuandoAdicionarJutsuDuplicado() {
        Personagem personagem = personagemService.criarPersonagem(
                new PersonagemRequest("Naruto", 16, "Konoha", 100));

        personagemService.adicionarJutsu(personagem.getId(),
                new JutsuRequest("Rasengan"));

        assertThrows(RuntimeException.class,
                () -> personagemService.adicionarJutsu(personagem.getId(),
                        new JutsuRequest("Rasengan")));
    }

    @Test
    void deveLancarExcecaoQuandoLimiteDeJutsusAtingido() {
        Personagem personagem = personagemService.criarPersonagem(
                new PersonagemRequest("Naruto", 16, "Konoha", 100));

        for (int i=0; i<10; i++) {
            personagemService.adicionarJutsu(personagem.getId(),
                    new JutsuRequest("Jutsu " + i));
        }

        assertThrows(RuntimeException.class,
                () -> personagemService.adicionarJutsu(personagem.getId(),
                        new JutsuRequest("Jutsu que extrapola limite")));
    }
    @Test
    void deveAumentarChakraQuandoValorValido() {
        Personagem personagem = personagemService.criarPersonagem(
                new PersonagemRequest("Naruto", 16, "Konoha", 100));

        personagemService.aumentarChakra(personagem.getId(),
                new ChakraRequest(50));

        Personagem resultado = personagemService.buscarPersonagemPorId(personagem.getId());

        assertEquals(150, resultado.getChakra());
    }

    @Test
    void deveLancarExcecaoQuandoAumentarChakraInvalido() {
        Personagem personagem = personagemService.criarPersonagem(
                new PersonagemRequest("Naruto", 16, "Konoha", 100));

        assertThrows(RuntimeException.class,
                () -> personagemService.aumentarChakra(personagem.getId(),
                        new ChakraRequest(-50)));
    }

    @Test
    void deveExibirInformacoesDoPersonagem() {
        Personagem personagem = personagemService.criarPersonagem(
                new PersonagemRequest("Naruto", 16, "Konoha", 100));

        personagemService.adicionarJutsu(personagem.getId(),
                new JutsuRequest("Rasengan"));

        Personagem resultado = personagemService.buscarPersonagemPorId(personagem.getId());

        assertNotNull(resultado);
        assertEquals("Naruto", resultado.getNome());
        assertEquals(1, resultado.getQtdJutsus());
        assertEquals("Rasengan", resultado.getJutsus()[0]);
    }
}
