package com.example.demo.controller;

import com.example.demo.dto.BookDto;
import com.example.demo.service.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class BookControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookService bookService;

    @Test
    public void testBookAdd() throws Exception {
        BookDto bookDto = new BookDto();
        bookDto.setTitle("本草纲目");
        bookDto.setAuthor("李时珍");
        bookDto.setPublishTime(2020);
        bookDto.setBookNum("324-df-32324");
        bookDto.setDesc("medical book");
        Optional<BookDto> mockBookDto = Optional.of(bookDto);

        Mockito.when(bookService.add(Mockito.any(BookDto.class))).thenReturn(mockBookDto);

        String param = "{\"title\":\"狂人日记\",\"author\":\"鲁迅\",\"publishTime\":\"2012\",\"bookNum\":\"3424-23423-2342\",\"desc\":\"description\"}";

        MvcResult mvcResult = mockMvc.perform(post("/book/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(param)
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value("本草纲目"))
                .andReturn();
        ;
    }

    @Test
    public void testBookQuery() throws Exception {

        BookDto bookDto = new BookDto();
        bookDto.setTitle("本草纲目");
        bookDto.setAuthor("李时珍");
        bookDto.setPublishTime(2020);
        bookDto.setBookNum("324-df-32324");
        bookDto.setDesc("medical book");
        Optional<BookDto> mockBookDto = Optional.of(bookDto);

        Mockito.when(bookService.query(1)).thenReturn(mockBookDto);


        mockMvc.perform(get("/book/get/{id}", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value("本草纲目"));
    }

    @Test
    public void testBookList() throws Exception {

        BookDto bookDto = new BookDto();
        bookDto.setTitle("本草纲目");
        bookDto.setAuthor("李时珍");
        bookDto.setPublishTime(2020);
        bookDto.setBookNum("324-df-32324");
        bookDto.setDesc("medical book");

        List<BookDto> bookDtos = new ArrayList();
        bookDtos.add(bookDto);

        Optional<List<BookDto>> bookDtoList = Optional.of(bookDtos);

        Mockito.when(bookService.list()).thenReturn(bookDtoList);

        mockMvc.perform(get("/book/list")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].title").value("本草纲目"));
    }

    @Test
    public void testBookDelete() throws Exception {

        Mockito.when(bookService.delete(Mockito.anyInt())).thenReturn(1);
        mockMvc.perform(delete("/book/delete")
                        .param("id", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("0"));

    }

    @Test
    public void testBookUpdate() throws Exception {

        BookDto bookDto = new BookDto();
        Integer randomPublishTime = Math.abs(new Random().nextInt() % 2024);
        bookDto.setId(1);
        bookDto.setTitle("狂人日记");
        bookDto.setAuthor("鲁迅");
        bookDto.setPublishTime(randomPublishTime);

        BookDto bookReturn = new BookDto();
        BeanUtils.copyProperties(bookDto, bookReturn);

        Optional<BookDto> res = Optional.of(bookReturn);

        Mockito.when(bookService.update(bookDto)).thenReturn(res);

        String param = "{\"id\":1,\"title\":\"狂人日记\",\"author\":\"鲁迅\",\"publishTime\": \"" +randomPublishTime +"\"}";

        mockMvc.perform(put("/book/update")
                        .content(param)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("0"));
    }
}
