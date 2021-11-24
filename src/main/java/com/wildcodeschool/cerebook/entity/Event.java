package com.wildcodeschool.cerebook.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.List;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Please enter your username.")
    @NotNull(message = "Please enter your username.")
    @Size(min = 3, max = 45, message = "The size of your username should be more than 2 characters and less than 45.")
    private String name;


    private Date date;
    private Date createdAt;
    private String backgroundPhoto;


    @OneToMany(mappedBy =  "event", cascade = CascadeType.ALL)
    private List<Post> posts;

    @OneToMany(mappedBy =  "event", cascade = CascadeType.ALL)
    private List<Picture> pictures;

    @ManyToOne
    private User creator;

    @ManyToOne(fetch=FetchType.EAGER)
    private EventCategory eventCategory;

    @OneToMany(mappedBy =  "event", cascade = CascadeType.ALL)
    private List<Participation> participants;

    @ManyToMany
    @JoinTable(name = "membership_event",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "membership_id"))
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

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public EventCategory getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(EventCategory eventCategory) {
        this.eventCategory = eventCategory;
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

    public String getBackgroundPhoto() {
        return backgroundPhoto;
    }

    public void setBackgroundPhoto(String backgroundPhoto) {
        this.backgroundPhoto = backgroundPhoto;
    }

    public String getBackgroundPhotoPath() {
        if (backgroundPhoto == null || id == null) return null;

        return "src/main/resources/public/images/WebContent/events-uploaded-files" + id + "/" + backgroundPhoto;
    }

    public String getBackgroundPhotoShortPath() {
        if (backgroundPhoto == null || id == null) return null;

        return "images/WebContent/events-uploaded-files" + id + "/" + backgroundPhoto;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}