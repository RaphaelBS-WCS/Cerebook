package com.wildcodeschool.cerebook.controller;

import com.wildcodeschool.cerebook.entity.CerebookUser;
import com.wildcodeschool.cerebook.entity.User;
import com.wildcodeschool.cerebook.entity.Post;
import com.wildcodeschool.cerebook.repository.PostRepository;
import com.wildcodeschool.cerebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Date;
import java.util.List;


@Controller
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostRepository postRepositoryDAO;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Principal principal;

    User currentUser = userRepository.getUserByUsername(principal.getName());
    CerebookUser cerebookUser = currentUser.getCerebookUser();

    @GetMapping("/{cerebookUser.id}/getAllByAuthor")
    public List<Post> getAllPostsByAuthor() {
        return postRepositoryDAO.findAllByAuthor(cerebookUser);
    }

    @GetMapping("/{cerebookUser.id}/getAllByCerebookUserFriendsOrByAuthor")
    public List<Post> getAllPostsByCerebookUserFriendsOrByAuthor() {
        return  postRepositoryDAO.findAllByAuthorOrAuthor_Friends(cerebookUser, cerebookUser.getFriends());
    }

    @PostMapping("/create")
    public String Create(@ModelAttribute Post post) {
        if(post.getId() != null) {
            throw new AccessDeniedException("403 forbidden");
        }
        long millis=System.currentTimeMillis();
        Date date = new Date(millis);
        post.setCreatedAt(date);
        post.setAuthor(currentUser.getCerebookUser());
        postRepositoryDAO.save(post);

        return "redirect:/cerebookUser";
    }

    @RequestMapping(value="/{id}/update", method = RequestMethod.PUT)
    public void update(@PathVariable String id, @RequestBody Post post) {
        postRepositoryDAO.save(post);
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        postRepositoryDAO.deleteById(id);
    }
}
