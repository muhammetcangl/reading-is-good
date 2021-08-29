package com.mcg.readingisgood.book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDTO {

    private String id;
    private String name;
    private String author;
    private Long page;
    private Double price;
}
