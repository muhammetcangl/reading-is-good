package com.mcg.readingisgood.order;

import com.mcg.readingisgood.book.Book;
import com.mcg.readingisgood.book.BookRepository;
import com.mcg.readingisgood.book.BookService;
import com.mcg.readingisgood.exception.AppException;
import com.mongodb.MongoCommandException;
import com.mongodb.MongoException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.MongoTransactionException;
import org.springframework.data.mongodb.UncategorizedMongoDbException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final MongoTemplate mongoTemplate;

    //@Transactional(value = "mongoTransactionManager", propagation = Propagation.REQUIRED)
    //@Retryable(value = {MongoCommandException.class, MongoException.class}, exclude = {MongoTransactionException.class, UncategorizedMongoDbException.class},
    //        backoff = @Backoff(delay = 10), maxAttempts = 10)
    public Order save(Order order) {

        order.getBooks().forEach(book -> {
            Book orderedBook = bookRepository.findById(book.getId()).orElseThrow(() -> new AppException("Book not found!"));
            bookRepository.updateBookStock(book.getId(), orderedBook.getStock() - 1); //todo: check stock
        });

        return orderRepository.save(order);
    }

    public Order findById(String id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public Page<OrderDTO> getOrdersWithDateInterval(Date startDate, Date endDate, Pageable paging) {
        LocalDateTime startLocalDate = LocalDateTime.ofInstant(startDate.toInstant(), ZoneId.systemDefault());
        LocalDateTime endLocalDate = LocalDateTime.ofInstant(endDate.toInstant(), ZoneId.systemDefault());

        return orderRepository.findAllByCreatedDateIsBetween(startLocalDate, endLocalDate, paging).map(OrderMapper.INSTANCE::toDTO);
    }

    @Transactional(readOnly = true)
    public List<Order> findAllByStatusAndCustomerId(String status, String customerId) {
        return orderRepository.findAllByStatusAndCustomer_Id(status, customerId);
    }
}
