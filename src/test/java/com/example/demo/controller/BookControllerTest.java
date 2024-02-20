package com.example.demo.controller;

import com.example.demo.dto.BookDto;
import com.example.demo.exception.ExceptionEnum;
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

import static org.mockito.Mockito.doThrow;
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
        bookDto.setTitle("The Big Sleep");
        bookDto.setAuthor("Mr. unknown");
        bookDto.setPublishTime(2020);
        bookDto.setBookNum("324-df-32324");
        bookDto.setDesc("medical book");
        Optional<BookDto> mockBookDto = Optional.of(bookDto);

        Mockito.when(bookService.add(Mockito.any(BookDto.class))).thenReturn(mockBookDto);

        String param = "{\"title\":\"The Big Sleep\",\"author\":\"Mr. unknown\",\"publishTime\":\"2020\",\"bookNum\":\"324-df-32324\",\"desc\":\"medical book\"}";

        MvcResult mvcResult = mockMvc.perform(post("/book/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(param)
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value("The Big Sleep"))
                .andReturn();
        ;
    }

    @Test
    public void testBookQuery() throws Exception {

        BookDto bookDto = new BookDto();
        bookDto.setTitle("The Big Sleep");
        bookDto.setAuthor("Mr. unknown");
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value("The Big Sleep"));
    }

    @Test
    public void testBookList() throws Exception {

        BookDto bookDto = new BookDto();
        bookDto.setTitle("The Big Sleep");
        bookDto.setAuthor("Mr. unknown");
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].title").value("The Big Sleep"));
    }

    @Test
    public void testBookDelete() throws Exception {

        String param = "{\"id\": 1}";

        Mockito.when(bookService.delete(Mockito.anyInt())).thenReturn(1);
        mockMvc.perform(delete("/book/delete")
                        .content(param)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("0"));

    }

    @Test
    public void testBookUpdate() throws Exception {

        BookDto bookDto = new BookDto();
        Integer randomPublishTime = Math.abs(new Random().nextInt() % 2024);
        bookDto.setId(1);
        bookDto.setTitle("The Big Sleep");
        bookDto.setAuthor("Mr. unknown");
        bookDto.setPublishTime(randomPublishTime);

        BookDto bookReturn = new BookDto();
        BeanUtils.copyProperties(bookDto, bookReturn);

        Optional<BookDto> res = Optional.of(bookReturn);

        Mockito.when(bookService.update(bookDto)).thenReturn(res);

        String param = "{\"id\":1,\"title\":\"The Big Sleep\",\"author\":\"Mr. unknown\",\"publishTime\": \"" + randomPublishTime + "\"}";

        mockMvc.perform(put("/book/update")
                        .content(param)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("0"));
    }

    @Test
    public void testGlobalExceptionHandler() throws Exception {
        doThrow(new RuntimeException("unknown exception")).when(bookService).list();

        mockMvc.perform(get("/book/list"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ExceptionEnum.INTERNAL_SERVER_ERROR.getResultCode()));
    }
}
