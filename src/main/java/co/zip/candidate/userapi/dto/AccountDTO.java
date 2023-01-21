package co.zip.candidate.userapi.dto;

import jakarta.validation.constraints.*;


public class AccountDTO {
    private Long id;

    @NotEmpty
    @Size(min = 2, message = "account name should have at least 2 characters")
    private String name;


    public AccountDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public AccountDTO() {
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

}
