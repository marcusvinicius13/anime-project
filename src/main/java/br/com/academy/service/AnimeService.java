package br.com.academy.service;

import br.com.academy.domain.Anime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AnimeService {

    private static final List<Anime> animes;
    static {
        animes =  new ArrayList<>(List.of(new Anime(1L, "DBZ"), new Anime(2L, "Berserk")));
    }

    public List<Anime> listAll(){
        return animes;
    }

    public Anime show(Long id){
        return animes.stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not Found"));
    }

    public Anime save(Anime anime) {
        anime.setId(ThreadLocalRandom.current().nextLong(3, 1000));
        animes.add(anime);
        return anime;
    }

    public void delete(long id) {
        animes.remove(show(id));
    }

    public void replace(Anime anime) {
        delete(anime.getId());
        animes.add(anime);
    }
}
