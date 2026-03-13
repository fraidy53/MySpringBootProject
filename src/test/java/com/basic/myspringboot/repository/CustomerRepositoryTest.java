package com.basic.myspringboot.repository;

import com.basic.myspringboot.entity.Customer;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CustomerRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;

    // 1. Customer 등록
    @Test
    @Rollback(value = false) // 롤백 방지, DB에 데이터가 남아있게 됨
    @Disabled
    void testCreate(){
        // Given (준비단계), 엔티티가 만들어진 상태
        Customer customer = new Customer();
        customer.setCustomerId("A004");
        customer.setCustomerName("스프링부트4");

        // When (실행단계)
        Customer addCustomer = customerRepository.save(customer);// 등록

        // Then (검증단계)
        assertThat(addCustomer).isNotNull(); // 등록된 고객이 null이 아님
        // 등록된 고객의 이름이 "스프링부트"와 동일한지 검증
        assertThat(addCustomer.getCustomerName()).isEqualTo("스프링부트4");
    }

    // 2. Customer 조회
    @Test
    void testFindBy(){
        Optional<Customer> optionalCustomer = customerRepository.findById(1L);
        if(optionalCustomer.isPresent()){
            Customer customer = optionalCustomer.get();
            assertThat(customer.getId()).isEqualTo(1L);
        }else{
            System.out.println("Customer Not Found");
        }

        // ifPresent(Consumer)
        // Consumer의 추상메서드는 void accept(T t)
        optionalCustomer.ifPresent(customer -> System.out.println(customer.getCustomerName()));
    }

    @Test
    @Disabled
    void testFindByNotFound(){
        // orElseGet(Supplier)
        // Supplier의 추상메서드는 T get(), T를 반드시 리턴
        Customer existCustomer = customerRepository.findById(2L).orElseGet(() -> new Customer());
        assertThat(existCustomer.getId()).isNull();
        //assertThat(existCustomer.getId()).isEqualTo(2L);

        // public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X
        Customer notFoundCustomer = customerRepository.findById(3L).orElseThrow(() -> new RuntimeException("Customer Not Found"));
    }

    @Test
    @Rollback(value = false)
    void testUpdate(){
        // 조회를 하고 setter 메서드 호출하면 update가 됨
        Customer customer = customerRepository.findByCustomerId("A002").orElseThrow(() -> new RuntimeException("Customer Not Found"));
        customer.setCustomerName("스프링부트21");
        customerRepository.save(customer); // setter메소드 사용 하면 save() 메서드 호출 안해도 update가 됨. 단, @Transactional이 붙어있어야 함
        assertThat(customer.getCustomerName()).isEqualTo("스프링부트21");

    }
}