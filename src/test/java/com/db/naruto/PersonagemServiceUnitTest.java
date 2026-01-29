package com.db.naruto;

import com.db.naruto.dto.PersonagemRequest;
import com.db.naruto.model.Personagem;
import com.db.naruto.repository.PersonagemRepository;
import com.db.naruto.service.PersonagemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PersonagemServiceUnitTest {

    @InjectMocks
    PersonagemService personagemService;

    @Mock
    PersonagemRepository personagemRepository;

    @BeforeEach
    void setup() {
        personagemRepository = mock(PersonagemRepository.class);
        personagemService = new PersonagemService(personagemRepository);
    }

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
    }
}
