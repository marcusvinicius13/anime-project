package br.com.academy.repository;

import br.com.academy.domain.Anime;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AnimeRepository {
    List<Anime> listAll();
}
