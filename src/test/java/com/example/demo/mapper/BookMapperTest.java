package com.example.demo.mapper;

import com.example.demo.dto.BookDto;
import com.example.demo.eo.BookEo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookMapperTest {

    @Autowired
    private BookMapper bookMapper;

    @Test
    public void testBookAdd() {
        BookEo bookEo = new BookEo();
        bookEo.setTitle("本草纲目");
        bookEo.setAuthor("李时珍");
        bookEo.setPublishTime(2020);
        bookEo.setBookNum("324-df-32324");
        bookEo.setDesc("medical book");

        bookMapper.saveOne(bookEo);

        assertNotNull(bookEo.getId());
        assertEquals("本草纲目", bookEo.getTitle());

    }

    @Test
    public void testQueryById() {

        BookEo bookEo = bookMapper.queryById(2);

        assertEquals("本草纲目", bookEo.getTitle());

    }

    @Test
    public void testListAll() {

        List<BookEo> bookEos = bookMapper.listAll();

        assertEquals("本草纲目", bookEos.get(1).getTitle());
    }

    @Test
    public void testDelete() {

        BookEo bookEo = new BookEo();
        bookEo.setTitle("本草纲目");
        bookEo.setAuthor("李时珍");
        bookEo.setPublishTime(2020);
        bookEo.setBookNum("324-df-32324");
        bookEo.setDesc("medical book");

        bookMapper.saveOne(bookEo);

        Integer id = bookEo.getId();
        Integer count = bookMapper.delete(id);
        assertEquals(1, count);
    }
    
    @Test
    public void testUpdate(){

        BookEo bookEo = new BookEo();
        Integer randomPublishTime = Math.abs(new Random().nextInt() % 2024);
        bookEo.setId(1);
        bookEo.setTitle("狂人日记");
        bookEo.setAuthor("鲁迅");
        bookEo.setPublishTime(randomPublishTime);

        BookDto bookDto = new BookDto();
        BeanUtils.copyProperties(bookEo,bookDto);

        Integer count = bookMapper.update(bookDto);

        assertTrue(count > 0);

    }

}
