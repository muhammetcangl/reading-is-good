package com.mcg.readingisgood.order;

import com.mcg.readingisgood.audit.Auditable;
import com.mcg.readingisgood.book.Book;
import com.mcg.readingisgood.customer.Customer;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import java.util.List;

@Data
@Document(collection = "orders")
@NoArgsConstructor
public class Order extends Auditable {

    @Id
    private String id;

    @DBRef
    private Customer customer;

    @DBRef
    private List<Book> books;

    @Min(value = 1, message = "Quantity must be more than zero!")
    private Long quantity;

    private OrderStatus status;
}
