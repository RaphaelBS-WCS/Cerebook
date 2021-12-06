package com.wildcodeschool.cerebook.controller;

import com.wildcodeschool.cerebook.entity.Post;
import com.wildcodeschool.cerebook.repository.CerebookUserRepository;
import com.wildcodeschool.cerebook.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Controller
@RequestMapping("/posts")
public class PostController extends AbstractCrudLongController<Post> {
    @Autowired
    private PostRepository postRepositoryDAO;

    @Autowired
    private CerebookUserRepository cerebookUserRepository;

    @Override
    @GetMapping("")
    public String getAll(Model model) {
        return getControllerRoute() + "/getAllByAuthor";
    }

    @GetMapping("/{CerebookUser.id}/getAllByAuthor")
    public String getAllPostsByAuthor(Model model, Principal principal) {
        model.addAttribute("allElements", postRepositoryDAO.findAllByAuthorOrderByCreatedAtDesc(getCurrentCerebookUser(principal)));
        model.addAttribute("elementFields", getElementFields());
        return getControllerRoute() + "/getAllByAuthor";
    }

    @GetMapping("/{CerebookUser.id}/getAllByAuthorOrByAuthorFriends")
    public String getAllPostsByCerebookUserFriendsOrByAuthor(Model model, @PathVariable("CerebookUser.id") String id, Principal principal) {
        model.addAttribute("currentUser", getCurrentCerebookUser(principal));
        return getControllerRoute() + "/getAllByAuthorOrByAuthorFriends";
    }

    @Override
    @PostMapping("/create")
    public String create(HttpServletRequest hsr, Post post) {
        preProcessElement(post, hsr);
        getRepository().save(post);

        return hsr.getRequestURI();
    }

    @Override
    protected JpaRepository<Post, Long> getRepository() {
        return postRepositoryDAO;
    }

    @Override
    protected String getControllerRoute() {
        return "posts";
    }

    @Override
    protected String[] getElementFields() {
        return new String[]{"content", "video"};
    }

    @Override
    protected Class<Post> getElementClass() {
        return Post.class;
    }

    @Override
    protected void preProcessElement(Post post, HttpServletRequest hsr) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.now();
        post.setCreatedAt(LocalDate.from(date));
        post.setAuthor(getCurrentCerebookUser(hsr.getUserPrincipal()));
    }

}
