package com.example.demo.mapper;


import com.example.demo.eo.BookEo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookMapper {

    BookEo saveOne(BookEo book);

    BookEo queryById(Integer id);

    List<BookEo> listAll();

}
