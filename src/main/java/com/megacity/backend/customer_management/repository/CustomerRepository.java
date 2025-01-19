package com.megacity.backend.customer_management.repository;

import com.megacity.backend.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {}
