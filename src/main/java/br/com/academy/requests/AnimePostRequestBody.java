package br.com.academy.requests;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AnimePostRequestBody {
    @NotEmpty(message = "The anime name cannot be null or empty")
    private String name;
}
