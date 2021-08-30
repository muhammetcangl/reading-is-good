package com.mcg.readingisgood.customer;

import com.mcg.readingisgood.auth.JwtAuthenticationResponse;
import com.mcg.readingisgood.auth.LoginRequest;
import com.mcg.readingisgood.auth.SignUpRequest;
import com.mcg.readingisgood.payload.ApiResponse;
import com.mcg.readingisgood.role.Role;
import com.mcg.readingisgood.role.RoleName;
import com.mcg.readingisgood.role.RoleRepository;
import com.mcg.readingisgood.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final AuthenticationManager authenticationManager;
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    public ResponseEntity<?> registerCustomer(SignUpRequest signUpRequest) {

        if (customerRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ApiResponse(false, "Bu kullanıcı adı zaten kullanılıyor."),
                    HttpStatus.BAD_REQUEST);
        }

        Customer customer = new Customer(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getPassword());

        customer.setPassword(passwordEncoder.encode(customer.getPassword()));

        Role customerRole = roleRepository.findByName(RoleName.USER).orElseThrow(RuntimeException::new);

        customer.setRoles(Collections.singleton(customerRole));

        Customer result = customerRepository.save(customer);


        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/customer/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "Customer registered successfully"));
    }
}
