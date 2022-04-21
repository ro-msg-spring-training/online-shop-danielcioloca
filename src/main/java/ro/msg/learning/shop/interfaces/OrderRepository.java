package ro.msg.learning.shop.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.msg.learning.shop.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}