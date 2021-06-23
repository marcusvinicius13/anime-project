package br.com.academy.domain.deeppar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Gabinete {
    private String nome;
    private String predio;
    private String sala;
    private String andar;
    private String telefone;
    private String email;
}
