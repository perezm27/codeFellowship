package com.perezm27.CodeFellowship.controllers;

import com.perezm27.CodeFellowship.models.ApplicationUser;
import com.perezm27.CodeFellowship.models.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ApplicationUserController {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @PostMapping("/users")
    public RedirectView createUser(String username, String password, String firstname, String lastname, Date dateOfBirth, String bio){
        ApplicationUser newUser = new ApplicationUser(
                username,
//  Handles hashing/salting of password
                encoder.encode(password),
                firstname,
                lastname,
                dateOfBirth,
                bio);
        applicationUserRepository.save(newUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("/myprofile");
    }

//  User profile
    @GetMapping("/myprofile")
    public String getProfilePage(Principal p, Model m){
            ApplicationUser currentUser = applicationUserRepository.findByUsername(p.getName());
            m.addAttribute("user", currentUser);
        return "myprofile";
    }

    @GetMapping("/users")
    public String getUsers(Principal p, Model m){
        List<ApplicationUser> u = applicationUserRepository.findAll();
        ApplicationUser currentUser = applicationUserRepository.findByUsername(p.getName());
        m.addAttribute("user", currentUser);
        m.addAttribute("allusers", u);
        return "users";
    }

    @GetMapping("/users/{id}")
    public String getUsersId(@PathVariable long id, Principal p, Model m){
        ApplicationUser currentUser = applicationUserRepository.findById(id).get();
        m.addAttribute("user", currentUser);
        m.addAttribute("currentUser", p);
        return "myprofile";
    }

//	Root
    @GetMapping("/")
    public String getRoot(Principal p, Model m) {
//        m.addAttribute("user", p);
        return "root";
    }

//	SignUp route
    @GetMapping("/signup")
    public String getSignup() {
        return "registration";
    }

//	Login route
    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }
}
