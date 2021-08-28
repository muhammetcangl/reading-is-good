package com.mcg.readingisgood.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BookStockDTO {

    @NotNull
    private String id;

    @NotNull
    private Long stock;
}
