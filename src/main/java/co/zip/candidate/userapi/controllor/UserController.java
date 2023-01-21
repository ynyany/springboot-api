package co.zip.candidate.userapi.controllor;

import co.zip.candidate.userapi.dto.AccountDTO;
import co.zip.candidate.userapi.dto.UpdateUserDTO;
import co.zip.candidate.userapi.dto.UserDTO;
import co.zip.candidate.userapi.model.User;
import co.zip.candidate.userapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

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
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UpdateUserDTO user, @PathVariable long id) {
        user.setId(id);
        userService.updateUser(user);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/users/{id}/accounts/")
    public ResponseEntity<Object> associatedAccount(@Valid @RequestBody AccountDTO accountDTO, @PathVariable long id) {

        userService.createAccount(id, accountDTO);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{userId}/accounts/")
    public List<AccountDTO> getAssociatedAccounts(@PathVariable long userId) {

       return  userService.getAccounts(userId);

    }
}
