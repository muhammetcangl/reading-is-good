package com.mcg.readingisgood.statistic;

import com.mcg.readingisgood.book.Book;
import com.mcg.readingisgood.book.BookService;
import com.mcg.readingisgood.order.Order;
import com.mcg.readingisgood.order.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StatisticServiceTest {

    @InjectMocks
    private StatisticService statisticService;

    @Mock
    private OrderService orderService;

    @Mock
    private BookService bookService;


    @Test
    public void getMonthlyOrderStatisticShouldReturnSuccessfully() {
        List<Order> orderList = new ArrayList<>();
        List<Book> books = new ArrayList<>();
        Book book = Book.builder().id("1").name("Suc ve Ceza").stock(2L).price(10d).build();
        books.add(book);
        Optional<Book> bookOptional = Optional.of(book);
        Order order = Order.builder().id("1").quantity(1L).books(books).build();
        LocalDateTime now = LocalDateTime.now();
        Month month = now.getMonth();
        order.setCreatedDate(now);
        orderList.add(order);

        when(orderService.findAllByStatusAndCustomerId(any(), anyString())).thenReturn(orderList);
        when(bookService.findById(anyString())).thenReturn(bookOptional);

        List<StatisticDTO> statisticData = statisticService.getMonthlyOrderStatistic("1");
        assertEquals(1, statisticData.size());
        StatisticDTO statisticDTO = statisticData.get(0);
        assertEquals(month.toString().toLowerCase(), statisticDTO.getMonth().toLowerCase());
        assertEquals(Long.valueOf(1), statisticDTO.getTotalOrderCount());

    }
}
