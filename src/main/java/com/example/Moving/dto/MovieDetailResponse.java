package com.example.Moving.dto;

import lombok.Data;
import java.util.List;

@Data
public class MovieDetailResponse {
    private MovieDetailData data;

    @Data
    public static class MovieDetailData {
        private MovieItem item; // Trang chi tiết trả về 1 item duy nhất
    }

    @Data
    public static class MovieItem {
        private String name;
        private String slug;
        private String content;
        private String thumb_url;
        private String poster_url;
        private int year;    // THÊM DÒNG NÀY
        private String time;   // Thêm luôn cái này cho đủ bộ
        private String quality;
        private String lang;
        private List<EpisodeServer> episodes;
        private String episode_total;   // Tổng số tập (ví dụ: 24 tập)
        private String episode_current;
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
        private String link_m3u8;
        private String link_embed;
    }


}