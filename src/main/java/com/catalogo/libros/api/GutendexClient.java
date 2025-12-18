package com.catalogo.libros.api;

import com.catalogo.libros.dto.GutendexResponseDTO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.catalogo.libros.dto.GutendexBookDTO;

import java.util.ArrayList;
import java.util.List;



@Component
public class GutendexClient {

    private final WebClient webClient;

    public GutendexClient() {
        this.webClient = WebClient.builder()
                .baseUrl("https://gutendex.com")
                .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public List<GutendexBookDTO> buscarTodosPorTitulo(String titulo) {

        List<GutendexBookDTO> resultadoFinal = new ArrayList<>();

        String url = "/books/?search=" + titulo;

        while (url != null) {

            GutendexResponseDTO response = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(GutendexResponseDTO.class)
                    .block();

            if (response == null) break;

            resultadoFinal.addAll(response.getResults());

            // Gutendex devuelve URL completa → la adaptamos
            if (response.getNext() != null) {
                url = response.getNext().replace("https://gutendex.com", "");
            } else {
                url = null;
            }
        }

        return resultadoFinal;
    }













}
