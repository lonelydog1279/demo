package com.example.demo.service;

import com.example.demo.dto.BookDto;
import com.example.demo.eo.BookEo;
import com.example.demo.mapper.BookMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @MockBean
    private BookMapper bookMapper;

    @Test
    public void testBookAdd() {
        BookEo bookReturn = new BookEo();
        bookReturn.setId(1);
        bookReturn.setTitle("本草纲目");
        bookReturn.setAuthor("李时珍");
        bookReturn.setPublishTime(2020);
        bookReturn.setBookNum("324-df-32324");
        bookReturn.setDesc("medical book");

        Mockito.when(bookMapper.saveOne(Mockito.any(BookEo.class))).thenReturn(bookReturn);

        BookDto bookInput = new BookDto();
        bookInput.setTitle("本草纲目");
        bookInput.setAuthor("李时珍");
        bookInput.setPublishTime(2020);
        bookInput.setBookNum("324-df-32324");
        bookInput.setDesc("medical book");

        Optional<BookDto> result = bookService.add(bookInput);

        assertEquals(bookInput.getTitle(), result.get().getTitle());
    }

    @Test
    public void testBookQuery(){
        BookEo bookReturn = new BookEo();
        bookReturn.setId(1);
        bookReturn.setTitle("本草纲目");
        bookReturn.setAuthor("李时珍");
        bookReturn.setPublishTime(2020);
        bookReturn.setBookNum("324-df-32324");
        bookReturn.setDesc("medical book");

        Mockito.when(bookMapper.queryById(Mockito.anyInt())).thenReturn(bookReturn);

        Optional<BookDto> result = bookService.query(1);
        assertEquals("本草纲目",result.get().getTitle());
    }

    @Test
    public void testUserListAll(){
        BookEo bookReturn = new BookEo();
        bookReturn.setId(1);
        bookReturn.setTitle("本草纲目");
        bookReturn.setAuthor("李时珍");
        bookReturn.setPublishTime(2020);
        bookReturn.setBookNum("324-df-32324");
        bookReturn.setDesc("medical book");

        List<BookEo> bookEoList = new ArrayList<>();
        bookEoList.add(bookReturn);

        Mockito.when(bookMapper.listAll()).thenReturn(bookEoList);

        Optional<List<BookDto>> result = bookService.list();
        assertEquals(Boolean.TRUE.booleanValue(),result.get().size()>0);
    }

}
