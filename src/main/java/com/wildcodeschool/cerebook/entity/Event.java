package com.wildcodeschool.cerebook.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Date date;

    @OneToMany(mappedBy =  "eventId", cascade = CascadeType.ALL)
    private List<Post> posts;

    @OneToMany(mappedBy =  "eventId", cascade = CascadeType.ALL)
    private List<Picture> pictures;

    @ManyToOne
    @JoinColumn(name = "cerebookUser_id")
    private CerebookUser creator;

    @ManyToOne
    @JoinColumn(name = "EventCategory_Id")
    private EventCategory eventCategoryId;

    @OneToMany(mappedBy =  "eventId", cascade = CascadeType.ALL)
    private List<Participation> participants;

    @ManyToMany
    @JoinTable(name = "membership_event",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "memebrship_id"))
    private List<Membership> memberships;

    public Event() {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    public CerebookUser getCreator() {
        return creator;
    }

    public void setCreator(CerebookUser creator) {
        this.creator = creator;
    }

    public EventCategory getEventCategoryId() {
        return eventCategoryId;
    }

    public void setEventCategoryId(EventCategory eventCategoryId) {
        this.eventCategoryId = eventCategoryId;
    }

    public List<Participation> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participation> participants) {
        this.participants = participants;
    }

    public List<Membership> getMemberships() {
        return memberships;
    }

    public void setMemberships(List<Membership> memberships) {
        this.memberships = memberships;
    }
}