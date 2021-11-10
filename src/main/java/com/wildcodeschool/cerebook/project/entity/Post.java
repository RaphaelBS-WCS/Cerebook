package com.wildcodeschool.cerebook.project.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date createdAt;
    private String title;
    @Column(columnDefinition="TEXT")
    private String content;
    private String video;

    @ManyToOne
    // NRO 2021-11-10 : i get the id but still, i think you
    //  would be better without the JoinColumn
    @JoinColumn(name = "cerebookuser_id")
    private CerebookUser author;

    @ManyToOne
    // NRO 2021-11-10 : useless JoinColumn
    @JoinColumn(name = "event_id")
    // NRO 2021-11-10 : bad naming, it should be event
    private Event eventId;

    @ManyToOne
    @JoinColumn(name = "picture_id")
    private Picture picture;

    @OneToMany(mappedBy = "postId", cascade = CascadeType.ALL)
    private List<Comment> comments;

    public Post() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public CerebookUser getAuthor() {
        return author;
    }

    public void setAuthor(CerebookUser author) {
        this.author = author;
    }

    public Event getEventId() {
        return eventId;
    }

    public void setEventId(Event eventId) {
        this.eventId = eventId;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}