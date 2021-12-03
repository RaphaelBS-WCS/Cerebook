package com.wildcodeschool.cerebook.controller;

import com.wildcodeschool.cerebook.entity.CerebookUser;
import com.wildcodeschool.cerebook.entity.User;
import com.wildcodeschool.cerebook.repository.CerebookUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

<<<<<<< HEAD
=======
import javax.servlet.http.HttpServletRequest;
>>>>>>> f3c0bcb (resolu conflict)
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.Period;

@Controller
public class IndexController extends AbstractCrudLongController<CerebookUser> {

    @Autowired
    private CerebookUserRepository cerebookUserRepository;

    @GetMapping("/")
    public String index(Model model, Principal principal, HttpServletResponse response) throws IOException {
        try {
            model.addAttribute("cerebookUser", getCurrentCerebookUser(principal));
        } catch (Exception e) {
            response.sendRedirect("/login");
        }

        model.addAttribute("cerebookUserFields", getElementFields());

        return "index";
    }

    @GetMapping("/profiles/{id}")
    public String getById(Model model, @PathVariable Long id) {
        model.addAttribute("cerebookUser", cerebookUserRepository.findCerebookUserById(id));

        return getControllerRoute() + "/getById";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @Override
    protected JpaRepository<CerebookUser, Long> getRepository() {
        return cerebookUserRepository;
    }

    @Override
    protected String getControllerRoute() {
        return "profiles";
    }




    @Override
    protected String[] getElementFields() {
        return new String[]{"profilImage", "background", "superPowers", "genre", "bio", "membership", "user", "birthDate"};
    }

    // creation de la methode pour calculer age
    public int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        return Period.between(birthDate, currentDate).getYears();
    }
}
