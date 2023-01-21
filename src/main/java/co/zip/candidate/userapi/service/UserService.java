package co.zip.candidate.userapi.service;

import co.zip.candidate.userapi.dto.UserDTO;
import co.zip.candidate.userapi.exception.UserNotFoundException;
import co.zip.candidate.userapi.model.User;
import co.zip.candidate.userapi.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> getUsers() {
        return this.userRepository.findAll().stream().map(u -> new UserDTO(u.getId(), u.getName(), u.getEmail(), u.getMonthlySalary(), u.getMonthlyExpenses())).collect(Collectors.toList());
    }

    public UserDTO getUser(long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) throw new UserNotFoundException("id-" + id);

        return user.map(u -> new UserDTO(u.getId(), u.getName(), u.getEmail(), u.getMonthlySalary(), u.getMonthlyExpenses())).get();
    }

    public User createUser(UserDTO userDTO) {
        User user = toDomainModel(userDTO);
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    private User toDomainModel(UserDTO userDTO) {
//        validdate dto;
        User user = new User(userDTO.getName(), userDTO.getEmail(), userDTO.getMonthlySalary(), userDTO.getMonthlyExpenses());
        user.setId(userDTO.getId());
        return user;


    }

    @Transactional
    public void updateUser(UserDTO userDTO) {
        User user = toDomainModel(userDTO);
        Optional<User> UserOptional = userRepository.findById(user.getId());


        if (UserOptional.isEmpty()) {
            throw new UserNotFoundException("User id " + user.getId());
        }
//            return ResponseEntity.notFound().build();

//        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setMonthlySalary(userDTO.getMonthlySalary());
        user.setMonthlyExpenses(userDTO.getMonthlyExpenses());

        userRepository.save(user);

    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}
