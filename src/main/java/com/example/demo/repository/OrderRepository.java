package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
  //List<Order> findMenusByorders(Long orderId);
  List<Order> findAllByCustomer_id(Long customerId);
  void deleteById(long orderlId);
}
