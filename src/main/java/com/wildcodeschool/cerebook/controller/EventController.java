package com.wildcodeschool.cerebook.controller;

import com.wildcodeschool.cerebook.entity.Event;
import com.wildcodeschool.cerebook.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/events")
public class EventController {

    @GetMapping("/new")
    public String createEventForm(Model model) {
        model.addAttribute("event", new Event());

        return "registration";
    }
}
