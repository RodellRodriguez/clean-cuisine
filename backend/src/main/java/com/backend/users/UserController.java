package com.backend.users;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/users")
class UserController {
    @Autowired
    private UserService userService;

    // To-Do: Don't return a list of objects. Return an object for Api best practices.
    @GetMapping
    List<User> all() {
        return userService.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED) // 201
    @PostMapping
    public User create(@RequestBody User user) {  // This annotation deserializes the JSON into a User type
        // To-Do: Implement requestbody validation e.g. required fields
        return userService.save(user);
    }
    
    
    @GetMapping("/{id}")
    public Optional<User> findById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        if (user.isEmpty()) {
            // To-Do: Standardize error response messages
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User with id %d not found", id)); // See https://stackoverflow.com/a/68701285
        }
        return user;
    }

    @PutMapping
    public User update(@RequestBody User user) {
        // To-Do: Handle requestbody validation
        // To-Do: Use the request body to update information but not for the initial user lookup.
        return userService.save(user);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }


}