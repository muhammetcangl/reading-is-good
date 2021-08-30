package com.mcg.readingisgood.book;


import com.mcg.readingisgood.dto.BookStockDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;


    @Test
    public void saveBookShouldSaveSuccessfully() {
        Book bookPassed = Book.builder().id("1").name("Suc ve Ceza").author("Dostoyevski").orders(new ArrayList<>())
                .price(1d).stock(1L).build();
        Book bookReturned = Book.builder().id("1").name("Suc ve Ceza").author("Dostoyevski").orders(new ArrayList<>())
                .price(1d).stock(1L).build();
        when(bookRepository.save(Mockito.any())).thenReturn(bookReturned);

        Book book = bookService.saveBook(bookPassed);
        assertEquals(bookPassed.getId(), book.getId());
    }

    @Test
    public void updateBookStockShouldCallRepository() {
        BookStockDTO bookStockDTO = new BookStockDTO();
        bookStockDTO.setId("123");
        bookStockDTO.setStock(1L);

        bookService.updateBookStock(bookStockDTO);

        verify(bookRepository, times(1)).updateBookStock(ArgumentMatchers.anyString(), ArgumentMatchers.anyLong());
    }

    @Test
    public void findByIdShouldReturnCallRepository() {

        bookService.findById("1");

        verify(bookRepository, times(1)).findById(stringArgumentCaptor.capture());
        String passedValue = stringArgumentCaptor.getValue();
        assertEquals("1", passedValue);
    }
}
