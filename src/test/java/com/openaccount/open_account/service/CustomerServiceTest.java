package com.openaccount.open_account.service;

import com.openaccount.open_account.data.dto.customer.CustomerResponseDto;
import com.openaccount.open_account.data.enums.TransactionType;
import com.openaccount.open_account.data.model.Customer;
import com.openaccount.open_account.exeption.CustomCustomerException;
import com.openaccount.open_account.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private CustomerService customerService;

    private Customer testCustomer;
    private CustomerResponseDto testCustomerDto;
    @BeforeEach
    void setUp() {
        testCustomer = new Customer();
        testCustomer.setId(1L);
        testCustomer.setFirstName("Test");
        testCustomer.setLastName("User");
        testCustomer.setBalance(new BigDecimal("100.00"));
        testCustomer.setActive(true);
        testCustomerDto = new CustomerResponseDto();
        testCustomerDto.setId(1L);
        testCustomerDto.setBalance(testCustomer.getBalance());
    }

    @Test
    void when_updateBalance_Credit_shouldAddBalance() {
        BigDecimal amountToAdd = new BigDecimal("50.00");
        TransactionType type = TransactionType.CREDIT;
        Long customerId = 1L;
        when(customerRepository.existsById(customerId)).thenReturn(true);
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(testCustomer));
        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> {
            Customer savedCustomer = invocation.getArgument(0);
            testCustomerDto.setBalance(savedCustomer.getBalance());
            return savedCustomer;
        });
        when(modelMapper.map(any(Customer.class), any(Class.class))).thenReturn(testCustomerDto);
        CustomerResponseDto response = customerService.updateBalance(amountToAdd, type, customerId);
        assertNotNull(response);
        assertEquals(0, response.getBalance().compareTo(new BigDecimal("150.00")));
    }

    @Test
    void when_updateBalance_Debit_Success_shouldSubtractBalance() {
        BigDecimal amountToSubtract = new BigDecimal("70.00");
        TransactionType type = TransactionType.DEBIT;
        Long customerId = 1L;
        when(customerRepository.existsById(customerId)).thenReturn(true);
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(testCustomer));
        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> {
            Customer savedCustomer = invocation.getArgument(0);
            testCustomerDto.setBalance(savedCustomer.getBalance());
            return savedCustomer;
        });
        when(modelMapper.map(any(Customer.class), any(Class.class))).thenReturn(testCustomerDto);
        CustomerResponseDto response = customerService.updateBalance(amountToSubtract, type, customerId);
        assertNotNull(response);
        assertEquals(0, response.getBalance().compareTo(new BigDecimal("30.00")));
    }

    @Test
    void when_updateBalance_Debit_InsufficientBalance_shouldThrowException() {
        BigDecimal amountToSubtract = new BigDecimal("200.00");
        TransactionType type = TransactionType.DEBIT;
        Long customerId = 1L;
        when(customerRepository.existsById(customerId)).thenReturn(true);
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(testCustomer));
        CustomCustomerException exception = assertThrows(CustomCustomerException.class, () -> {
            customerService.updateBalance(amountToSubtract, type, customerId);
        });
        assertTrue(exception.getMessage().contains("Yetersiz bakiye"));
    }
}
