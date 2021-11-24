package com.wildcodeschool.cerebook.controller;

import com.wildcodeschool.cerebook.entity.CerebookUser;
import com.wildcodeschool.cerebook.entity.Post;
import com.wildcodeschool.cerebook.repository.CerebookUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

@Controller
@RequestMapping("/profiles")
public class ProfileController extends AbstractCrudLongController<CerebookUser> {

    @Autowired
    private CerebookUserRepository cerebookUserRepository;

    @GetMapping("/{id}/getById")
    public String getById(Model model, @PathVariable("id") Long id) {
        model.addAttribute("elements", cerebookUserRepository.findCerebookUserById(id));
        model.addAttribute("elementFields", getElementFields());
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
        return new String[]{"profilImage", "background", "superPowers", "genre", "bio"};
    }
}
