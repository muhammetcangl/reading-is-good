package com.mcg.readingisgood.order;

import com.mcg.readingisgood.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.concurrent.Executor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Order createOrder(@Valid @RequestBody Order order) {
        return orderService.save(order);
    }

    @GetMapping("/{id}")
    public Order findById(@PathVariable("id") String id) {
        return orderService.findById(id);
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
