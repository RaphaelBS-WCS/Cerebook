package com.wildcodeschool.cerebook.project.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    // NRO 2021-11-10 : useless JoinColumn
    @JoinColumn(name = "author_id")
    private CerebookUser authorId;

    @ManyToOne
    // NRO 2021-11-10 : useless JoinColumn
    @JoinColumn(name = "event_id")
    private Event eventId;

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

    public CerebookUser getAuthorId() {
        return authorId;
    }

    public void setAuthorId(CerebookUser authorId) {
        this.authorId = authorId;
    }

    public Event getEventId() {
        return eventId;
    }

    public void setEventId(Event eventId) {
        this.eventId = eventId;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}