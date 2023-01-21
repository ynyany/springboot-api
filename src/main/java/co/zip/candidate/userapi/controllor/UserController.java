package co.zip.candidate.userapi.controllor;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import co.zip.candidate.userapi.dto.UserDTO;
import co.zip.candidate.userapi.exception.UserNotFoundException;
import co.zip.candidate.userapi.model.User;
import co.zip.candidate.userapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<UserDTO> retrieveAllUsers() {
        return userService.getUsers();
    }
    @GetMapping("/users/{id}")
    public UserDTO retrieveUser(@PathVariable long id) {
        UserDTO user = userService.getUser(id);

        return user;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserDTO User) {
        User savedUser = userService.createUser(User);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserDTO user, @PathVariable long id) {
        user.setId(id);
         userService.updateUser(user);

        return ResponseEntity.noContent().build();
    }
}
