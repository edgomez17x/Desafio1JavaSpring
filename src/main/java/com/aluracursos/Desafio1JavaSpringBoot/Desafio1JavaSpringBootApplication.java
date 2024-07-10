package com.aluracursos.Desafio1JavaSpringBoot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Desafio1JavaSpringBootApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Desafio1JavaSpringBootApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		new Principal().processBooks();
	}
}
