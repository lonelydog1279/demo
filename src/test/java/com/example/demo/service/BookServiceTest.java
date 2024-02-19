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
import java.util.Random;

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
        bookReturn.setTitle("The Big Sleep");
        bookReturn.setAuthor("Mr. unknown");
        bookReturn.setPublishTime(2020);
        bookReturn.setBookNum("324-df-32324");
        bookReturn.setDesc("medical book");

        //TODO validate the format,length of isbn
        //TODO validate the publishTime....
        Mockito.when(bookMapper.saveOne(Mockito.any(BookEo.class))).thenReturn(1);

        BookDto bookInput = new BookDto();
        bookInput.setTitle("The Big Sleep");
        bookInput.setAuthor("Mr. unknown");
        bookInput.setPublishTime(2020);
        bookInput.setBookNum("324-df-32324");
        bookInput.setDesc("medical book");

        Optional<BookDto> result = bookService.add(bookInput);

        //TODO result.get canbe replace with a better way
        assertEquals(result.get().getPublishTime(),2020);
    }

    @Test
    public void testBookQuery() {
        BookEo bookReturn = new BookEo();
        bookReturn.setId(1);
        bookReturn.setTitle("The Big Sleep");
        bookReturn.setAuthor("Mr. unknown");
        bookReturn.setPublishTime(2020);
        bookReturn.setBookNum("324-df-32324");
        bookReturn.setDesc("medical book");

        Mockito.when(bookMapper.queryById(Mockito.anyInt())).thenReturn(bookReturn);

        Optional<BookDto> result = bookService.query(1);
        assertEquals("The Big Sleep", result.get().getTitle());
    }

    @Test
    public void testBookListAll() {
        BookEo bookReturn = new BookEo();
        bookReturn.setId(1);
        bookReturn.setTitle("The Big Sleep");
        bookReturn.setAuthor("Mr. unknown");
        bookReturn.setPublishTime(2020);
        bookReturn.setBookNum("324-df-32324");
        bookReturn.setDesc("medical book");

        List<BookEo> bookEoList = new ArrayList<>();
        bookEoList.add(bookReturn);

        Mockito.when(bookMapper.listAll()).thenReturn(bookEoList);

        Optional<List<BookDto>> result = bookService.list();
        assertEquals(Boolean.TRUE.booleanValue(), result.get().size() > 0);
    }

    @Test
    public void testDelete() {

        Mockito.when(bookMapper.delete(Mockito.anyInt())).thenReturn(1);
        assertEquals(1,bookService.delete(1));

    }

    @Test
    public void testUpdate(){
        BookDto bookDto = new BookDto();
        Integer randomPublishTime = Math.abs(new Random().nextInt() % 2024);
        bookDto.setId(1);
        bookDto.setTitle("The Big Sleep");
        bookDto.setAuthor("Mr. unknown");
        bookDto.setPublishTime(randomPublishTime);

        BookEo bookReturn = new BookEo();
        bookReturn.setId(1);
        bookReturn.setTitle("The Big Sleep");
        bookReturn.setAuthor("Mr. unknown");
        bookReturn.setPublishTime(2020);
        bookReturn.setBookNum("324-df-32324");
        bookReturn.setDesc("medical book");

        Mockito.when(bookMapper.update(bookDto)).thenReturn(1);
        Mockito.when(bookMapper.queryById(1)).thenReturn(bookReturn);

        //TODO can add more validation
        Optional<BookDto> update = bookService.update(bookDto);

        BookDto uodatedBookDto = update.get();
        assertEquals(uodatedBookDto.getId(),1);
        assertEquals(uodatedBookDto.getTitle(),"The Big Sleep");
        assertEquals(uodatedBookDto.getAuthor(),"Mr. unknown");
        assertEquals(uodatedBookDto.getPublishTime(),randomPublishTime);

    }

}
