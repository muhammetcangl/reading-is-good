package com.mcg.readingisgood.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mcg.readingisgood.audit.Auditable;
import com.mcg.readingisgood.order.Order;
import com.mcg.readingisgood.role.Role;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Document(collection = "customers")
@NoArgsConstructor
public class Customer extends Auditable {

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

    @DBRef
    private List<Order> orders;

    private Set<Role> roles = new HashSet<>();

}