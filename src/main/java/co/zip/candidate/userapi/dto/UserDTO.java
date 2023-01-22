package co.zip.candidate.userapi.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class UserDTO {
    private Long id;

    @NotEmpty
    @Size(min = 2, message = "user name should have at least 2 characters")
    private String name;

    @NotEmpty
    @Email
    private String email;


    @DecimalMin(value = "1000.0", inclusive = false, message = "we can not take monthlySalary that lower than 1000")
    @DecimalMax(value = "100000.0", message = "we can not take monthlySalary that exceeds 100000.0")
    @Digits(integer = 6, fraction = 2)
    private BigDecimal monthlySalary;
    @DecimalMin(value = "100.0", inclusive = false, message = "we can not take monthlyExpenses that lower than 100")
    @DecimalMax(value = "100000.0", message = "we can not take monthlyExpenses that exceeds 100000.0")
    @Digits(integer = 6, fraction = 2)
    private BigDecimal monthlyExpenses;

    public UserDTO(Long id, String name, String email, BigDecimal monthlySalary, BigDecimal monthlyExpenses) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.monthlySalary = monthlySalary;
        this.monthlyExpenses = monthlyExpenses;
    }

    public UserDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(BigDecimal monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    public BigDecimal getMonthlyExpenses() {
        return monthlyExpenses;
    }

    public void setMonthlyExpenses(BigDecimal monthlyExpenses) {
        this.monthlyExpenses = monthlyExpenses;
    }
}
