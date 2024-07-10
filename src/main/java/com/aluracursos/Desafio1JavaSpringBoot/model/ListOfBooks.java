package com.aluracursos.Desafio1JavaSpringBoot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ListOfBooks(
        Integer count,
        String next,
        String previous,
        List<Book> results
) {
}
