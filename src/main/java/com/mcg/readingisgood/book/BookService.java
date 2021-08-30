package com.mcg.readingisgood.book;

import com.mcg.readingisgood.dto.BookStockDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    @Transactional
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public void updateBookStock(BookStockDTO bookStockDTO) {
        bookRepository.updateBookStock(bookStockDTO.getId(), bookStockDTO.getStock());
    }

    @Transactional(readOnly = true)
    public Optional<Book> findById(String id) {
        return bookRepository.findById(id);
    }
}
