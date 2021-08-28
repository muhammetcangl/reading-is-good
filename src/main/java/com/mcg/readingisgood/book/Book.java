package com.mcg.readingisgood.book;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Document(collection = "books")
public class Book {

    @Id
    private String id;

    @NotBlank(message = "Book name cannot be empty!")
    private String name;

    @NotBlank(message = "Author cannot be null or blank!")
    @Size(max = 255, message = "Author is too long!")
    private String author;

    @NotNull(message = "Stock cannot be null!")
    @Min(value = 0, message = "Stock is not valid!")
    private Long stock;

    @NotNull(message = "Price cannot be null!")
    @Min(value = 0, message = "Price is not valid!")
    private Double price;
}
