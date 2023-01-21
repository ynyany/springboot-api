package co.zip.candidate.userapi.controllor;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import co.zip.candidate.userapi.exception.UserNotFoundException;
import co.zip.candidate.userapi.model.User;
import co.zip.candidate.userapi.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> retrieveAllStudents() {
        return userRepository.findAll();
    }
    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable long id) {
        Optional<User> User = userRepository.findById(id);

        if (User.isEmpty())
            throw new UserNotFoundException("id-" + id);

        return User.get();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable long id) {
        userRepository.deleteById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@RequestBody User User) {
        User savedUser = userRepository.save(User);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Object> updateUser(@RequestBody User user, @PathVariable long id) {

        Optional<User> UserOptional = userRepository.findById(id);

        if (UserOptional.isEmpty())
            return ResponseEntity.notFound().build();

        user.setId(id);

        userRepository.save(user);

        return ResponseEntity.noContent().build();
    }
}
