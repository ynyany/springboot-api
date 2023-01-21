package co.zip.candidate.userapi.service;

import co.zip.candidate.userapi.dto.UpdateUserDTO;
import co.zip.candidate.userapi.dto.UserDTO;
import co.zip.candidate.userapi.exception.UserEmailAlreadyExistsException;
import co.zip.candidate.userapi.exception.UserNotFoundException;
import co.zip.candidate.userapi.model.User;
import co.zip.candidate.userapi.repo.UserRepository;
import co.zip.candidate.userapi.validator.SimpleIncomeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private SimpleIncomeValidator validator;
    @Autowired
    private UserRepository userRepository;

    public UserService(SimpleIncomeValidator validator, UserRepository userRepository) {
        this.validator = validator;
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
        List<User> usersWithSameEmail = userRepository.findByEmail(user.getEmail());
        if(!usersWithSameEmail.isEmpty()) {
            throw new UserEmailAlreadyExistsException("Duplicated email address");
        }
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    private User toDomainModel(UserDTO userDTO) {

        validator.validExpendableInCome(userDTO);
        User user = new User(userDTO.getName(), userDTO.getEmail(), userDTO.getMonthlySalary(), userDTO.getMonthlyExpenses());
        user.setId(userDTO.getId());
        return user;
    }

    @Transactional
    public void updateUser(UpdateUserDTO userDTO) {


        Optional<User> userOptional = userRepository.findById(userDTO.getId());

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User id " + userDTO.getId());
        }

        User user = userOptional.get();
        user.setName(userDTO.getName());
        user.setMonthlySalary(userDTO.getMonthlySalary());
        user.setMonthlyExpenses(userDTO.getMonthlyExpenses());

        userRepository.save(user);

    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}
