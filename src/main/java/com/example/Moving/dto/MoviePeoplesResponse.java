package com.example.Moving.dto;

import lombok.Data;
import java.util.List;

@Data
public class MoviePeoplesResponse {
    private MoviePeoplesData data;

    @Data
    public static class MoviePeoplesData {
        // PHẢI LÀ "peoples" thì Java mới hiểu để map dữ liệu từ JSON vào
        private List<Person> peoples;
    }

    @Data
    public static class Person {
        private String name;
        private String character; // Bạn có thể lấy thêm vai diễn nếu muốn
        private String profile_path; // Đường dẫn ảnh nếu muốn dùng sau này
    }
}