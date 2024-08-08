package com.ricardofood.order.service;

import com.ricardofood.order.dto.OrderDto;
import com.ricardofood.order.dto.StatusDto;
import com.ricardofood.order.enums.Status;
import com.ricardofood.order.model.Order;
import com.ricardofood.order.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private final ModelMapper mapper;

    public List<OrderDto> findAll() {
        return repository
                .findAll()
                .stream()
                .map(order -> mapper
                        .map(order, OrderDto.class)
                ).toList();
    }

    public OrderDto getById(Long id) {
        var order = this.findOrderById(id);
        return mapper.map(order, OrderDto.class);
    }

    public OrderDto create(OrderDto orderDto) {
        var order = mapper.map(orderDto, Order.class);
        order.setDate(LocalDateTime.now());
        order.setStatus(Status.COMPLETED);
        order.getItems().forEach(item -> item.setOrder(order));
        repository.save(order);

        return mapper.map(order, OrderDto.class);
    }

    public OrderDto updateStatus(Long id, StatusDto statusDto) {
        var order = this.findOrderById(id);
        order.setStatus(statusDto.getStatus());
        repository.save(order);
        return mapper.map(order, OrderDto.class);
    }

    public void approvePayment(Long id) {
        var order = this.findOrderById(id);
        order.setStatus(Status.PAID);
        repository.save(order);
    }

    private Order findOrderById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }
}
