package com.perezm27.CodeFellowship.controllers;

import com.perezm27.CodeFellowship.models.ApplicationUser;
import com.perezm27.CodeFellowship.models.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.Set;

@Controller
public class FeedController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @PostMapping("/feed")
    public RedirectView addFollowUser(long followedUser, Principal p){
        ApplicationUser followingUser = applicationUserRepository.findByUsername(p.getName());
        followingUser.addFollowedUsers(applicationUserRepository.findById(followedUser).get());
        applicationUserRepository.save(followingUser);
        return new RedirectView("/feed");
    }

    @GetMapping("/feed")
    public String getFeed(Principal p, Model m){
        ApplicationUser userThatIFollow = applicationUserRepository.findByUsername(p.getName());
        Set<ApplicationUser> test = userThatIFollow.getUsersThatIFollow();
        m.addAttribute("users", applicationUserRepository.findAll());
        m.addAttribute("followers", test);
        return "feed";
    }
}
