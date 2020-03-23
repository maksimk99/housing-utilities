package com.epam.maksim.katuranau.housingutilities.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

public class UserRegistrationDto {

    @NotBlank(message = "user login should not be empty")
    private String login;
    @NotBlank(message = "user password should not be empty")
    private String password;

    public UserRegistrationDto() {
    }

    public UserRegistrationDto(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRegistrationDto)) return false;
        UserRegistrationDto that = (UserRegistrationDto) o;
        return Objects.equals(login, that.login) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }
}
