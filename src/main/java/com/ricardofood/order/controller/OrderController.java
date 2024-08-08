package com.ricardofood.order.controller;

import com.ricardofood.order.dto.OrderDto;
import com.ricardofood.order.dto.StatusDto;
import com.ricardofood.order.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping
    public List<OrderDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> findById(@PathVariable @NotNull Long id) {
        var dto = service.getById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<OrderDto> create(@RequestBody @Valid OrderDto dto, UriComponentsBuilder uriBuilder) {
        var createdOrder = service.create(dto);
        URI location = uriBuilder.path("/orders/{id}").buildAndExpand(createdOrder.getId()).toUri();
        return ResponseEntity.created(location).body(createdOrder);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderDto> updateStatus(@PathVariable Long id, @RequestBody StatusDto statusDto) {
        OrderDto dto = service.updateStatus(id, statusDto);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}/paid")
    public ResponseEntity<Void> approvePayment(@PathVariable @NotNull Long id) {
        service.approvePayment(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/port")
    public String getPort(@Value("${local.server.port}") String port) {
        return String.format("Order service running on port %s", port);
    }
}
