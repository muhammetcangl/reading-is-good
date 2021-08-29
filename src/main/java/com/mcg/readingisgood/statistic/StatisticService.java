package com.mcg.readingisgood.statistic;

import com.mcg.readingisgood.book.Book;
import com.mcg.readingisgood.book.BookService;
import com.mcg.readingisgood.exception.AppException;
import com.mcg.readingisgood.order.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.Month;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
public class StatisticService {

    private final OrderService orderService;
    private final BookService bookService;

    public List<StatisticDTO> getMonthlyOrderStatistic(String currentUserId) {
        List<Order> customerOrders = orderService.findAllByStatusAndCustomerId(OrderStatus.PURCHASED.name(), currentUserId);
        List<OrderDTO> customerOrderDTOs = OrderMapper.INSTANCE.toListDTO(customerOrders);

        Map<Integer, List<OrderDTO>> monthlyOrders = customerOrderDTOs.stream()
                .collect(groupingBy(d -> d.getCreatedDate().get(ChronoField.MONTH_OF_YEAR)));

        List<StatisticDTO> statisticData = new ArrayList<>();
        DecimalFormat amountFormat = new DecimalFormat("#.##");

        for (var monthKey : monthlyOrders.entrySet()) {

            String monthName = Month.of(monthKey.getKey()).getDisplayName(TextStyle.FULL_STANDALONE, Locale.ENGLISH);
            long totalBookCount = 0L;
            AtomicReference<Double> totalPurchasedAmount = new AtomicReference<>((double) 0);

            for (OrderDTO orderDTO : monthKey.getValue()) {
                totalBookCount += orderDTO.getQuantity();
                orderDTO.getBooks().forEach(bookDTO -> {
                    Book book = bookService.findById(bookDTO.getId()).orElseThrow(() -> new AppException("Book not found!"));
                    totalPurchasedAmount.updateAndGet(v -> v + book.getPrice() * orderDTO.getQuantity());
                });

            }

            statisticData.add(
                    StatisticDTO.builder()
                            .month(monthName)
                            .totalOrderCount((long) monthKey.getValue().size())
                            .totalBookCount(totalBookCount)
                            .totalAmount(Double.valueOf(amountFormat.format(totalPurchasedAmount.get())))
                            .build()
            );
        }
        return statisticData;
    }
}
