package com.mcg.readingisgood.order;

import com.mcg.readingisgood.book.Book;
import com.mcg.readingisgood.book.BookRepository;
import com.mcg.readingisgood.exception.AppException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private BookRepository bookRepository;

    @Test
    public void saveShouldSaveOrderSuccessfully() {
        List<Book> books = new ArrayList<>();
        Book book = Book.builder().id("1").name("Suc ve Ceza").stock(2L).build();
        books.add(book);
        Optional<Book> bookOptional = Optional.of(book);

        Order order = Order.builder().id("1").quantity(1L).books(books).build();
        Order returnedOrder = Order.builder().id("1").quantity(1L).books(books).build();

        when(orderRepository.save(any())).thenReturn(returnedOrder);
        when(bookRepository.findById(any())).thenReturn(bookOptional);

        Order savedOrder = orderService.save(order);
        verify(orderRepository, times(1)).save(any());
        assertEquals(order.getId(), savedOrder.getId());
    }

    @Test(expected = AppException.class)
    public void saveShouldThrowExceptionWhenOrderDoesnotHaveBook() {
        Order order = Order.builder().id("1").quantity(1L).build();
        orderService.save(order);
    }

    @Test
    public void findByIdShouldReturnNotEmptyWhenRepositoryReturnOrder() {
        Order order = Order.builder().id("1").quantity(1L).build();

        when(orderRepository.findById(any())).thenReturn(Optional.of(order));

        Order returnedOrder = orderService.findById("1");
        assertEquals("1", returnedOrder.getId());

    }

    @Test
    public void findByIdShouldReturnNullWhenRepositoryReturnEmpty() {
        when(orderRepository.findById(any())).thenReturn(Optional.empty());

        Order returnedOrder = orderService.findById("1");
        assertNull(returnedOrder);
    }

    @Test
    public void getOrdersWithDateIntervalShouldReturnPageableDataSuccessfully() {
        List<Order> orders = new ArrayList<>();
        Order order = Order.builder().id("1").quantity(1L).build();
        orders.add(order);
        Page<Order> page = new PageImpl<>(orders);
        Pageable pageable = Pageable.ofSize(1);
        when(orderRepository.findAllByCreatedDateIsBetween(any(),any(),any())).thenReturn(page);

        Date dateNow = new Date();

        Page<OrderDTO> returnedOrder = orderService.getOrdersWithDateInterval(dateNow, dateNow, pageable);

        assertEquals(1, returnedOrder.getSize());
        OrderDTO orderDTO = returnedOrder.getContent().get(0);

        assertEquals(order.getId(), orderDTO.getId());
    }

    @Test
    public void findAllByStatusAndCustomerIdShouldReturnSuccessfully() {
        List<Order> orders = new ArrayList<>();
        Order order = Order.builder().id("1").build();
        orders.add(order);

        when(orderRepository.findAllByStatusAndCustomer_Id(anyString(), anyString())).thenReturn(orders);

        List<Order> returnedOrder = orderService.findAllByStatusAndCustomerId(OrderStatus.PURCHASED.name(), "1");
        assertEquals(1, returnedOrder.size());
        Order savedOrder = returnedOrder.get(0);
        assertEquals("1", savedOrder.getId());
    }

}
