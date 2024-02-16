package com.example.service.serviceImpl;

import com.example.dto.BookDto;
import com.example.eo.BookEo;
import com.example.mapper.BookMapper;
import com.example.service.BookService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Override
    public Optional<BookDto> add(BookDto bookDto) {
        BookEo bookEoInput = new BookEo();
        BeanUtils.copyProperties(bookDto,bookEoInput);
        BookEo bookEoRes = bookMapper.saveOne(bookEoInput);
        BookDto bookDtoRes = new BookDto();
        BeanUtils.copyProperties(bookEoRes,bookDtoRes);
        return Optional.of(bookDtoRes);
    }

    @Override
    public Optional<BookDto> query(Integer id) {

        BookEo bookEoRes = bookMapper.queryById(id);
        BookDto bookDtoRes = new BookDto();
        BeanUtils.copyProperties(bookEoRes,bookDtoRes);
        return Optional.of(bookDtoRes);
    }

    @Override
    public Optional<List<BookDto>> list() {

        List<BookEo> bookEoRes = bookMapper.listAll();
        List<BookDto> bookDtoListRes = new ArrayList<>();
        for(BookEo book : bookEoRes){
            BookDto bookDto = new BookDto();
            BeanUtils.copyProperties(book,bookDto);
            bookDtoListRes.add(bookDto);
        }
        return Optional.of(bookDtoListRes);
    }
}
