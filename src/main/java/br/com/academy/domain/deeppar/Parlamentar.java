package br.com.academy.domain.deeppar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private List<String> redeSocial;
    private String dataNascimento;
    private String dataFalecimento;
    private String ufNascimento;
    private String municipioNascimento;
    private String escolaridade;

}







