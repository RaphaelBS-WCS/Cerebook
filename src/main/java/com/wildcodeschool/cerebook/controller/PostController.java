package com.wildcodeschool.cerebook.controller;

import com.wildcodeschool.cerebook.entity.CerebookUser;
import com.wildcodeschool.cerebook.entity.Post;
import com.wildcodeschool.cerebook.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;


@Controller
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostRepository postRepositoryDAO;



    @PostMapping("/create")
    public String Create(@ModelAttribute Post post) {
        if(post.getId() != null) {
            throw new AccessDeniedException("403 forbidden");
        }
        long millis=System.currentTimeMillis();
        Date date = new Date(millis);
        post.setCreatedAt(date);
        post.setAuthor(User.getCerebookUser);
        postRepositoryDAO.save(post);

        return "redirect:/cerebookUser";
    }

    @RequestMapping(value="/post/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable String id, @RequestBody Post post) {
        postRepositoryDAO.save(post);
    }

    @RequestMapping(value = "/post/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        postRepositoryDAO.deleteById(id);
    }
}
