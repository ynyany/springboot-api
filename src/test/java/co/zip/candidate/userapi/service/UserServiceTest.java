package co.zip.candidate.userapi.service;

import co.zip.candidate.userapi.dto.UserDTO;
import co.zip.candidate.userapi.exception.UserEmailAlreadyExistsException;
import co.zip.candidate.userapi.model.User;
import co.zip.candidate.userapi.repo.AccountRepository;
import co.zip.candidate.userapi.repo.UserRepository;
import co.zip.candidate.userapi.validator.SimpleIncomeValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {
    private UserService classUnderTest;
    private SimpleIncomeValidator validator;
    private UserRepository userRepository;
    private AccountRepository accountRepository;
    private static final UserDTO dummyUserDto = new UserDTO(null, "testname", "email@email.com", BigDecimal.valueOf(10000), BigDecimal.valueOf(1000));
    private static final User dummyUser = new User("testname", "email@email.com", BigDecimal.valueOf(10000), BigDecimal.valueOf(1000));
    private static final User dummyUser2 = new User("testname1", "email1@email.com", BigDecimal.valueOf(10000), BigDecimal.valueOf(1000));

    @BeforeEach
    void setUp() {
        validator = mock(SimpleIncomeValidator.class);
        userRepository = mock(UserRepository.class);
        accountRepository = mock(AccountRepository.class);
        classUnderTest = new UserService(validator, userRepository, accountRepository);
        when(userRepository.save(any())).thenReturn(dummyUser);
        dummyUser.setId(null);
        dummyUser2.setId(null);
    }

    @Test
    void shouldCreateUserWithUniqueEmail() {
        when(userRepository.findByEmail(dummyUserDto.getEmail())).thenReturn(emptyList());
        dummyUser.setId(2l);
        UserDTO user = classUnderTest.createUser(dummyUserDto);

        assertNotNull(user);
        assertEquals(user.getId(), dummyUser.getId());
    }

    @Test
    void shouldNotCreateUserWithUniqueEmail() {
        ArrayList<User> users = new ArrayList<>();
        users.add(dummyUser2);
        when(userRepository.findByEmail(dummyUserDto.getEmail())).thenReturn(users);

        assertThrows(UserEmailAlreadyExistsException.class, () -> classUnderTest.createUser(dummyUserDto));

    }
}