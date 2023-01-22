package co.zip.candidate.userapi.controllor;

import co.zip.candidate.userapi.dto.AccountDTO;
import co.zip.candidate.userapi.dto.UpdateUserDTO;
import co.zip.candidate.userapi.dto.UserDTO;
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
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO savedUser = userService.createUser(userDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();

        UserDTO responseDTO = new UserDTO(savedUser.getId(), savedUser.getName(), savedUser.getEmail(), savedUser.getMonthlySalary(), savedUser.getMonthlyExpenses());
        return ResponseEntity.created(location).body(responseDTO);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UpdateUserDTO user, @PathVariable long id) {
        user.setId(id);
        UpdateUserDTO updateUserDTO = userService.updateUser(user);

        return ResponseEntity.accepted().body(updateUserDTO);
    }

    @PostMapping("/users/{id}/accounts")
    public ResponseEntity<Object> associatedAccount(@Valid @RequestBody AccountDTO accountDTO, @PathVariable long id) {

        AccountDTO account = userService.createAccount(id, accountDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{accountId}")
                .buildAndExpand(account.getId()).toUri();
        return ResponseEntity.created(location).body(account);
    }

    @GetMapping("/users/{userId}/accounts/")
    public List<AccountDTO> getAssociatedAccounts(@PathVariable long userId) {

        return userService.getAccounts(userId);

    }
}
