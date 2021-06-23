package br.com.academy.domain.deeppar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UltimoStatus {
    private Long id;
    private String uri;
    private String nome;
    private String siglaPartido;
    private String uriPartido;
    private String siglaUf;
    private Long idLegislatura;
    private String urlFoto;
    private String email;
//    private LocalDateTime data;
    private String nomeEleitoral;

    private Gabinete gabinete;

    private String situacao;
    private String condicaoEleitoral;
    private String descricaoStatus;
}
