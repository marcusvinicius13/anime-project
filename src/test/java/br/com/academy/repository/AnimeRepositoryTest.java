package br.com.academy.repository;

import br.com.academy.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ListAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Tests for AnimeRepository")
@Log4j2
class AnimeRepositoryTest {

    @Autowired
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("Save persists anime when successful")
    void save_Persistes_Anime_WhenSuccessful() {
        Anime anime = createAnime();
        Anime animeSaved = this.animeRepository.save(anime);

        Assertions.assertThat(animeSaved).isNotNull();
        Assertions.assertThat(animeSaved.getId()).isNotNull();
        Assertions.assertThat(animeSaved.getName())
                .isEqualTo(animeSaved.getName());
    }

    @Test
    @DisplayName("Save updates anime when successful")
    void save_Updates_Anime_WhenSuccessful() {
        Anime anime = createAnime();
        Anime animeSaved = this.animeRepository.save(anime);
        animeSaved.setName("Overlod");
        Anime animeUpdated = this.animeRepository.save(animeSaved);

        Assertions.assertThat(animeUpdated).isNotNull();
        Assertions.assertThat(animeUpdated.getId()).isNotNull();
        Assertions.assertThat(animeSaved.getName())
                .isEqualTo(animeSaved.getName());
    }

    @Test
    @DisplayName("Delete removes anime when successful")
    void delete_Removes_Anime_WhenSuccessful() {
        Anime animeToBeDeleted = createAnime();

        Anime animeDeleted = this.animeRepository.save(animeToBeDeleted);
        this.animeRepository.delete(animeDeleted);

        Optional<Anime> animeOptional = this.animeRepository.findById(animeDeleted.getId());

        Assertions.assertThat(animeOptional).isEmpty();
    }

    @Test
    @DisplayName("Find By Name returns anime when successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful() {
        Anime anime = createAnime();
        Anime animeSaved = this.animeRepository.save(anime);
        String name = animeSaved.getName();

        List<Anime> animes = this.animeRepository.findByName(name);

        Assertions.assertThat(animes)
                .isNotEmpty()
                .contains(animeSaved);
    }

    @Test
    @DisplayName("Save throw ConstraintViolationException when name is empty")
    void save_ThrowConstraintViolationException_Anime_WhenNameIsEmpty() {
        Anime anime = new Anime();
        /* Uma forma de
        Assertions.assertThatThrownBy(() -> this.animeRepository.save(anime))
                .isInstanceOf(ConstraintViolationException.class);
        */

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.animeRepository.save(anime))
                .withMessageContaining("The anime cannot be empty");

    }


    @Test
    @DisplayName("Find By Name returns empty list when no anime is found")
    void findByName_ReturnsEmptyList_WhenAnimeIsNotFound() {
        List<Anime> animes = this.animeRepository.findByName("xaxa");
        Assertions.assertThat(animes).isEmpty();
    }

    private Anime createAnime() {
        return Anime
                .builder()
                .name("Hajime no Ippo")
                .build();
    }
}