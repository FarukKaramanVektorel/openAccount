package com.openaccount.open_account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openaccount.open_account.data.model.Customer;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    public List<Customer> findByActiveIsTrue();

    public List<Customer> findByActiveIsFalse();
}
