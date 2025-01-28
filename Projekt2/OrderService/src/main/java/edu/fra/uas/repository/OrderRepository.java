package edu.fra.uas.repository;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

import edu.fra.uas.model.Order;

@Repository
public class OrderRepository extends HashMap<Long, Order> {
}
