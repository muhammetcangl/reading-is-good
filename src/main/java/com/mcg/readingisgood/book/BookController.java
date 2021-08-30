package com.mcg.readingisgood.book;

import com.mcg.readingisgood.dto.BookStockDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookDTO> save(@Valid @RequestBody Book book) {
        return ResponseEntity.ok(BookMapper.INSTANCE.toBookDTO(bookService.saveBook(book)));
    }

    @PutMapping
    public ResponseEntity<?> updateBookStock(@Valid @RequestBody BookStockDTO bookStockDTO) {
        bookService.updateBookStock(bookStockDTO);
        return ResponseEntity.ok().build();
    }

}
