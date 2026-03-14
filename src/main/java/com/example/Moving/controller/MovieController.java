package com.example.Moving.controller;

import java.util.Collections; // Dòng này cực kỳ quan trọng để sửa lỗi của Kiệt
import java.util.List;
import com.example.Moving.dto.MovieResponse;
import com.example.Moving.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Đã sửa import chuẩn của Spring
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.Moving.dto.MovieDetailResponse;
import com.example.Moving.dto.MoviePeoplesResponse;


@Controller
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/")
    public String getIndex(Model model) {
        MovieResponse response = movieService.getHomeData().block();
        if (response != null && response.getData() != null) {
            model.addAttribute("movies", response.getData().getItems());
        }
        return "index";
    }

    @GetMapping("/search")
    public String search(@RequestParam String keyword, Model model) {
        MovieResponse response = movieService.searchMovies(keyword).block();
        if (response != null && response.getData() != null) {
            model.addAttribute("movies", response.getData().getItems());
            model.addAttribute("searchTerm", keyword);
        }
        return "index";
    }

    // --- TRANG CHI TIẾT PHIM ---
    @GetMapping("/phim/{slug}")
    public String showDetail(@PathVariable String slug, Model model) {
        // 1. Lấy thông tin phim (Bắt buộc phải có)
        MovieDetailResponse detail = movieService.getDetail(slug).block();

        if (detail != null && detail.getData() != null) {
            model.addAttribute("movie", detail.getData().getItem());

            // 2. Lấy diễn viên (Dùng try-catch hoặc xử lý lỗi để không làm sập trang)
            try {
                MoviePeoplesResponse peoples = movieService.getPeoples(slug)
                        .onErrorReturn(new MoviePeoplesResponse()) // Nếu lỗi thì trả về đối tượng rỗng
                        .block();

                if (peoples != null && peoples.getData() != null) {
                    model.addAttribute("actors", peoples.getData().getPeoples());
                } else {
                    model.addAttribute("actors", Collections.emptyList());
                }
            } catch (Exception e) {
                // Nếu API diễn viên lỗi, gán danh sách rỗng để HTML không lỗi
                model.addAttribute("actors", java.util.Collections.emptyList());
                System.err.println("Lỗi API diễn viên nhưng vẫn cho xem phim: " + e.getMessage());
            }

            return "detail";
        }
        return "error";
    }

    // --- TRANG XEM PHIM ---
    @GetMapping("/xem-phim/{slug}")
    public String watchMovie(@PathVariable String slug,
                             @RequestParam(required = false) String ep,
                             Model model) {
        MovieDetailResponse detail = movieService.getDetail(slug).block();
        MoviePeoplesResponse peoples = movieService.getPeoples(slug).block();

        if (detail != null && detail.getData() != null) {
            var item = detail.getData().getItem();
            model.addAttribute("movie", item);

            // Logic lấy tập phim
            var servers = item.getEpisodes().get(0).getServer_data();
            var currentEp = servers.stream()
                    .filter(e -> e.getSlug().equals(ep))
                    .findFirst()
                    .orElse(servers.get(0));

            model.addAttribute("currentLink", currentEp.getLink_embed());
            model.addAttribute("currentEpSlug", currentEp.getSlug());

            if (peoples != null && peoples.getData() != null) {
                model.addAttribute("actors", peoples.getData().getPeoples());
            }
            return "watch"; // Trả về trang watch.html
        }
        return "error";
    }

    @GetMapping("/filter/{type}/{slug}")
    public String filter(@PathVariable String type, @PathVariable String slug, Model model) {
        MovieResponse response = movieService.getMoviesByFilter(type, slug).block();
        if (response != null && response.getData() != null) {
            model.addAttribute("movies", response.getData().getItems());
            model.addAttribute("title", "Kết quả cho: " + slug);
        }
        return "index";
    }
}