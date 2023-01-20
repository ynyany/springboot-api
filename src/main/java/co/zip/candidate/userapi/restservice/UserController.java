package co.zip.candidate.userapi.restservice;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private static final String template = "Hello, %s!";


    @GetMapping("/users")
    public User greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new User();
    }


    @RequestMapping(method = RequestMethod.POST,
            value = "/{name}",
            produces = MediaType.TEXT_PLAIN_VALUE)
    public User createUser(User user) {
//        return userRepository.save(user);
        return null;
    }
}
