package com.aluracursos.Desafio1JavaSpringBoot.service;

public interface IConveirteDatos {

    <T> T obtenerDatos(String json, Class<T> clase);
}
