package com.wildcodeschool.cerebook.controller;

import com.wildcodeschool.cerebook.entity.*;
import com.wildcodeschool.cerebook.repository.CerebookUserRepository;
import com.wildcodeschool.cerebook.repository.EventCategoryRepository;
import com.wildcodeschool.cerebook.repository.EventRepository;
import com.wildcodeschool.cerebook.repository.UserRepository;
import com.wildcodeschool.cerebook.service.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
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


    @GetMapping("")
    public String showEvents(@ModelAttribute Event event, Model model, Principal principal) {

        String username = principal.getName();
        User currentUser = userRepository.getUserByUsername(username);

        try {
            model.addAttribute("eventsByUser", eventRepository.getAllEventsByCreator(currentUser));
        } catch (Exception e) {
            return "redirect:/events";
        }


        return "events/eventsByUser";
    }

    @GetMapping("/show/{eventId}")
    public String showEventsById(@ModelAttribute Event event, Model model, Principal principal, @PathVariable Long eventId) {

        String username = principal.getName();
        User currentUser = userRepository.getUserByUsername(username);

        try {
            model.addAttribute("eventByIdAndUser", eventRepository.getEventByIdAndByCreator(currentUser, eventId));
        } catch (Exception e) {
            return "events/showEventById";
        }

        return "events/showEventById";
    }


    @GetMapping("/edit/{id}")
    public String updateEvent(@PathVariable("id") Long id, @ModelAttribute Event event, Model model, Principal principal) {
        String username = principal.getName();
        User currentUser = userRepository.getUserByUsername(username);

        model.addAttribute("eventByIdAndUser", eventRepository.getEventByIdAndByCreator(currentUser, id));
        model.addAttribute("eventCategories", eventCategoryRepository.getAllEventCategories());
        return "events/update";
    }


    @PostMapping("/update/{id}")
    public String modifyEvent(@PathVariable("id") Long id,
                              @Valid @ModelAttribute Event event,
                              Model model,
                              Principal principal,
                              @RequestParam("date") Date eventDate,
                              BindingResult bindingResult,
                              @RequestParam("backgroundPhotoFile") MultipartFile multipartFile) throws ServletException, IOException
    {

        Event currentEvent = eventRepository.getEventById(id);

     if (!principal.getName().equals(currentEvent.getCreator().getUsername())) {
         throw new IOException("User not allowed.");
     }
        String username = principal.getName();
        User currentUser = userRepository.getUserByUsername(username);

        long millis = System.currentTimeMillis();
        Date localDate = new Date(millis);

        model.addAttribute("eventByIdAndUser", eventRepository.getEventByIdAndByCreator(currentUser, id));
        model.addAttribute("eventCategories", eventCategoryRepository.getAllEventCategories());


        if (eventDate.before(localDate)) {
            bindingResult.rejectValue("date", "error.date", "The event date cannot be before the current date.");
        }
        if (bindingResult.hasErrors()) {
            return "events/update";
        } else {
            event.setCreatedAt(currentEvent.getCreatedAt());
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            event.setBackgroundPhoto(fileName);
            event.setCreator(currentUser);
            event.setUpdatedAt(localDate);
            Event updatedEvent = eventRepository.save(event);
            String uploadDir = "src/main/resources/public/images/WebContent/events-uploaded-files/" + updatedEvent.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

            return "redirect:/events/show/" +  id;
        }

    }


    @GetMapping("/create")
    public String createEvent(Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("eventCategories", eventCategoryRepository.getAllEventCategories());
        return "events/create";
    }

    @PostMapping("/create")
    public String saveEvent(@Valid @ModelAttribute Event event,
                            Model model,
                            BindingResult bindingResult,
                            Principal principal,
                            @RequestParam("date") Date eventDate,
                            @RequestParam("backgroundPhotoFile") MultipartFile multipartFile) throws ServletException, IOException {

        long millis = System.currentTimeMillis();
        Date localDate = new Date(millis);

        model.addAttribute("eventCategories", eventCategoryRepository.getAllEventCategories());

        if (eventDate.before(localDate)) {
            bindingResult.rejectValue("date", "error.date", "The event date cannot be before the current date.");
        }

        if (bindingResult.hasErrors()) {
            return "events/create";
        } else {

            event.setCreatedAt(localDate);

            String username = principal.getName();
            User currentUser = userRepository.getUserByUsername(username);
            event.setCreator(currentUser);
            /*     Getting the name of the uploaded file and persisting it to the database */
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            event.setBackgroundPhoto(fileName);

            Event savedEvent = eventRepository.save(event);
            String uploadDir = "src/main/resources/public/images/WebContent/events-uploaded-files/" + savedEvent.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

            return "redirect:/events";
        }
    }

}
