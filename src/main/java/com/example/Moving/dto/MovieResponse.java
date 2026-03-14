package com.example.Moving.dto;

import lombok.Data;
import java.util.List;

@Data
public class MovieResponse {
    private String status;
    private MovieListData data;

    @Data
    public static class MovieListData {
        private List<MovieListItem> items; // Chú ý: Trang chủ trả về mảng items
    }

    @Data
    public static class MovieListItem {
        private String name;
        private String slug;
        private String thumb_url;
        private int year;
    }
}