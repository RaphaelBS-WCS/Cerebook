package com.wildcodeschool.cerebook.controller.crud_html;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;
import org.springframework.web.servlet.support.RequestContext;
import com.wildcodeschool.cerebook.controller.AbstractCrudController;
import com.wildcodeschool.cerebook.entity.CerebookUser;
import com.wildcodeschool.cerebook.repository.CerebookUserRepository;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.security.Principal;

import static org.springframework.web.servlet.function.RequestPredicates.accept;
import static org.springframework.web.servlet.function.RouterFunctions.route;

public abstract class AbstractCrudHtmlController<E, EK> extends AbstractCrudController<E, EK> {
    // <editor-fold desc="Route methods">
    @GetMapping("")
    public String getAll(Model model) {
        model.addAttribute("allElements", getRepository().findAll());
        addCommonModel(model);

        return getControllerRoute() + "/getAll";
    }

    @GetMapping("rest")
    public String getAllWithRest(Model model) {
        model.addAttribute("allElements", new ObjectMapper().valueToTree(getRepository().findAll()));
        addCommonModel(model);

        return getControllerRoute() + "/getAllWithRest";
    }

    @PostMapping("")
    public String create(HttpServletRequest hsr, @ModelAttribute E e) {
        preProcessElement(e, hsr);
        getRepository().save(e);

        return "redirect:/" + getControllerRoute();
    }

    @GetMapping("/{id}")
    public String getOne(@PathVariable("id") String id, Model model) {
        model.addAttribute("element", getElement(id));
        addCommonModel(model);

        return getControllerRoute() + "/getOne";
    }

    @GetMapping("/{id}/update")
    public String updateGet(@PathVariable("id") String id, Model model) {
        E e = getElement(id);
        postProcessElementForUpdateGet(e);

        model.addAttribute("element", e);
        addCommonModel(model);

        return getControllerRoute() + "/update";
    }

    private void addCommonModel(Model model) {
        model.addAttribute("elementFields", getElementFields());
        model.addAttribute("controllerRoute", getControllerRoute());
    }

    @PostMapping("/{id}/update")
    public String update(HttpServletRequest hsr, @PathVariable("id") String id, @ModelAttribute E e) {
        preProcessElement(e, hsr);
        getRepository().save(e);

        return "redirect:/" + getControllerRoute();
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") String id) {
        getRepository().deleteById(parseId(id));

        return "redirect:/" + getControllerRoute();
    }
    // </editor-fold>
}