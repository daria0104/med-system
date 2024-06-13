package com.example.controller;

import com.example.entity.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import com.example.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService){
        this.userService = userService;
    }


    @GetMapping("/login")
    public String login(HttpSession session){
        System.out.println("login");
        System.out.println(session.getId());
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        System.out.println("entered to register");
//        System.out.println(((Authentication) principal).getAuthorities().stream().toList());
//        if (!isAdmin(principal)) {
//            System.out.println("!is Admin");
//            return "redirect:/";
//        }
        User user = new User();
        model.addAttribute("user", user);
        System.out.println("redirect");
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, Principal principal){
        User existingUser = userService.getUserByEmail(user.getEmail());

        System.out.println("register/save");
        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("user", user);
            return "register";
        }

        userService.saveUser(user);
        return "redirect:/";
    }
}