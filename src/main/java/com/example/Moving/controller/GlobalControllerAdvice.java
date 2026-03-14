package com.example.Moving.controller;

import com.example.Moving.dto.CategoryResponse;
import com.example.Moving.service.MovieService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import java.util.Collections;

@ControllerAdvice
public class GlobalControllerAdvice {
    private final MovieService movieService;

    public GlobalControllerAdvice(MovieService movieService) {
        this.movieService = movieService;
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        try {
            CategoryResponse genresRes = movieService.getGenres().block();
            CategoryResponse countriesRes = movieService.getCountries().block();

            // Lấy List items từ trong .getData()
            model.addAttribute("allGenres", (genresRes != null && genresRes.getData() != null)
                    ? genresRes.getData().getItems() : Collections.emptyList());
            model.addAttribute("allCountries", (countriesRes != null && countriesRes.getData() != null)
                    ? countriesRes.getData().getItems() : Collections.emptyList());
        } catch (Exception e) {
            model.addAttribute("allGenres", Collections.emptyList());
            model.addAttribute("allCountries", Collections.emptyList());
        }
    }
}