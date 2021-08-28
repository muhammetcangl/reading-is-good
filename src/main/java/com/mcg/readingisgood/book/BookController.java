package com.mcg.readingisgood.book;

import com.mcg.readingisgood.dto.BookStockDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public Book save(@Valid @RequestBody Book book) {
        return bookService.addBook(book);
    }

    @PutMapping
    public Book updateBookStock(@Valid @RequestBody BookStockDTO bookStockDTO) {
        return bookService.updateBookStock(bookStockDTO);
    }

}
