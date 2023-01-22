package co.zip.candidate.userapi.validator;

import co.zip.candidate.userapi.dto.UserDTO;
import co.zip.candidate.userapi.exception.UserAccountAssociationExceededException;
import co.zip.candidate.userapi.exception.UserIncomeNotQualitiedException;
import co.zip.candidate.userapi.model.Account;
import co.zip.candidate.userapi.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class SimpleIncomeValidatorTest {
    private SimpleIncomeValidator classUnderTest;

    @BeforeEach
    void setUp() {
        classUnderTest = new SimpleIncomeValidator(BigDecimal.valueOf(1000), 1);
    }

    @Test
    void validIncome() {
        UserDTO userDTO = new UserDTO(1l,"name", "email@email.com", BigDecimal.valueOf(10000), BigDecimal.valueOf(9500) );
        assertThrows(UserIncomeNotQualitiedException.class, () -> classUnderTest.validExpendableInCome(userDTO));
    }

    @Test
    void invalidIncome() {
        UserDTO userDTO = new UserDTO(1l,"name", "email@email.com", BigDecimal.valueOf(10000), BigDecimal.valueOf(8000) );
        assertDoesNotThrow(() -> classUnderTest.validExpendableInCome(userDTO));
    }

    @Test
    void validAssociation() {
        User user = new User("user", "email@email.com", BigDecimal.valueOf(10000), BigDecimal.valueOf(8000));
        Set<Account> objects = new HashSet<>();

        user.setAccounts(objects);
        assertDoesNotThrow( () -> classUnderTest.validAccountAssociation(user));
    }

    @Test
    void inValidAssociation() {
        User user = new User("user", "email@email.com", BigDecimal.valueOf(10000), BigDecimal.valueOf(8000));
        Set<Account> objects = new HashSet<>();
        objects.add(mock(Account.class));
        objects.add(mock(Account.class));
        user.setAccounts(objects);
        assertThrows(UserAccountAssociationExceededException.class, () -> classUnderTest.validAccountAssociation(user));
    }


}