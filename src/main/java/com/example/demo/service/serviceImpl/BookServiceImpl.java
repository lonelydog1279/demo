package com.example.demo.service.serviceImpl;

import com.example.demo.dto.BookDto;
import com.example.demo.eo.BookEo;
import com.example.demo.mapper.BookMapper;
import com.example.demo.service.BookService;
import com.example.demo.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
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
        BeanUtils.copyProperties(bookDto, bookEoInput);
        bookMapper.saveOne(bookEoInput);
        BookDto bookDtoRes = new BookDto();
        BeanUtils.copyProperties(bookEoInput, bookDtoRes);
        return Optional.of(bookDtoRes);
    }

    @Override
    public Optional<BookDto> query(Integer id) {

        BookEo bookEoRes = bookMapper.queryById(id);
        BookDto bookDtoRes = new BookDto();
        BeanUtils.copyProperties(bookEoRes, bookDtoRes);
        return Optional.of(bookDtoRes);
    }

    @Override
    public Optional<List<BookDto>> list() {

        List<BookEo> bookEoRes = bookMapper.listAll();
        List<BookDto> bookDtoListRes = new ArrayList<>();
        for (BookEo book : bookEoRes) {
            BookDto bookDto = new BookDto();
            BeanUtils.copyProperties(book, bookDto);
            bookDtoListRes.add(bookDto);
        }
        return Optional.of(bookDtoListRes);
    }

    @Override
    public Integer delete(Integer id) {

        Integer count = bookMapper.delete(id);
        return count;
    }

    @Override
    public Optional<BookDto> update(BookDto bookDto) {

        BookEo bookEo = bookMapper.queryById(bookDto.getId());
        Integer count = bookMapper.update(bookDto);
        BeanMap beanMap = BeanMap.create(bookEo);
        if (count > 0) {
            BeanUtil.beanCopy(beanMap, bookDto);
        }
        Optional<BookDto> res = Optional.of(bookDto);

        return res;
    }

}
