package com.mcg.readingisgood.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    Page<Order> findAllByCreatedDateIsBetween(LocalDateTime dt1, LocalDateTime dt2, Pageable pageable);

    List<Order> findAllByStatusAndCustomer_Id(String status, String customerId);
}
