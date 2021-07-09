package br.com.academy.util;

import br.com.academy.requests.AnimePostRequestBody;

public class AnimePostRequestBodyCreator {
    public static AnimePostRequestBody createAnimePostRequestBody() {
        return AnimePostRequestBody
                .builder()
                .name(AnimeCreator.createAnimeToBeSaved().getName())
                .build();
    }
}
