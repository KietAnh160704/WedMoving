package com.example.Moving.dto;

import lombok.Data;
import java.util.List;

@Data
public class MovieResponse {
    private String status;
    private String message;
    private MovieData data;

    @Data
    public static class MovieData {
        private List<MovieItem> items;
        // QUAN TRỌNG: Phải có biến params này thì mới gọi được getParams()
        private MovieParams params;
    }

    @Data
    public static class MovieParams {
        private String type_list;
        private String title; // THÊM DÒNG NÀY VÀO ĐỂ HẾT LỖI getTitle()
        private String slug;
        private MoviePagination pagination;
    }

    @Data
    public static class MoviePagination {
        private int totalItems;
        private int totalItemsPerPage;
        private int currentPage;
        private int totalPages;
    }

    @Data
    public static class MovieItem {
        private String name;
        private String slug;
        private String thumb_url;
        private int year;
        private String episode_current; // Ví dụ: "Tập 10" hoặc "Full"
        private String quality;         // Ví dụ: "HD" hoặc "FHD"
        private String lang;
    }
}