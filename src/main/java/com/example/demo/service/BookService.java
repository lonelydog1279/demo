package com.example.demo.service;

import com.example.demo.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Optional<BookDto> add(BookDto userDto);

    Optional<BookDto> query(Integer id);

    Optional<List<BookDto>> list();
}
