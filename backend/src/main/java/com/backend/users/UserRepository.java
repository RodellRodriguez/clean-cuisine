package com.backend.users;

import org.springframework.data.jpa.repository.JpaRepository;

/*
Spring Data JPA repositories are interfaces with methods that support creating, reading, updating, and deleting records against a back end data store. Some repositories also support data paging and sorting, where appropriate.
Spring Data synthesizes implementations based on conventions found in the naming of the methods in the interface. 

JpaRepository extends CrudRepository which provides crud actions for the User class at runtime automatically
*/

public interface UserRepository extends JpaRepository<User, Long> {
    
    // @Override
    // Optional<User> findById(Long id);
}
