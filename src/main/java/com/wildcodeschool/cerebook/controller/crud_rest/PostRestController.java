package com.wildcodeschool.cerebook.controller.crud_rest;

import com.wildcodeschool.cerebook.entity.CerebookUser;
import com.wildcodeschool.cerebook.entity.Post;
import com.wildcodeschool.cerebook.repository.CerebookUserRepository;
import com.wildcodeschool.cerebook.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.function.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostRestController extends AbstractCrudRestLongController<Post> {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CerebookUserRepository cerebookUserRepository;


    /*@Override
    public String getAll() {
        return getControllerRoute() + "/getAllByAuthor";
    }*/

    @GetMapping("/{CerebookUser.id}/getAllByAuthorOrByAuthorFriends")
    public List<Post> getAllPostsByCerebookUserFriendsOrByAuthor(@PathVariable("CerebookUser.id") String id) {
        CerebookUser cerebookUser = cerebookUserRepository.findById(parseId(id)).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND, getNotFoundMessage(id)
                ));
        return postRepository.findAllByAuthorOrByAuthorFriends(
                cerebookUser);
    }

    @Override
    protected JpaRepository<Post, Long> getRepository() {
        return postRepository;
    }

    @Override
    protected String getControllerRoute() {
        return "/api/posts";
    }

    @Override
    protected String[] getElementFields() {
        return new String[]{"content", "video"};
    }

    @Override
    protected Class<Post> getElementClass() {
        return Post.class;
    }
}
