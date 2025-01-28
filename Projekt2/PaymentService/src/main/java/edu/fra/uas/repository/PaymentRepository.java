package edu.fra.uas.repository;

import java.util.HashMap;


import org.springframework.stereotype.Repository;

import edu.fra.uas.model.Payment;

@Repository
public class PaymentRepository extends HashMap<Long, Payment>{

}
