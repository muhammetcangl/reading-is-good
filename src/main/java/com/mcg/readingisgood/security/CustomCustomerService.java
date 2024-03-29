package com.mcg.readingisgood.security;

import com.mcg.readingisgood.customer.Customer;
import com.mcg.readingisgood.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomCustomerService implements UserDetailsService {

    private final CustomerRepository customerRepository;


    @Override
    public UserDetails loadUserByUsername(String customerName) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByUsername(customerName);
        return UserPrincipal.create(customer);
    }

    @Transactional
    public UserDetails loadUserById(String id) {
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found for this id : " + id));
        return UserPrincipal.create(customer);
    }
}
