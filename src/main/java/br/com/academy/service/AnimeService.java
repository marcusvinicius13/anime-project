package br.com.academy.service;

import br.com.academy.domain.Anime;
import br.com.academy.exception.BadRequestException;
import br.com.academy.mapper.AnimeMapper;
import br.com.academy.repository.AnimeRepository;
import br.com.academy.requests.AnimePostRequestBody;
import br.com.academy.requests.AnimePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository animeRepository;

    public List<Anime> listAll(){
        return animeRepository.findAll();
    }

    public List<Anime> listByName(String name){
        return animeRepository.findByName(name);
    }

    public Anime showOrThrowBadRequestException(Long id){
        return animeRepository
                .findById(id)
                .orElseThrow(() -> new BadRequestException("Anime not Found"));
    }

    @Transactional
    public Anime save(AnimePostRequestBody animePostRequestBody) {
        return animeRepository.save(AnimeMapper.INSTANCE.toAnime(animePostRequestBody));
    }

    public void delete(long id) {
        animeRepository.delete(showOrThrowBadRequestException(id));
    }

    public void replace(AnimePutRequestBody animePutRequestBody) {
        showOrThrowBadRequestException(animePutRequestBody.getId());
        var anime = AnimeMapper.INSTANCE.toAnime(animePutRequestBody);
        animeRepository.save(anime);
    }
}
