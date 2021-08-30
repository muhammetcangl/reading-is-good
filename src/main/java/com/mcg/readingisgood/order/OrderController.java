package com.mcg.readingisgood.order;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody Order order) {
        return ResponseEntity.ok(OrderMapper.INSTANCE.toDTO(orderService.save(order)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable("id") String id) {
        return ResponseEntity.ok(OrderMapper.INSTANCE.toDTO(orderService.findById(id)));
    }

    @GetMapping("/interval")
    public ResponseEntity<Page<OrderDTO>> getOrdersWithDateInterval(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                                                    @RequestParam(defaultValue = "0") Integer pageNo,
                                                                    @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return ResponseEntity.ok(orderService.getOrdersWithDateInterval(startDate, endDate, paging));
    }
}
