package com.example.mapper;


import com.example.eo.BookEo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookMapper {

    BookEo saveOne(BookEo book);

    BookEo queryById(Integer id);

    List<BookEo> listAll();

}
