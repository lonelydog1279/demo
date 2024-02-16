package com.example.demo.controller;

import com.example.demo.dto.BookDto;
import com.example.demo.service.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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


        MvcResult mvcResult = mockMvc.perform(post("/book/add")
                        .param("id", "1")
                        .param("title", "本草纲目")
                        .param("author", "李时珍")
                        .param("publishTime", "2023")
                        .param("bookNum", "324-df-32324")
                        .param("desc", "medical book")
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
}
