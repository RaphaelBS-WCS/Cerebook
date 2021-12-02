package com.wildcodeschool.cerebook.controller;

import com.wildcodeschool.cerebook.entity.CerebookUser;
import com.wildcodeschool.cerebook.entity.User;
import com.wildcodeschool.cerebook.repository.CerebookUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class IndexController extends AbstractCrudLongController<CerebookUser> {

    @Autowired
    private CerebookUserRepository cerebookUserRepository;

    @GetMapping("/")
    public String index(Model model, Principal principal) {

        model.addAttribute("cerebookUser", getCurrentCerebookUser(principal));
        model.addAttribute("cerebookUserFields", getElementFields());
   /*     // envoyer age
        model.addAttribute("date", calculateAge(cerebookUserRepository.findCerebookUserById(id).getBirthDate(), java.time.LocalDate.now()));
*/

        return "index";
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
        return null;
    }

    @Override
    protected String[] getElementFields() {
        return new String[]{"profilImage", "background", "superPowers", "genre", "bio", "membership", "user", "birthDate"};
    }

    @Override
    protected Class<CerebookUser> getElementClass() {
        return null;
    }
}
