package com.example.Moving.dto;

import lombok.Data;
import java.util.List;

@Data
public class CategoryResponse {
    private String status;
    private String message;
    private CategoryData data; // Lớp vỏ bọc "data" đây rồi!

    @Data
    public static class CategoryData {
        private List<CategoryItem> items; // Danh sách thực sự nằm ở đây
    }

    @Data
    public static class CategoryItem {
        private String name;
        private String slug;
    }
}