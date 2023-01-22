package co.zip.candidate.userapi.validator;

import co.zip.candidate.userapi.dto.UserDTO;
import co.zip.candidate.userapi.exception.UserAccountAssociationExceededException;
import co.zip.candidate.userapi.exception.UserIncomeNotQualitiedException;
import co.zip.candidate.userapi.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SimpleIncomeValidator {

    private BigDecimal minExpendableIncome;


    private int maxNubmerAccountAssociation;

    public SimpleIncomeValidator(@Value("${user-api.minExpendableIncome}") BigDecimal minExpendableIncome, @Value("${user-api.maxNubmerAccountAssociation}") int maxNubmerAccountAssociation) {
        this.minExpendableIncome = minExpendableIncome;
        this.maxNubmerAccountAssociation = maxNubmerAccountAssociation;
    }


    public void validExpendableInCome(UserDTO userDTO) {
        if (userDTO.getMonthlySalary().subtract(userDTO.getMonthlyExpenses()).compareTo(this.minExpendableIncome) <= 0) {
            throw new UserIncomeNotQualitiedException("Monthly expendable income is less that min required");
        }
    }

    public void validAccountAssociation(User user) {
        if (user.getAccounts().size() > this.maxNubmerAccountAssociation)
            throw new UserAccountAssociationExceededException("User max accounts association reached");
    }
}

