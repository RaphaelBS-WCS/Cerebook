package com.wildcodeschool.cerebook.controller;

import com.wildcodeschool.cerebook.entity.CerebookUser;
import com.wildcodeschool.cerebook.entity.Post;
import com.wildcodeschool.cerebook.repository.CerebookUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;


@Controller
@RequestMapping("/profiles")
public class ProfileController extends AbstractCrudLongController<CerebookUser> {

    @Autowired
    private CerebookUserRepository cerebookUserRepository;

    @GetMapping("/{id}/getById")
    public String getById(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", cerebookUserRepository.findCerebookUserById(id));
        model.addAttribute("userFields", getElementFields());
        // envoyer age
        model.addAttribute("date", calculateAge(cerebookUserRepository.findCerebookUserById(id).getBirthDate(), java.time.LocalDate.now()));
        return getControllerRoute() + "/getById";
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
        return new String[]{"profilImage", "background", "superPowers", "genre", "bio", "membership", "user"};
    }

    @Override
    @PostMapping("/{id}/update")
    public String update(HttpServletRequest hsr, @PathVariable("id") String id, @ModelAttribute CerebookUser cerebookUser) {
        preProcessElement(cerebookUser, hsr);
        getRepository().save(cerebookUser);

        return "redirect:/";
    }

    // creation de la methode pour calculer age
    public int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        return Period.between(birthDate, currentDate).getYears();
    }
}
