package com.example.demo.controller;

import com.example.demo.dto.BookDto;
import com.example.demo.dto.Response;
import com.example.demo.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/book")
public class BookController {

    @Resource
    BookService bookService;

    @PostMapping("/add")
    @ResponseBody
    public Response add(@RequestBody BookDto bookDto) {

        //TODO handle exception
        Optional<BookDto> result = bookService.add(bookDto);
        Response response = Response.success(result.orElseGet(BookDto::new));
        return response;
    }

    @GetMapping("/get/{id}")
    @ResponseBody
    public Response query(@PathVariable("id") Integer id) {

        Optional<BookDto> result = bookService.query(id);
        Response response = Response.success(result.orElseGet(BookDto::new));
        return response;
    }

    @GetMapping("/list")
    @ResponseBody
    public Response list() {

        Optional<List<BookDto>> result = bookService.list();
        Response response = Response.success(result.orElseGet(ArrayList<BookDto>::new));
        return response;
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public Response list(@RequestBody BookDto bookDto) {
        Integer count = bookService.delete(bookDto.getId());
        HashMap<String, Integer> map = new HashMap<>();
        map.put("count", count);
        Response response = Response.success(map);
        return response;
    }

    @PutMapping("/update")
    @ResponseBody
    public Response update(@RequestBody BookDto bookDto) {

        Optional<BookDto> update = bookService.update(bookDto);
        Response response = Response.success(update.orElseGet(BookDto::new));
        return response;
    }


}
