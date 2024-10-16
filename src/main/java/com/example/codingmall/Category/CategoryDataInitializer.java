package com.example.codingmall.Category;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

// 실행될 때 항상 카테고리 항목이 존재하도록 하는 클래스
@Configuration
@RequiredArgsConstructor
public class CategoryDataInitializer {

    private final CategoryService categoryService;

    @Bean
    public ApplicationRunner initializeCategories(){
        return args ->{
            if (categoryService.findAllCategories().isEmpty()){
                // 상위 카테고리
                Category board = new Category("board", null,LocalDateTime.now()); // 회로
                Category books = new Category("Books", null, LocalDateTime.now()); // 책

                // 하위 카테고리
                Category aduino = new Category("aduino", board, LocalDateTime.now());// 놀랍게도 아두이노;;
                Category rasberrypi = new Category("Rasberrypi", board, LocalDateTime.now());
                Category low = new Category("low", books, LocalDateTime.now());
                Category middle = new Category("middle", books, LocalDateTime.now());
                Category high = new Category("high", books, LocalDateTime.now());

                categoryService.saveCategory(board);
                categoryService.saveCategory(books);
                categoryService.saveCategory(aduino);
                categoryService.saveCategory(rasberrypi);
                categoryService.saveCategory(low);
                categoryService.saveCategory(middle);
                categoryService.saveCategory(high);


            }
        };
    }
}
