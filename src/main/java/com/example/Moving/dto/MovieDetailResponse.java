package com.example.Moving.dto;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class MovieDetailResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private MovieDetailData data;

    @Data
    public static class MovieDetailData implements Serializable {
        private static final long serialVersionUID = 1L;
        private MovieItem item;
    }

    @Data
    public static class MovieItem implements Serializable {
        private static final long serialVersionUID = 1L;
        private String name;
        private String origin_name;
        private String slug;
        private String content;
        private String thumb_url;
        private int year;
        private String time;
        private String quality;
        private String lang;
        private String episode_current;
        private String episode_total;
        private List<CategoryItem> category;
        private List<CategoryItem> country;
        private List<String> actor;
        private List<String> director;
        private List<EpisodeServer> episodes;
    }

    @Data
    public static class CategoryItem implements Serializable {
        private static final long serialVersionUID = 1L;
        private String name;
        private String slug;
    }

    @Data
    public static class EpisodeServer implements Serializable {
        private static final long serialVersionUID = 1L;
        private String server_name;
        private List<ServerData> server_data;
    }

    @Data
    public static class ServerData implements Serializable {
        private static final long serialVersionUID = 1L;
        private String name;
        private String slug;
        private String link_embed;
    }
}