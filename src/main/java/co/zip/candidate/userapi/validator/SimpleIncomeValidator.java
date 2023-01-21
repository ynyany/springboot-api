package co.zip.candidate.userapi.validator;

import co.zip.candidate.userapi.dto.UserDTO;
import co.zip.candidate.userapi.exception.UserIncomeNotQualitiedException;
import co.zip.candidate.userapi.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SimpleIncomeValidator {
    @Value("${user-api.minExpendableIncome}")
    private BigDecimal minExpendableIncome;
    public SimpleIncomeValidator() {
    }

    public void validExpendableInCome(UserDTO userDTO) {
        if (userDTO.getMonthlySalary().subtract(userDTO.getMonthlyExpenses()).compareTo(this.minExpendableIncome) <= 0) {
            throw new UserIncomeNotQualitiedException("Monthly expendable income is less that min required");
        }
    }
}
