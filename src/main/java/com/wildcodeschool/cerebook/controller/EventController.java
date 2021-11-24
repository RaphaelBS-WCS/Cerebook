package com.wildcodeschool.cerebook.controller;

import com.wildcodeschool.cerebook.entity.*;
import com.wildcodeschool.cerebook.repository.CerebookUserRepository;
import com.wildcodeschool.cerebook.repository.EventCategoryRepository;
import com.wildcodeschool.cerebook.repository.EventRepository;
import com.wildcodeschool.cerebook.repository.UserRepository;
import com.wildcodeschool.cerebook.service.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.sql.Date;
import java.util.Objects;

@Controller
@RequestMapping("/events")
public class EventController {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EventCategoryRepository eventCategoryRepository;


    @GetMapping("/")
    public String showEvents(@ModelAttribute Event event, Model model, Principal principal) {

        String username = principal.getName();
        User currentUser = userRepository.getUserByUsername(username);

        model.addAttribute("eventsByUser", eventRepository.getAllEventsByCreator(currentUser));

        return "events/eventsByUser";
    }

    @GetMapping("/create")
    public String createEvent(Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("eventCategories", eventCategoryRepository.getAllEventCategories());
        return "events/create";
    }

    @PostMapping("/create")
    public String saveEvent(@Valid @ModelAttribute Event event, BindingResult bindingResult, Principal principal, @RequestParam("backgroundPhotoFile") MultipartFile multipartFile) throws ServletException, IOException {


        if (bindingResult.hasErrors()) {
            return "events/create";
        } else {
            long millis = System.currentTimeMillis();
            Date localDate = new Date(millis);
            event.setCreatedAt(localDate);

            String username = principal.getName();
            User currentUser = userRepository.getUserByUsername(username);
            event.setCreator(currentUser);

            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));

            event.setBackgroundPhoto(fileName);

            Event savedEvent = eventRepository.save(event);

            String uploadDir = "src/main/resources/public/images/WebContent/events-uploaded-files/" + savedEvent.getId();

            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

            return "redirect:/";
        }

    }

}
