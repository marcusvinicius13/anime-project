package br.com.academy.controller;

import br.com.academy.domain.Anime;
import br.com.academy.requests.AnimePostRequestBody;
import br.com.academy.requests.AnimePutRequestBody;
import br.com.academy.service.AnimeService;
import br.com.academy.util.DateUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("animes")
@Log4j2
@AllArgsConstructor // Todos os atributos final da classe são injetados via construtor
public class AnimeController {

    private final DateUtil dateUtil;
    private final AnimeService animeService;

    // Sem pegar o usuário que vem na requisição autenticado
    @GetMapping(path = "by-id/{id}")
    public ResponseEntity<Anime> show(@PathVariable Long id) {
        return ResponseEntity.ok(animeService.showOrThrowBadRequestException(id));
    }

    // Pegando o usuário logado, está quebrando os testes por isso foi removido
    @GetMapping(path = "/{id}")
    public ResponseEntity<Anime> showAuthenticationPricipal(@PathVariable Long id,
                                                            @AuthenticationPrincipal UserDetails userDetails) {
        log.info(userDetails);
        return ResponseEntity.ok(animeService.showOrThrowBadRequestException(id));
    }


    @GetMapping(path = "/find")
    public ResponseEntity<List<Anime>> show(@RequestParam String name) {
        return ResponseEntity.ok(animeService.showByName(name));
    }

    @GetMapping
    @Operation(
            summary = "List all animes paginated",
            description = "The default size is 20, use the parameter size to change the default value",
            tags = {"anime"}
    )
    public ResponseEntity<Page<Anime>> list(@ParameterObject Pageable pageable) {
        return new ResponseEntity<>(animeService.listAll(pageable), HttpStatus.OK);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<Anime>> listAll() {
        return new ResponseEntity<>(animeService.listAllNonPageable(), HttpStatus.OK);
    }

    @PostMapping("/admin")
   // @PreAuthorize("hasRole('ADMIN')") // Só autoriza os usuários com a role de ADMIN
    public ResponseEntity<Anime> create(@RequestBody @Valid AnimePostRequestBody anime) {
        return new ResponseEntity<>(animeService.save(anime), HttpStatus.CREATED);
    }

    @PutMapping("/admin")
    public ResponseEntity<Void> replace(@RequestBody AnimePutRequestBody animePutRequestBody) {
        animeService.replace(animePutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/admin/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "When Anime Does not Exists in the Database"),
    })
    public ResponseEntity<Void> destroy(@PathVariable long id) {
        animeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}