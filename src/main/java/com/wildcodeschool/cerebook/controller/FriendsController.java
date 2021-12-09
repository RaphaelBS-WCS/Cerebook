package com.wildcodeschool.cerebook.controller;

import com.wildcodeschool.cerebook.entity.CerebookUser;
import com.wildcodeschool.cerebook.entity.CerebookUserFriends;
import com.wildcodeschool.cerebook.entity.ids.CerebookUserFriendsId;
import com.wildcodeschool.cerebook.repository.CerebookUserFriendsRepository;
import com.wildcodeschool.cerebook.repository.CerebookUserRepository;
import com.wildcodeschool.cerebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Controller
@RequestMapping("/friends")
public class FriendsController{
    @Autowired
    CerebookUserRepository cerebookUserRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CerebookUserFriendsRepository cerebookUserFriendsRepository;

    @GetMapping
    public String showFriends(@ModelAttribute CerebookUserFriends cerebookUserFriends, Model model, Principal principal, CerebookUser cerebookUser) {

        CerebookUser currentCerebookUser = userRepository.getUserByUsername(principal.getName()).getCerebookUser();

        // Get the friend list:  retrieve the rows of friendship with isAccepted set to true
        List<CerebookUserFriends> friendsList = cerebookUserFriendsRepository.findCerebookUserFriendsByOriginatedUserAndAccepted(currentCerebookUser);
        model.addAttribute("friends", friendsList);
            // Get the number of friends
            int countFriend = 0;
            for (CerebookUserFriends friend: friendsList) {
                countFriend++;
            }
            model.addAttribute("countFriend", countFriend);
        // Get the invitations : get friendrequests(Or pending requests to be Approved): get all the rows with isAccepted = false.
        List<CerebookUserFriends> friendsRequests = cerebookUserFriendsRepository.getAllFriendRequests(currentCerebookUser);
        model.addAttribute("friendrequests", friendsRequests);

        // Get the suggestion friend list
        model.addAttribute("suggestfriends", cerebookUserRepository.findFriendsSuggestions(currentCerebookUser));

        return "friends/getAll";
    }

    @Transactional
    @GetMapping("/{friendId}/accept")
    public String acceptFriend(@PathVariable Long friendId, Principal principal) {

        // Get the current user and the user who sent the invitation
        CerebookUser currentCerebookUser = userRepository.getUserByUsername(principal.getName()).getCerebookUser();
        CerebookUser friend = cerebookUserRepository.getById(friendId);

        // Run the request that add the new-added friend in to the friend list
        cerebookUserFriendsRepository.acceptFriend(currentCerebookUser, friend);

        return "redirect:/friends";
    }

    @GetMapping("/{friendId}/addFriend")
    public String addFriend(@PathVariable Long friendId, Principal principal) {

        CerebookUser currentCerebookUser = userRepository.getUserByUsername(principal.getName()).getCerebookUser();
        CerebookUser friend = cerebookUserRepository.getById(friendId);

        if (!friend.equals(currentCerebookUser)) {

            currentCerebookUser.getFriends().add(friend);
            cerebookUserRepository.save(currentCerebookUser);

            friend.getFriends().add(currentCerebookUser);
            cerebookUserRepository.save(friend);

            // Remove the friend from suggestion friend list
            // Get the list of all my suggestion friends, define the friend that i added and delete him from the suggestion list
            List<CerebookUser> suggestfriends = cerebookUserRepository.findFriendsSuggestions(currentCerebookUser);
            suggestfriends.remove(friend);
        }
        return "redirect:/friends";
    }
}
