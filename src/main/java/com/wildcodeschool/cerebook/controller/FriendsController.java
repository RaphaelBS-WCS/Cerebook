package com.wildcodeschool.cerebook.controller;

import com.wildcodeschool.cerebook.entity.CerebookUser;
import com.wildcodeschool.cerebook.entity.CerebookUserFriends;
import com.wildcodeschool.cerebook.entity.Event;
import com.wildcodeschool.cerebook.entity.User;
import com.wildcodeschool.cerebook.repository.CerebookUserFriendsRepository;
import com.wildcodeschool.cerebook.repository.CerebookUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;
import java.util.*;

@Controller
public class FriendsController{
    @Autowired
    CerebookUserRepository cerebookUserRepository;

    @Autowired
    CerebookUserFriendsRepository cerebookUserFriendsRepository;


    @GetMapping("/friends")
    public String showFriends(@ModelAttribute CerebookUserFriends cerebookUserFriends, Model model, Principal principal) {

        // Get the friend list
        List<CerebookUserFriends> friendsList = cerebookUserFriendsRepository.findAll();
        model.addAttribute("friends", friendsList);

        return "friends/getAll";
    }
}
