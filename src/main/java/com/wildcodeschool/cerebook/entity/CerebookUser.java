package com.wildcodeschool.cerebook.entity;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Entity
public class CerebookUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String profilImage;
    private String background;
    private LocalDate birthDate;
    private String superPowers;
    private String genre;
    @Column(columnDefinition="TEXT")
    private String bio;
    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Post> posts;

    @ManyToOne
    private Membership membership;

    @ManyToMany(cascade = CascadeType.REFRESH)
    private List<CerebookUser> friends;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private List<Event> events;

    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL)
    private List<Participation> participations;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Comment> comments;

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public CerebookUser() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfilImage() {
        return profilImage;
    }

    public void setProfilImage(String profilImage) {
        this.profilImage = profilImage;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getSuperPowers() {
        return superPowers;
    }

    public void setSuperPowers(String superPowers) {
        this.superPowers = superPowers;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public List<CerebookUser> getFriends() {
        return friends;
    }

    public void setFriends(List<CerebookUser> friends) {
        this.friends = friends;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<Participation> getParticipations() {
        return participations;
    }

    public void setParticipations(List<Participation> participations) {
        this.participations = participations;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}