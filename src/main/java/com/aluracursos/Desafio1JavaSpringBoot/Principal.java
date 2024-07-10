package com.aluracursos.Desafio1JavaSpringBoot;

import com.aluracursos.Desafio1JavaSpringBoot.model.Book;
import com.aluracursos.Desafio1JavaSpringBoot.model.ListOfBooks;
import com.aluracursos.Desafio1JavaSpringBoot.service.ConsumoAPI;
import com.aluracursos.Desafio1JavaSpringBoot.service.ConvierteDatos;

import javax.xml.transform.URIResolver;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner scanner = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private static final String URL = "https://gutendex.com/books/";

    public void processBooks(){
        System.out.println("""
                Elija la opción para realizar una acción:
                1.- Obtener el top 10 de los libros.
                2.- Busqueda de Libro y sus estadisticas
                
                Teclea cualquier cosa diferente al menu para salir.
                """);
        var option = scanner.nextInt();
        scanner.nextLine();
        switch (option){
            case 1:{
                getTop10();
                break;
            }
            case 2:{
                System.out.println("Escriba el titulo o parte del titulo para buscar:");
                String searchedBook = scanner.nextLine();
                searchForBook(searchedBook);
                break;
            }
        }
    }

    private void searchForBook(String searchedBook){
        var json =consumoAPI.obtenerDatos(URL + "?search=" + URLEncoder.encode(searchedBook, StandardCharsets.UTF_8));
        String nextPage;
        List<Book> bookList = new ArrayList<>();
        do{
            ListOfBooks listOfBooks = convierteDatos.obtenerDatos(json, ListOfBooks.class);
            nextPage = listOfBooks.next();
            listOfBooks.results()
                    .forEach(lob -> System.out.println("Titulo: " + lob.title() + " con " + lob.downloadCount() + " descarga(s)"));
            bookList.addAll(listOfBooks.results());
        }while(nextPage != null);
        DoubleSummaryStatistics dss = bookList.stream()
                .filter(b -> b.downloadCount() > 0.0)
                .collect(Collectors.summarizingDouble(Book::downloadCount));
        System.out.println("De los " + dss.getCount() + " titulo(s) mostrado(s)");
        System.out.println("El total de descargas es de: " + dss.getSum());
        System.out.println("La mayor cantidad de descargas es de: " + dss.getMax());
        System.out.println("La menor cantidad de descargas es de: " + dss.getMin());
    }

    private void getTop10(){
        List<Book> top10Book = new ArrayList<>();
        String nextPage;
        int i = 0;
        do{
            var json =consumoAPI.obtenerDatos(URL);
            ListOfBooks listOfBooks = convierteDatos.obtenerDatos(json, ListOfBooks.class);
            nextPage = listOfBooks.next();
            listOfBooks.results().stream()
                    .sorted(Comparator.comparing(Book::downloadCount).reversed())
                    .limit(10)
                    .forEach(top10Book::add);
            i++;
            System.out.println("Leyendo página " + i);
        }while(nextPage == null);
        top10Book.stream()
                .sorted(Comparator.comparing(Book::downloadCount).reversed())
                .limit(10)
                .forEach(System.out::println);
    }
}
