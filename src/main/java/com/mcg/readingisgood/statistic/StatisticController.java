package com.mcg.readingisgood.statistic;

import com.mcg.readingisgood.security.CurrentUser;
import com.mcg.readingisgood.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statistic")
@RequiredArgsConstructor
public class StatisticController {

    private final StatisticService statisticService;

    @GetMapping("/getMonthlyStatistics")
    public ResponseEntity<List<StatisticDTO>> getMonthlyStatistics(@CurrentUser UserPrincipal currentUser) {
        return ResponseEntity.ok(statisticService.getMonthlyOrderStatistic(currentUser.getId()));
    }
}
