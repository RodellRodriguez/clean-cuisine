package com.backend.users;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)  // Shortcut that allows Mockito injection capability in our tests
public class UserServiceTests {

    // @BeforeAll
    // static void setup() {
    //     System.out.println("@BeforeAll - executes once before all test methods in this class");

    // }

    @Mock
    UserRepository mockUserRepository;

    @InjectMocks  // Injects all mocks into this instance
    UserService underTest;

    @Test
    void findById_ShouldReturnUserWithId_WhenGivenExistingId() {
        Long existingId = 1L;
        User expectedUser = new User(existingId, "Bob The Builder", "builder");
        // Mocking returning a User implies the user exists.
        when(mockUserRepository.findById(existingId)).thenReturn(Optional.of(expectedUser));  // Optional.of creates an Optional<User> instance

        Optional<User> actualUser = underTest.findById(existingId);
        assertEquals(existingId, actualUser.get().getId());  //.get() gets the User object inside of the Optional 
    }

    @Test
    void findById_ShouldReturnEmpty_WhenGivenNonExistingId() {
        Long nonExistingId = 5L;
        when(mockUserRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        Optional<User> actualUser = underTest.findById(nonExistingId);
        assertTrue(actualUser.isEmpty());
    }

    @Test
    void save_ShouldReturnUser_WhenGivenUserInstance() {
        Long newUserId = 5L;
        User newUser = new User(newUserId, "Bob The Builder", "builder");
        when(mockUserRepository.save(newUser)).thenReturn(newUser);

        User actualUser = underTest.save(newUser);
        assertEquals(newUserId, actualUser.getId());
    }

}
