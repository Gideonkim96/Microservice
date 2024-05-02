package com.kim.accounts.service.impl;

import com.kim.accounts.dto.AccountsDto;
import com.kim.accounts.dto.CustomerDetailsDto;
import com.kim.accounts.entity.Accounts;
import com.kim.accounts.entity.Customer;
import com.kim.accounts.exception.ResourceNotFoundException;
import com.kim.accounts.mapper.AccountsMapper;
import com.kim.accounts.mapper.CustomerMapper;
import com.kim.accounts.repository.AccountsRepository;
import com.kim.accounts.repository.CustomerRepository;
import com.kim.accounts.service.ICustomersService;
import com.kim.accounts.service.client.CardsFiegnClient;
import com.kim.accounts.service.client.LoansFiegnClient;
import com.kim.cards.dto.CardsDto;
import com.kim.loans.dto.LoansDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomersService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CardsFiegnClient cardsFeignClient;
    private LoansFiegnClient loansFeignClient;

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Customer Details based on a given mobileNumber
     */
    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(mobileNumber);
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(mobileNumber);
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());

        return customerDetailsDto;

    }
}
