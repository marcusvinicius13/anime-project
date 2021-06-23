package br.com.academy.domain;

import br.com.academy.domain.deeppar.UltimoStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parlamentar {

    private Long id;
    private String uri;
    private String nomeCivil;

    private UltimoStatus ultimoStatus;

    private String cpf;
    private Character sexo;
    private String urlWebsite;
    private String redeSocial;
//    private LocalDateTime dataNascimento;
//    private LocalDateTime dataFalecimento;
    private String ufNascimento;
    private String municipioNascimento;
    private String escolaridade;

}







