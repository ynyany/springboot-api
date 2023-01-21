package co.zip.candidate.userapi.service;

import co.zip.candidate.userapi.dto.AccountDTO;
import co.zip.candidate.userapi.dto.UpdateUserDTO;
import co.zip.candidate.userapi.dto.UserDTO;
import co.zip.candidate.userapi.exception.UserEmailAlreadyExistsException;
import co.zip.candidate.userapi.exception.UserNotFoundException;
import co.zip.candidate.userapi.model.Account;
import co.zip.candidate.userapi.model.User;
import co.zip.candidate.userapi.repo.AccountRepository;
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

    @Autowired
    private AccountRepository accountRepository;


    public UserService(SimpleIncomeValidator validator, UserRepository userRepository, AccountRepository accountRepository) {
        this.validator = validator;
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
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
        if (!usersWithSameEmail.isEmpty()) {
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


        Long userId = userDTO.getId();
        User user = getUserById(userId);
        user.setName(userDTO.getName());
        user.setMonthlySalary(userDTO.getMonthlySalary());
        user.setMonthlyExpenses(userDTO.getMonthlyExpenses());

        userRepository.save(user);

    }


    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void createAccount(long userId, AccountDTO accountDTO) {
        User user = getUserById(userId);

        Account newAccount = new Account(accountDTO.getName(), user);
        Account savedAccount = accountRepository.save(newAccount);
        user.getAccounts().add(savedAccount);
    }

    private User getUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User id " + userId);
        }

        User user = userOptional.get();
        return user;
    }

    public List<AccountDTO> getAccounts(long id) {

        User user = getUserById(id);
        return user.getAccounts().stream().map(ac -> new AccountDTO(ac.getId(), ac.getName())).collect(Collectors.toList());

    }

}
