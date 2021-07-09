package br.com.academy.service;

import br.com.academy.domain.Anime;
import br.com.academy.exception.BadRequestException;
import br.com.academy.repository.AnimeRepository;
import br.com.academy.util.AnimeCreator;
import br.com.academy.util.AnimePostRequestBodyCreator;
import br.com.academy.util.AnimePutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class AnimeServiceTest {
    @InjectMocks // Moca a classe que eu quero testar.
    private AnimeService animeService;

    @Mock // Moca a interface de animeRepository, para testar o animeService.
    private AnimeRepository animeRepositoryMock;

    @BeforeEach
        //  Executa esses mocks, antes de executar os métodos de testes.
    void setUp()
    {
        PageImpl<Anime> animePage = new PageImpl<Anime>(List.of(AnimeCreator.createValidAnime()));

        // Moca os dados quando for chamado o método listAll, passando qualquer tipo de argumento, ele chama o
        // animePage, que é uma lista paginada!
        BDDMockito.when(animeRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(animePage);

        // Moca os dados quando for chamado o método listNonPageable, sem argumentos, ele chama o
        // List.of(AnimeCreator.createValidAnime()), que moca uma lista de animes, para teste!
        BDDMockito.when(animeRepositoryMock.findAll())
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        // Moca os dados quando for chamado o método showOrThrowBadRequestException,
        // passando qualquer tipo de argumento do tipo Long, ele chama o
        // AnimeCreator.createValidAnime(), que cria um anime, e gera uma exceção caso o anime não exista!
        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(AnimeCreator.createValidAnime()));

        // Moca os dados quando for chamado o método listByName, passando qualquer tipo de argumento String
        // Ele chama o List.of(AnimeCreator.createValidAnime()), que moca uma lista de animes, para teste!
        BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        // Moca um anime para testar o método de salvar um anime
        // Quando chamar o método save, ele moca o anime com o AnimeCreator.createValidAnime().
        BDDMockito.when(animeRepositoryMock.save(ArgumentMatchers.any(Anime.class)))
                .thenReturn(AnimeCreator.createValidAnime());

        // Moca um anime para testar o método de deletar um anime
        // Quando chamar o método delete, ele não faz nada, porque o delete retorna void.
        BDDMockito.doNothing().when(animeRepositoryMock)
                .delete(ArgumentMatchers.any(Anime.class));
    }

    @Test //    Retorna uma lista de Animes com objetos paginados caso de sucesso!
    @DisplayName("List All returns list of anime inside page object when successful")
    void listAll_ReturnsListOfAnimesInsidePageObject_WhenSuccessful()
    {
        String expectedName = AnimeCreator.createValidAnime().getName();
        Page<Anime> animePage = animeService.listAll(PageRequest.of(1,1));


        Assertions.assertThat(animePage).isNotNull();

        Assertions.assertThat(animePage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions
                .assertThat(animePage.toList().get(0).getName())
                .isEqualTo(expectedName);
    }

    @Test //    Retorna uma lista de Animes não paginados caso de sucesso!
    @DisplayName("ListAll returns list of anime when successful")
    void listAllNonPageable_ReturnsListOfAnimes_WhenSuccessful()
    {
        String expectedName = AnimeCreator.createValidAnime().getName();

        List<Anime> animes = animeService.listAllNonPageable();

        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
    }

    @Test //    Retorna um anime, quando der sucesso!
    @DisplayName("ShowOrThrowBadRequestException returns anime when successful")
    void showOrThrowBadRequestException_ReturnsAnime_WhenSuccessful()
    {
        Long expectedId = AnimeCreator.createValidAnime().getId();

        Anime anime = animeService.showOrThrowBadRequestException(1L);

        Assertions.assertThat(anime).isNotNull();

        Assertions.assertThat(anime.getId())
                .isNotNull()
                .isEqualTo(expectedId);

    }

    @Test //    Retorna um anime, quando der sucesso!
    @DisplayName("ShowOrThrowBadRequestException throws BadRequestException when anime not found")
    void showOrThrowBadRequestException_ThrowsBadRequestException_WhenAnimeIsNotFound() {
        // Moca os dados quando for chamado o método showOrThrowBadRequestException,
        // passando qualquer tipo de argumento do tipo Long, ele chama o
        // AnimeCreator.createValidAnime(), que cria um anime, e gera uma exceção caso o anime não exista!
        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> animeService.showOrThrowBadRequestException(1L));
    }

    @Test // Retorna uma lista de anime caso de sucesso!
    @DisplayName("ShowByName returns list of anime when successful")
    void showByName_ReturnsListOfAnime_When_Successful()
    {
        String expectedName = AnimeCreator.createValidAnime().getName();

        List<Anime> animes = animeService.showByName("anime");

        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animes.get(0).getName())
                .isEqualTo(expectedName);
    }

    @Test // Retorna uma lista vazia caso dê sucesso!
    @DisplayName("FindByName returns an empty list of anime when anime is not found")
    void findByName_ReturnsEmptyListOfAnime_When_AnimeIsNotFound()
    {
        // Cria uma Collections com uma lista vazia, para testar quando não hover objetos no retorno do método.
        BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Anime> animes = animeService.showByName("anime");

        Assertions.assertThat(animes)
                .isNotNull()
                .isEmpty();
    }


    @Test // Testa o método de salvar um anime
    @DisplayName("Save returns anime when successful")
    void save_ReturnsAnime_When_Successful() {
        Anime anime = animeService.save(AnimePostRequestBodyCreator.createAnimePostRequestBody());

        Assertions.assertThat(anime)
                .isNotNull()
                .isEqualTo(anime);
    }

    @Test // Testa o método de atualizar um anime e não retorna nada
    @DisplayName("Replace update anime when successful")
    void replace_UpdatesAnime_When_Successful() {
        //  Testa se não lançou nenhuma exeção ao chamar o método replace
        Assertions.assertThatCode(() -> animeService.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()))
                .doesNotThrowAnyException();
    }

    @Test // Testa o método de deletar um anime e não retorna nada
    @DisplayName("Deletes removes anime when successful")
    void replace_RemovesAnime_When_Successful() {
        //  Testa se não lançou nenhuma exeção ao chamar o método deletar
        Assertions.assertThatCode(() -> animeService.delete(1))
                .doesNotThrowAnyException();
    }

}