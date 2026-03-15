package com.example.Moving.dto;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class MovieResponse implements Serializable {
    private static final long serialVersionUID = 1L; // Nên có ID này cho mỗi class
    private String status;
    private String message;
    private MovieData data;

    @Data
    public static class MovieData implements Serializable {
        private static final long serialVersionUID = 1L;
        private List<MovieItem> items;
        private MovieParams params;
    }

    @Data
    public static class MovieParams implements Serializable {
        private static final long serialVersionUID = 1L;
        private String type_list;
        private String title;
        private String slug;
        private MoviePagination pagination;
    }

    @Data
    public static class MoviePagination implements Serializable {
        private static final long serialVersionUID = 1L;
        private int totalItems;
        private int totalItemsPerPage;
        private int currentPage;
        private int totalPages;
    }

    @Data
    public static class MovieItem implements Serializable {
        private static final long serialVersionUID = 1L;
        private String name;
        private String slug;
        private String thumb_url;
        private int year;
        private String episode_current;
        private String quality;
        private String lang;
    }
}