package com.yeva.dauletova.controllers;

import com.yeva.dauletova.models.User;
import com.yeva.dauletova.repositories.UserRepository;
import com.yeva.dauletova.services.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")


public class AuthController {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserService userService;
    @Autowired
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }
    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user",new User());
        return "registration";
    }
    @PostMapping("/register")
    public String register(Model model, @ModelAttribute("user")@Valid User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            System.out.println("errors");
            return "registration";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        try {
            userService.sendRegistrationConfirmationEmail(user);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/auth/login";
    }
    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("user",new User());
        return "login";
    }
    @GetMapping("/confirm-email")
    public String confirmEmail(@RequestParam("token")String token){
     if(userService.verifyUser(token)){
     return "responses/verifiedToken";
     }
     return "responses/notVerifiedToken";
    }

}

