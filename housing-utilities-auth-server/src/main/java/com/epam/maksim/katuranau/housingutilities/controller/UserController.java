package com.epam.maksim.katuranau.housingutilities.controller;

import com.epam.maksim.katuranau.housingutilities.model.UserRegistrationDto;
import com.epam.maksim.katuranau.housingutilities.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserController {

    @Value("${login.external.url}")
    private String LOGIN_URL;
    private UserInfoService userService;

    @Autowired
    public UserController(final UserInfoService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegisterPage(final Model model) {

        model.addAttribute("userRegistrationDto", new UserRegistrationDto());
        model.addAttribute("loginUrl", LOGIN_URL);
        return "registerPage";
    }

    @PostMapping("/register/submit")
    public String registerUser(@ModelAttribute @Valid final UserRegistrationDto user,
                               final BindingResult validationResults, final Model model) {
        if (validationResults.hasErrors()) {
            model.addAttribute("registrationErrorMessage",
                    "Validation exception, fields should not be empty");
            return "registerPage";
        }
        if (userService.getUserInfoByUserName(user.getLogin()) != null) {
            model.addAttribute("registrationErrorMessage",
                    "can't register user, user with username = \"" + user.getLogin() + "\" already exist");
            return "registerPage";
        }
        userService.addUser(user);
        return "redirect:" + LOGIN_URL;
    }
}
