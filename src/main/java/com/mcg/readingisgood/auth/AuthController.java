package com.mcg.readingisgood.auth;

import com.mcg.readingisgood.customer.Customer;
import com.mcg.readingisgood.customer.CustomerRepository;
import com.mcg.readingisgood.exception.AppException;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;


    @PostMapping("/signIn")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

        if (customerRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ApiResponse(false, "Bu kullanıcı adı zaten kullanılıyor."),
                    HttpStatus.BAD_REQUEST);
        }

        Customer customer = new Customer(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getPassword());

        customer.setPassword(passwordEncoder.encode(customer.getPassword()));

        Role customerRole = roleRepository.findByName(RoleName.USER).orElseThrow(() -> new RuntimeException());

        customer.setRoles(Collections.singleton(customerRole));

        Customer result = customerRepository.save(customer);


        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

}
