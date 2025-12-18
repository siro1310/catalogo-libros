package com.catalogo.libros.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class GutendexBookDTO {

    private String title;
    private List<GutendexAuthorDTO> authors;
    private List<String> languages;

    @JsonProperty("download_count")
    private Integer downloadCount;

    public String getTitle() {
        return title;
    }

    public List<GutendexAuthorDTO> getAuthors() {
        return authors;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }
}
