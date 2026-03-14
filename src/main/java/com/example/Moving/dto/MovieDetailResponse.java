package com.example.Moving.dto;

import lombok.Data;
import java.util.List;

@Data
public class MovieDetailResponse {
    private MovieDetailData data;

    @Data
    public static class MovieDetailData {
        private MovieItem item;
    }

    @Data
    public static class MovieItem {
        private String name;
        private String origin_name; // Fix lỗi origin_name
        private String slug;
        private String content;
        private String thumb_url;
        private int year;
        private String time;
        private String quality;
        private String lang;
        private String episode_current;
        private String episode_total;
        private List<CategoryItem> category; // Đổi từ genres sang category cho đúng API
        private List<CategoryItem> country;
        private List<String> actor;
        private List<String> director;
        private List<EpisodeServer> episodes; // Danh sách Server (Vietsub, Lồng tiếng)
    }

    @Data
    public static class CategoryItem {
        private String name;
        private String slug;
    }

    @Data
    public static class EpisodeServer {
        private String server_name;
        private List<ServerData> server_data;
    }

    @Data
    public static class ServerData {
        private String name;
        private String slug;
        private String link_embed;
    }
}