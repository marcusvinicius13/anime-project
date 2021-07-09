package br.com.academy.integration;

import br.com.academy.domain.Anime;
import br.com.academy.repository.AnimeRepository;
import br.com.academy.requests.AnimePostRequestBody;
import br.com.academy.util.AnimeCreator;
import br.com.academy.util.AnimePostRequestBodyCreator;
import br.com.academy.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AnimeControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private AnimeRepository animeRepository;

    @LocalServerPort
    private int port;

    @Test
    @DisplayName(("List returns list of anime inside page object when successful"))
    void lis_ReturnsListOfAnimesInsidePageObject_WhenSuccessful() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        String expectedName = savedAnime.getName();

        PageableResponse<Anime> animePage = testRestTemplate.exchange("/animes", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Anime>>() {
                }).getBody();

        Assertions.assertThat(animePage).isNotNull();

        Assertions.assertThat(animePage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animePage.toList().get(0).getName())
                .isEqualTo(expectedName);
    }

    @Test
    @DisplayName(("List All returns list of anime when successful"))
    void listAll_ReturnsListOfAnimes_WhenSuccessful() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        String expectedName = savedAnime.getName();

        List<Anime> animes = testRestTemplate.exchange("/animes/all", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animes.get(0).getName())
                .isEqualTo(expectedName);

    }

    @Test
    @DisplayName(("Returns a anime when successful"))
    void findById_ReturnsAnime_When_Successful() {
        // Utiliza o criador de dados fakes, para mocar um anime salvo
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        // Pega o id do Anime salvo
        Long expectedId = savedAnime.getId();
        // Faz a requisição para a rota para pegar o Anime pelo ID, passado.
        Anime anime = testRestTemplate.getForObject("/animes/{id}", Anime.class, expectedId);
        //  Testa se o Anime que veio na requisição está null;
        Assertions.assertThat(anime).isNotNull();
        //  Testa se o ID do Anime que veio na requisição, está diferente de null e é igual ao que foi enviado!
        Assertions.assertThat(anime.getId())
                .isNotNull()
                .isEqualTo(expectedId);
    }

    @Test
    @DisplayName("Find by name returns  a list of Anime when successful")
    void findByName_ReturnsListOfAnime_When_Successful() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        String expectedName = savedAnime.getName();
        String url = String.format("/animes/find?name=%s", expectedName);

        List<Anime> animes = testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animes.get(0).getName())
                .isEqualTo(expectedName);

    }

    @Test
    @DisplayName("Find by name returns an empty list of anime when anime is not found")
    void findByName_ReturnsEmptyListOfAnime_When_Successful() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        String expectedName = savedAnime.getName();

        List<Anime> animes = testRestTemplate.exchange("/animes/find?name=nomeNaoRetorna", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isEmpty();

    }

    /*
        Passos
        1° Crio um animePostRequestBody
        2° Faço a requisição, para minha rota passando esse anime para ele testar se salva.
        3° Faço os Testes no objeto retornado.
     */
    @Test
    @DisplayName("Save returns anime when successful")
    void save_ReturnsAnime_WhenSuccessful() {
        AnimePostRequestBody animePostRequestBody = AnimePostRequestBodyCreator.createAnimePostRequestBody();
        ResponseEntity<Anime> animeResponse = testRestTemplate.postForEntity("/animes", animePostRequestBody, Anime.class);

        Assertions.assertThat(animeResponse).isNotNull();

        Assertions.assertThat(animeResponse.getBody())
                .isNotNull();

        Assertions.assertThat(animeResponse.getBody().getId())
                .isNotNull();

        Assertions.assertThat(animeResponse.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.CREATED);
    }

    /*
        Passos
        1° Salvo um anime
        2° Atualizo o nome desse anime
        3° Faço a requisição, para minha rota de atualização passando o anime alterado para ele testar se atualiza.
        4° Faço os Testes no objeto retornado.
     */
    @Test
    @DisplayName("Replace updates anime when successful")
    void replace_UpdatesAnime_When_Successful() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        savedAnime.setName("new name");

        ResponseEntity<Void> animeResponseEntity = testRestTemplate.exchange("/animes",
                HttpMethod.PUT, new HttpEntity<>(savedAnime), Void.class);

        Assertions.assertThat(animeResponseEntity)
                .isNotNull();

        Assertions.assertThat(animeResponseEntity.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.NO_CONTENT);
    }


    /*
        Passos
        1° Salvo um anime
        2° Atualizo o nome desse anime
        3° Faço a requisição, para minha rota de atualização passando o anime alterado para ele testar se atualiza.
        4° Faço os Testes no objeto retornado.
     */
    @Test
    @DisplayName("Delete Removes anime when successful")
    void delete_DeletesAnime_When_Successful() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        ResponseEntity<Void> animeResponseEntity = testRestTemplate.exchange("/animes/{id}",
                HttpMethod.DELETE, null, Void.class, savedAnime.getId());

        Assertions.assertThat(animeResponseEntity)
                .isNotNull();

        Assertions.assertThat(animeResponseEntity.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.NO_CONTENT);
    }

}
