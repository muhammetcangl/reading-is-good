package com.mcg.readingisgood.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mcg.readingisgood.role.Role;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Document(collection = "customers")
public class Customer {

    @Id
    private String id;
    private String name;
    private String username;

    public Customer(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    @JsonIgnore
    private String password;

    private Set<Role> roles = new HashSet<>();

}