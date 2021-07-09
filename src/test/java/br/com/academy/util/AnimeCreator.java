package br.com.academy.util;

import br.com.academy.domain.Anime;

public class AnimeCreator {

    public static  Anime createAnimeToBeSaved(){
        return Anime.builder()
                .name("Hijime no Ippo")
                .build();
    }

    public static Anime createValidAnime(){
        return Anime.builder()
                .name("Hijime no Ippo")
                .id(1L)
                .build();
    }

    public static Anime createValidUpdateAnime(){
        return Anime.builder()
                .name("Hijime no Ippo Atualizado")
                .id(1L)
                .build();
    }
}
