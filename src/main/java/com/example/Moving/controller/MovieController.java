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
    public String getIndex(@RequestParam(defaultValue = "1") int page, Model model) {
        // 1. Gọi service và truyền số page vào (Nhớ sửa service nhận int page như mình hướng dẫn nhé)
        MovieResponse response = movieService.getHomeData(page).block();

        if (response != null && response.getData() != null) {
            // 2. Gửi danh sách phim
            model.addAttribute("movies", response.getData().getItems());

            // 3. Lấy số trang hiện tại từ API gửi sang HTML
            if (response.getData().getParams() != null && response.getData().getParams().getPagination() != null) {
                int currentPage = response.getData().getParams().getPagination().getCurrentPage();
                model.addAttribute("currentPage", currentPage);
            } else {
                // Nếu API không trả về params, mặc định là trang hiện tại Kiệt đang yêu cầu
                model.addAttribute("currentPage", page);
            }
        }

        MovieResponse vnResponse = movieService.getMoviesByCountry("viet-nam", 1).block();

        if (response != null && response.getData() != null) {
            model.addAttribute("movies", response.getData().getItems());

            // Gửi dữ liệu phân trang cho hàng phim chính
            if (response.getData().getParams() != null && response.getData().getParams().getPagination() != null) {
                model.addAttribute("currentPage", response.getData().getParams().getPagination().getCurrentPage());
            } else {
                model.addAttribute("currentPage", page);
            }
        }

        // 3. Gửi danh sách phim Việt Nam sang Model
        if (vnResponse != null && vnResponse.getData() != null) {
            model.addAttribute("moviesVN", vnResponse.getData().getItems());
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
                             @RequestParam(defaultValue = "1") String ep,
                             @RequestParam(defaultValue = "0") int sv,
                             Model model) {
        try {
            MovieDetailResponse response = movieService.getDetail(slug).block();
            if (response != null && response.getData() != null) {
                var movie = response.getData().getItem();
                model.addAttribute("movie", movie);
                model.addAttribute("selectedSv", sv);
                model.addAttribute("currentEpSlug", ep);

                // Lấy server được chọn
                var currentServer = movie.getEpisodes().get(sv);
                model.addAttribute("currentServer", currentServer);

                // Lấy tập phim trong server đó
                var episodeData = currentServer.getServer_data().stream()
                        .filter(e -> e.getSlug().equals(ep))
                        .findFirst()
                        .orElse(currentServer.getServer_data().get(0));

                model.addAttribute("currentLink", episodeData.getLink_embed());
            }
        } catch (Exception e) {
            return "redirect:/";
        }
        return "watch";
    }

    @GetMapping("/filter/{category}/{slug}")
    public String filterMovies(@PathVariable String category,
                               @PathVariable String slug,
                               @RequestParam(defaultValue = "1") int page,
                               Model model) {
        try {
            // Truyền đủ 3 tham số
            MovieResponse response = movieService.getMoviesByFilter(category, slug, page).block();

            if (response != null && response.getData() != null) {
                model.addAttribute("movies", response.getData().getItems());

                // Lấy Title an toàn
                var params = response.getData().getParams();
                if (params != null) {
                    model.addAttribute("title", params.getTitle());
                    if (params.getPagination() != null) {
                        model.addAttribute("currentPage", params.getPagination().getCurrentPage());
                    }
                }

                // Gửi dữ liệu để nút Phân trang biết đường mà tạo link
                model.addAttribute("currentCategory", category);
                model.addAttribute("currentSlug", slug);
            }
        } catch (Exception e) {
            model.addAttribute("movies", Collections.emptyList());
            model.addAttribute("currentPage", 1);
        }
        return "index";
    }
}