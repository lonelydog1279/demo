package com.example.demo.dto;

import lombok.Data;

@Data
public class BookDto {

    private Integer id;

    private String title;

    private String author;

    private Integer publishTime;

    private String bookNum;

    private String desc;
}
