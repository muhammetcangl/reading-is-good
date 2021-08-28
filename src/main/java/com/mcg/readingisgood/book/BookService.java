package com.mcg.readingisgood.book;

import com.mcg.readingisgood.dto.BookStockDTO;
import com.mcg.readingisgood.exception.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBookStock(BookStockDTO bookStockDTO) {
        Book book = bookRepository.findById(bookStockDTO.getId()).orElseThrow(() -> new AppException("Book id is not found!"));
        book.setStock(bookStockDTO.getStock());
        return bookRepository.save(book);
    }
}
