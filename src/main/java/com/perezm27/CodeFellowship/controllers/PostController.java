package com.perezm27.CodeFellowship.controllers;

import com.perezm27.CodeFellowship.models.ApplicationUser;
import com.perezm27.CodeFellowship.models.ApplicationUserRepository;
import com.perezm27.CodeFellowship.models.UserPost;
import com.perezm27.CodeFellowship.models.UserPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.sql.Date;

@Controller
public class PostController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;
    @Autowired
    UserPostRepository userPostRepository;

    @GetMapping("/post")
    public String getPost(Principal p, Model m){
        ApplicationUser currentUser = applicationUserRepository.findByUsername(p.getName());
        m.addAttribute("user", currentUser);
        return "myprofile";
    }

    @PostMapping("/post")
    public RedirectView createPost(String body, Date createdAt,  Principal p){
        ApplicationUser loggedInUser = applicationUserRepository.findByUsername(p.getName());
        UserPost post = new UserPost(body, createdAt, loggedInUser);
        userPostRepository.save(post);
        return new RedirectView("myprofile");
    }

}
