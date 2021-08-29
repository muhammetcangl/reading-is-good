package com.mcg.readingisgood.book;

import com.mcg.readingisgood.dto.BookStockDTO;
import com.mcg.readingisgood.exception.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public void updateBookStock(BookStockDTO bookStockDTO) {
        bookRepository.updateBookStock(bookStockDTO.getId(), bookStockDTO.getStock());
    }

    public Optional<Book> findById(String id) {
        return bookRepository.findById(id);
    }
}
