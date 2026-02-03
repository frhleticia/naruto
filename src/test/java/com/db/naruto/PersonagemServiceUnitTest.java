package com.db.naruto;

import com.db.naruto.dto.PersonagemRequest;
import com.db.naruto.model.Personagem;
import com.db.naruto.repository.PersonagemRepository;
import com.db.naruto.service.PersonagemService;
import com.db.naruto.dto.JutsuRequest;
import com.db.naruto.dto.ChakraRequest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PersonagemServiceUnitTest {

    @InjectMocks
    private PersonagemService personagemService;

    @Mock
    private PersonagemRepository personagemRepository;

    @Test
    void deveCriarPersonagemQuandoDadosValidos() {
        PersonagemRequest req = new PersonagemRequest("Naruto", 16, "Konoha", 100);
        Personagem personagem = new Personagem("Naruto", 16, "Konoha", 100);

        when(personagemRepository.save(any(Personagem.class))).thenReturn(personagem);

        Personagem resultado = personagemService.criarPersonagem(req);

        assertNotNull(resultado);
        assertEquals("Naruto", resultado.getNome());
    }

    @Test
    void deveDeletarPersonagemQuandoExistente() {
        Personagem personagem =
                new Personagem("Naruto", 16, "Konoha", 100);
        personagem.setId(1);

        when(personagemRepository.findById(1))
                .thenReturn(Optional.of(personagem));

        personagemService.deletarPersonagem(1);

        verify(personagemRepository).delete(personagem);
    }

    @Test
    void deveLancarExcecaoQuandoBuscarPersonagemInexistente() {
        when(personagemRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> personagemService.buscarPersonagemPorId(1));
    }

    @Test
    void deveDevolverPersonagemQuandoExistente() {
        Personagem personagem =
                new Personagem("Naruto", 16, "Konoha", 100);
        personagem.setId(1);

        when(personagemRepository.findById(1))
                .thenReturn(Optional.of(personagem));

        Personagem resultado =
                personagemService.buscarPersonagemPorId(1);

        assertNotNull(resultado);
        assertEquals("Naruto", resultado.getNome());
        assertEquals(16, resultado.getIdade());
    }

    @Test
    void deveAdicionarJutsuAoPersonagemQuandoDadoValido() {
        Personagem personagem =
                new Personagem("Naruto", 16, "Konoha", 100);
        personagem.setId(1);

        when(personagemRepository.findById(1))
                .thenReturn(Optional.of(personagem));

        personagemService.adicionarJutsu(1, new JutsuRequest("Rasengan"));

        assertEquals(1, personagem.getQtdJutsus());
        assertEquals("Rasengan", personagem.getJutsus()[0]);
    }

    @Test
    void deveLancarExcecaoQuandoAdicionarJutsuDuplicado() {
        Personagem personagem = new Personagem("Naruto", 16, "Konoha", 100);
        personagem.setId(1);
        personagem.setQtdJutsus(1);
        personagem.getJutsus()[0] = "Rasengan";

        when(personagemRepository.findById(1))
                .thenReturn(Optional.of(personagem));

        assertThrows(RuntimeException.class, () ->
                personagemService.adicionarJutsu(1, new JutsuRequest("Rasengan")));
    }

    @Test
    void deveLancarExcecaoQuandoLimiteDeJutsusAtingido() {
        Personagem personagem = new Personagem("Naruto", 16, "Konoha", 100);
        personagem.setId(1);

        personagem.setQtdJutsus(12);

        when(personagemRepository.findById(1))
                .thenReturn(Optional.of(personagem));

        assertThrows(RuntimeException.class, () ->
                personagemService.adicionarJutsu(1, new JutsuRequest("Chidori")));
    }

    @Test
    void deveAumentarChakraQuandoValorValido() {
        Personagem personagem = new Personagem("Naruto", 16, "Konoha", 100);
        personagem.setId(1);

        when(personagemRepository.findById(1))
                .thenReturn(Optional.of(personagem));

        personagemService.aumentarChakra(1, new ChakraRequest(50));

        assertEquals(150, personagem.getChakra());
    }

    @Test
    void deveLancarExcecaoQuandoAumentarChakraInvalido() {
        ChakraRequest aumenta = new ChakraRequest(0);

        assertThrows(RuntimeException.class, () ->
                personagemService.aumentarChakra(1, aumenta));
    }

    @Test
    void deveExibirInformacoesDoPersonagem() {
        Personagem personagem = new Personagem("Naruto", 16, "Konoha", 100);
        personagem.setId(1);

        when(personagemRepository.findById(1))
                .thenReturn(Optional.of(personagem));

        personagemService.adicionarJutsu(1, new JutsuRequest("Rasengan"));

        Personagem resultado = personagemService.buscarPersonagemPorId(personagem.getId());

        assertNotNull(resultado);
        assertEquals("Naruto", resultado.getNome());
        assertEquals(16, resultado.getIdade());
        assertEquals("Konoha", resultado.getAldeia());
        assertEquals(100, resultado.getChakra());
        assertEquals("Rasengan", resultado.getJutsus()[0]);
    }
}
