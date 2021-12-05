package com.wildcodeschool.cerebook.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Boolean isUrl;
    @ManyToOne
    private CerebookUser author;

    @ManyToOne
    private Event event;

    @JsonBackReference
    @OneToMany(mappedBy =  "picture", cascade = CascadeType.REFRESH)
    private List<Post> posts;

    public Picture() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CerebookUser getAuthor() {
        return author;
    }

    public void setAuthor(CerebookUser author) {
        this.author = author;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Boolean getIsUrl() {
        return isUrl;
    }

    public void setIsUrl(Boolean url) {
        isUrl = url;
    }
}