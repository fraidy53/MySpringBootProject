package com.basic.myspringboot.repository;

import com.basic.myspringboot.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // finder(query) method
    // 1. customerId로 고객번호(Unique -> 중복 허용 X) 조회하는 finder 메서드
    Optional<Customer> findByCustomerId(String customerId);

    // 2. customerName으로 고객명(-> 중복 허용)조회하는 finder 메서드
    List<Customer> findByCustomerName(String customerName);
}
