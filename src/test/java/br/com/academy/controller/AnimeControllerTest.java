package br.com.academy.controller;

import br.com.academy.domain.Anime;
import br.com.academy.requests.AnimePostRequestBody;
import br.com.academy.requests.AnimePutRequestBody;
import br.com.academy.service.AnimeService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
class AnimeControllerTest {

    @InjectMocks // Moca a classe que eu quero testar.
    private  AnimeController animeController;

    @Mock // Moka as classe que tem dentro do meu controller, dentro da classe que eu quero testar.
    private AnimeService animeServiceMock;

    @BeforeEach //  Executa esses mocks, antes de executar os métodos de testes.
    void setUp()
    {
        PageImpl<Anime> animePage = new PageImpl<Anime>(List.of(AnimeCreator.createValidAnime()));

        // Moca os dados quando for chamado o método listAll, passando qualquer tipo de argumento, ele chama o
        // animePage, que é uma lista paginada!
        BDDMockito.when(animeServiceMock.listAll(ArgumentMatchers.any()))
                .thenReturn(animePage);

        // Moca os dados quando for chamado o método listNonPageable, sem argumentos, ele chama o
        // List.of(AnimeCreator.createValidAnime()), que moca uma lista de animes, para teste!
        BDDMockito.when(animeServiceMock.listAllNonPageable())
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        // Moca os dados quando for chamado o método showOrThrowBadRequestException,
        // passando qualquer tipo de argumento do tipo Long, ele chama o
        // AnimeCreator.createValidAnime(), que cria um anime, e gera uma exceção caso o anime não exista!
        BDDMockito.when(animeServiceMock.showOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(AnimeCreator.createValidAnime());

        // Moca os dados quando for chamado o método listByName, passando qualquer tipo de argumento String
        // Ele chama o List.of(AnimeCreator.createValidAnime()), que moca uma lista de animes, para teste!
        BDDMockito.when(animeServiceMock.showByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        // Moca um anime para testar o método de salvar um anime
        // Quando chamar o método save, ele moca o anime com o AnimeCreator.createValidAnime().
        BDDMockito.when(animeServiceMock.save(ArgumentMatchers.any(AnimePostRequestBody.class)))
                .thenReturn(AnimeCreator.createValidAnime());

        // Moca um anime para testar o método de atualizar um anime
        // Quando chamar o método replace, ele não faz nada, porque o replace retorna void.
        BDDMockito.doNothing().when(animeServiceMock)
                .replace(ArgumentMatchers.any(AnimePutRequestBody.class));

        // Moca um anime para testar o método de deletar um anime
        // Quando chamar o método delete, ele não faz nada, porque o delete retorna void.
        BDDMockito.doNothing().when(animeServiceMock)
                .delete(ArgumentMatchers.anyLong());
    }

    @Test //    Retorna uma lista de Animes com objetos paginados caso de sucesso!
    @DisplayName("List returns list of anime inside page object when successful")
    void list_ReturnsListOfAnimesInsidePageObject_WhenSuccessful()
    {
        String expectedName = AnimeCreator.createValidAnime().getName();
        Page<Anime> animePage = animeController.list(null).getBody();

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
    void listAll_ReturnsListOfAnimes_WhenSuccessful()
    {
        String expectedName = AnimeCreator.createValidAnime().getName();

        List<Anime> animes = animeController.listAll().getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
    }

    @Test //    Retorna um anime, quando der sucesso!
    @DisplayName("FindById returns anime when successful")
    void findById_ReturnsAnime_WhenSuccessful()
    {
        Long expectedId = AnimeCreator.createValidAnime().getId();

        Anime anime = animeController.show(1L).getBody();

        Assertions.assertThat(anime).isNotNull();

        Assertions.assertThat(anime.getId())
                .isNotNull()
                .isEqualTo(expectedId);

    }

    @Test // Retorna uma lista de anime caso de sucesso!
    @DisplayName("FindByName returns list of anime when successful")
    void findByName_ReturnsListOfAnime_When_Successful()
    {
        String expectedName = AnimeCreator.createValidAnime().getName();

        List<Anime> animes = animeController.show("anime").getBody();

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
        BDDMockito.when(animeServiceMock.showByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Anime> animes = animeController.show("anime").getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isEmpty();
    }


    @Test // Testa o método de salvar um anime
    @DisplayName("Save returns anime when successful")
    void save_ReturnsAnime_When_Successful() {
        Anime anime = animeController.create(AnimePostRequestBodyCreator.createAnimePostRequestBody()).getBody();

        Assertions.assertThat(anime)
                .isNotNull()
                .isEqualTo(anime);
    }

    @Test // Testa o método de atualizar um anime e não retorna nada
    @DisplayName("Replace update anime when successful")
    void replace_UpdatesAnime_When_Successful() {
        //  Testa se não lançou nenhuma exeção ao chamar o método replace
        Assertions.assertThatCode(() -> animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()))
            .doesNotThrowAnyException();

        ResponseEntity<Void> entity = animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody());

        //  Testa se o entity retornado não é nulo
        Assertions.assertThat(entity)
                .isNotNull();

        //  Testa se o status code do entity retornado é igual a no_content
        Assertions.assertThat(entity.getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test // Testa o método de deletar um anime e não retorna nada
    @DisplayName("Deletes removes anime when successful")
    void replace_RemovesAnime_When_Successful() {
        //  Testa se não lançou nenhuma exeção ao chamar o método deletar
        Assertions.assertThatCode(() -> animeController.destroy(1))
            .doesNotThrowAnyException();

        ResponseEntity<Void> entity = animeController.destroy(1);

        //  Testa se o entity retornado não é nulo
        Assertions.assertThat(entity)
                .isNotNull();

        //  Testa se o status code do entity retornado é igual a no_content
        Assertions.assertThat(entity.getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);
    }

}