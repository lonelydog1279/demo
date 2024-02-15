package com.example.controller;

import com.example.dto.BookDto;
import com.example.dto.Response;
import com.example.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/book")
public class BookController {

    @Resource
    BookService bookService;

    @PostMapping("/add")
    @ResponseBody
    public Response add(BookDto bookDto){
        Response response = new Response();
        Optional<BookDto> result = bookService.add(bookDto);
        response.setData(result.get());
        return response;
    }

    @GetMapping("/get/{id}")
    @ResponseBody
    public Response query(@PathVariable("id") Integer id){
        Response response = new Response();
        Optional<BookDto> result = bookService.query(id);
        response.setData(result.get());
        return response;
    }

    @GetMapping("/list")
    @ResponseBody
    public Response list(){
        Response response = new Response();
        Optional<List<BookDto>> result =bookService.list();
        response.setData(result.get());
        return response;
    }
}
