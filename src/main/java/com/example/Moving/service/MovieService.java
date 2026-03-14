package com.example.Moving.service;

import com.example.Moving.dto.MovieResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import com.example.Moving.dto.MovieDetailResponse;
import com.example.Moving.dto.MoviePeoplesResponse;
import org.springframework.core.ParameterizedTypeReference; // Fix lỗi ParameterizedTypeReference
import java.util.List; // Fix lỗi List
import com.example.Moving.dto.CategoryResponse.CategoryItem; // Fix lỗi CategoryItem (lấy từ inner class của DTO)
import reactor.core.publisher.Mono;
import com.example.Moving.dto.CategoryResponse;

@Service
public class MovieService {

    private final WebClient webClient;

    public MovieService(WebClient webClient) {
        this.webClient = webClient;
    }

    // Lấy danh sách phim mới cập nhật
    public Mono<MovieResponse> getHomeData() {
        return this.webClient.get()
                .uri("/v1/api/home") // Đúng thứ tự v1 rồi đến api
                .retrieve()
                .bodyToMono(MovieResponse.class);
    }
    public Mono<MovieResponse> searchMovies(String keyword) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/api/tim-kiem")
                        .queryParam("keyword", keyword)
                        .build())
                .retrieve()
                .bodyToMono(MovieResponse.class);
    }

    public Mono<MovieDetailResponse> getDetail(String slug) { // Sửa kiểu trả về ở đây
        return this.webClient.get()
                .uri("/v1/api/phim/" + slug)
                .retrieve()
                .bodyToMono(MovieDetailResponse.class); // Sửa class hứng ở đây
    }

    // 2. Hình ảnh phim (Backdrop, Still)
    public Mono<String> getImages(String slug) {
        return webClient.get().uri("/v1/api/phim/" + slug + "/images").retrieve().bodyToMono(String.class);
    }


    public Mono<MoviePeoplesResponse> getPeoples(String slug) {
        return this.webClient.get()
                .uri("/v1/api/phim/" + slug + "/peoples")
                .retrieve()
                .bodyToMono(MoviePeoplesResponse.class)
                .onErrorResume(e -> Mono.empty()); // Nếu API OPhim sập (500), trả về Mono trống
    }

    // Lấy danh sách thể loại để hiện lên Menu
    public Mono<String> getAllGenres() {
        return this.webClient.get().uri("/v1/api/the-loai").retrieve().bodyToMono(String.class);
    }

    // Lấy phim theo tiêu chí bất kỳ (thể loại, quốc gia, năm)
    public Mono<MovieResponse> getMoviesByFilter(String category, String slug) {
        // category có thể là 'the-loai', 'quoc-gia', 'danh-sach', 'nam-phat-hanh'
        return this.webClient.get()
                .uri("/v1/api/" + category + "/" + slug)
                .retrieve()
                .bodyToMono(MovieResponse.class);
    }


    public Mono<CategoryResponse> getGenres() {
        return this.webClient.get()
                .uri("/v1/api/the-loai")
                .retrieve()
                .bodyToMono(CategoryResponse.class); // Trả về cả Object CategoryResponse
    }

    public Mono<CategoryResponse> getCountries() {
        return this.webClient.get()
                .uri("/v1/api/quoc-gia")
                .retrieve()
                .bodyToMono(CategoryResponse.class);
    }
}