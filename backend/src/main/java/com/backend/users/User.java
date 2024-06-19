package com.backend.users;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity // this is a JPA annotation to make this object ready for storage in a JPA-based data store
@Table(name = "users")  // Forcing table name to be plural to avoid postgres reserved keyword conflict
public class User {
    private @Id
    @GeneratedValue Long id;  // JPA annotations indicate that this is the primary key and is automatically populated by the JPA provider
    private String name;
    private String role;

    User() {}  // for JPA only, no use

    public User(String name, String role){  // A custom constructor is created when we need to create a new instance but do not yet have an id.
        this.name = name;
        this.role = role;
    }

    public Long getId() {
        return this.id;
      }
    
      public String getName() {
        return this.name;
      }
    
      public String getRole() {
        return this.role;
      }
    
      public void setId(Long id) {
        this.id = id;
      }
    
      public void setName(String name) {
        this.name = name;
      }
    
      public void setRole(String role) {
        this.role = role;
      }
}
