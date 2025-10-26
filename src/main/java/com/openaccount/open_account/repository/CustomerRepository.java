package com.openaccount.open_account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openaccount.open_account.data.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
