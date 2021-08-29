package com.mcg.readingisgood.book;

import com.mcg.readingisgood.dto.BookStockDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public Book save(@Valid @RequestBody Book book) {
        return bookService.saveBook(book);
    }

    @PutMapping
    public ResponseEntity<?> updateBookStock(@Valid @RequestBody BookStockDTO bookStockDTO) {
        bookService.updateBookStock(bookStockDTO);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
