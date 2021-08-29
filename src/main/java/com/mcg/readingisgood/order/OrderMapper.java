package com.mcg.readingisgood.order;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDTO toDTO(Order order);

    Order fromDTO(OrderDTO orderDTO);

    List<OrderDTO> toListDTO(List<Order> orderList);

}
