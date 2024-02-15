package com.example.service.serviceImpl;

import com.example.dto.BookDto;
import com.example.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    @Override
    public Optional<BookDto> add(BookDto userDto) {
        return Optional.empty();
    }

    @Override
    public Optional<BookDto> query(Integer id) {
        return Optional.empty();
    }

    @Override
    public Optional<List<BookDto>> list() {
        return Optional.empty();
    }
}
