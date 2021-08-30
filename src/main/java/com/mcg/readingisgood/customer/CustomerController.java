package com.mcg.readingisgood.customer;

import com.mcg.readingisgood.auth.LoginRequest;
import com.mcg.readingisgood.auth.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/signIn")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return customerService.authenticateUser(loginRequest);
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody SignUpRequest signUpRequest) {
        return customerService.registerCustomer(signUpRequest);
    }

}
