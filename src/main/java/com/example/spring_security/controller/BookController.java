package com.example.spring_security.controller;

import com.example.spring_security.entity.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author wenyabing
 * @Date 2019/5/16 11:38
 */
@RestController
@RequestMapping("/book")
public class BookController {
    @GetMapping("/books")
    public ModelAndView books() {
        List<Book> books = new ArrayList<>();
        Book book1 = new Book();
        book1.setId(1);
        book1.setName("三国演义");
        book1.setAuthor("罗贯中");
        Book book2 = new Book();
        book2.setId(2);
        book2.setName("西游记");
        book2.setAuthor("吴承恩");
        Book book3 = new Book();
        book3.setId(3);
        book3.setName("红楼梦");
        book3.setAuthor("曹雪芹");
        Book book4 = new Book();
        book4.setId(4);
        book4.setName("水浒传");
        book4.setAuthor("施耐庵");
        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("books", books);
        modelAndView.setViewName("books");
        return modelAndView;
    }
}
