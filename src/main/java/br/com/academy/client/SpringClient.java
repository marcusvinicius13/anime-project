package br.com.academy.client;

import br.com.academy.domain.Anime;
import br.com.academy.domain.Parlamentar;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        Anime anime = new RestTemplate().getForEntity("http://localhost:8080/animes/1", Anime.class).getBody();

        Anime object = new RestTemplate().getForObject("http://localhost:8080/animes/{id}", Anime.class, 1);

        // Trabalhando com Arrays
        Anime[] animes = new RestTemplate().getForObject("http://localhost:8080/animes/all", Anime[].class);

        // Trabalhando com Listas
        ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("http://localhost:8080/animes/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {});

        log.info(anime);
        log.info(exchange.getBody());
        log.info(Arrays.toString(animes));
        log.info(object);


//      Mandando uma requisição POST, com o postForObject

//      Anime kingdon = Anime.builder().name("kingdom").build();
//      Anime kindomDaved = new RestTemplate().postForObject("http://localhost:8080/animes", kingdon, Anime.class);
//      log.info("saved anime {}",kindomDaved);

//      Mandando uma requisição POST, com o exchange
        Anime samuraiChaploo = Anime.builder().name("Samurai Shamploo").build();
        ResponseEntity<Anime> samuraiShamplooSaved = new RestTemplate().exchange("http://localhost:8080/animes",
                HttpMethod.POST,
                new HttpEntity<>(samuraiChaploo, createJsonHeader()),
                Anime.class);
        log.info("saved anime {}",samuraiShamplooSaved);

        Anime animeToBeUpdated = samuraiShamplooSaved.getBody();
        animeToBeUpdated.setName("Samurai Shamploo 2");

        ResponseEntity<Void> samuraiShamplooUpdated = new RestTemplate().exchange("http://localhost:8080/animes",
                HttpMethod.PUT,
                new HttpEntity<>(animeToBeUpdated, createJsonHeader()),
                Void.class);
        log.info("updated anime {}", samuraiShamplooUpdated);


        ResponseEntity<Void> samuraiShamplooDeleted = new RestTemplate().exchange("http://localhost:8080/animes/{id}",
                HttpMethod.DELETE,
                null,
                Void.class,
                animeToBeUpdated.getId());
        log.info("updated anime {}", samuraiShamplooDeleted);
    }

    private static HttpHeaders createJsonHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }

    private Parlamentar retornaParlamentares() {
        String parlamentarEntity = new RestTemplate()
                .getForEntity("https://dadosabertos.camara.leg.br/api/v2/deputados/204554", String.class).getBody();


        String[] split = parlamentarEntity.split("\\{\"dados\":");
        String[] split1 = split[1].split(",\"links\":\\[");


//        log.info(new RestTemplate()
//                .getForEntity("https://dadosabertos.camara.leg.br/api/v2/deputados/204554", Object.class));

//       Parlamentar parlamentar = new RestTemplate().getForObject("https://dadosabertos.camara.leg.br/api/v2/deputados/204554", Parlamentar.class);

        //log.info(parlamentarEntity);
//        log.info(parlamentar);


        ObjectMapper objectMapper = new ObjectMapper();

        String par = split1[0];

        boolean palavra = "Java322".matches("^Java.*");
        System.out.println(palavra);

        split1[0]. split("");
        try {
            Parlamentar parlamentar = objectMapper.readValue(par, Parlamentar.class);

            log.info(parlamentar);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}


