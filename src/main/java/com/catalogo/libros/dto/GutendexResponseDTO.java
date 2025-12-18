package com.catalogo.libros.dto;

import java.util.List;

public class GutendexResponseDTO {

    private Integer count;
    private String next;
    private String previous;
    private List<GutendexBookDTO> results;

    public Integer getCount() {
        return count;
    }

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }

    public List<GutendexBookDTO> getResults() {
        return results;
    }
}


