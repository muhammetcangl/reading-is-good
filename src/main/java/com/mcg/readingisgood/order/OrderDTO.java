package com.mcg.readingisgood.order;

import com.mcg.readingisgood.book.BookDTO;
import com.mcg.readingisgood.customer.CustomerDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderDTO {

    private String id;
    private OrderStatus status;
    private Long quantity;
    private String createdBy;
    private LocalDateTime createdDate;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;
    private List<BookDTO> books;
    private CustomerDTO customer;
}
