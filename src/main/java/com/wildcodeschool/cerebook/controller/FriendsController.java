package com.wildcodeschool.cerebook.controller;

import com.wildcodeschool.cerebook.entity.CerebookUser;
import com.wildcodeschool.cerebook.entity.CerebookUserFriends;
import com.wildcodeschool.cerebook.entity.Event;
import com.wildcodeschool.cerebook.entity.User;
import com.wildcodeschool.cerebook.repository.CerebookUserFriendsRepository;
import com.wildcodeschool.cerebook.repository.CerebookUserRepository;
import com.wildcodeschool.cerebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.sql.Date;
import java.util.*;

@Controller
public class FriendsController{
    @Autowired
    CerebookUserRepository cerebookUserRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CerebookUserFriendsRepository cerebookUserFriendsRepository;

    @GetMapping("/friends")
    public String showFriends(@ModelAttribute CerebookUserFriends cerebookUserFriends, Model model, Principal principal) {

        CerebookUser currentCerebookUser = userRepository.getUserByUsername(principal.getName()).getCerebookUser();

        // Get the friend list
        List<CerebookUserFriends> friendsList = cerebookUserFriendsRepository.findCerebookUserFriendsByOriginatedUser(currentCerebookUser);
        model.addAttribute("friends", friendsList);

        // Get the suggestion friend list
        model.addAttribute("suggestfriends", cerebookUserRepository.findFriendsSuggestions(currentCerebookUser));

        return "friends/getAll";
    }

}
