package com.wildcodeschool.cerebook.project.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
public class CerebookUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String password;
    // NRO 2021-11-10 : it is better to handle the image
    //  with an object (like Picture or ProfilPicture)
    //  Why ? Because you will have to handle different sizes
    //  of the picture depending of the context, so having an
    //  object to deal with that seems good
    private String profilImage;
    private String background;
    private Date birthDate;
    private String superPowers;
    private String genre;
    @Column(columnDefinition="TEXT")
    private String bio;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Post> posts;

    @ManyToOne
    @JoinColumn(name = "membership_id")
    private Membership membershipId;

    @ManyToMany(cascade = CascadeType.REFRESH)
    private List<CerebookUser> friends;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private List<Event> events;

    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL)
    private List<Participation> participations;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Comment> comments;

    public CerebookUser() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
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

    public Membership getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(Membership membershipId) {
        this.membershipId = membershipId;
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
}