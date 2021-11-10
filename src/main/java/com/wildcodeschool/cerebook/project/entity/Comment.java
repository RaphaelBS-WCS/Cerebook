package com.wildcodeschool.cerebook.project.entity;

import javax.persistence.*;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition="TEXT")
    private String content;

    @ManyToOne
    // NRO 2021-11-10 : good example of why you should not use JoinColumn by
    //  default :-) There is a spelling mistake
    @JoinColumn(name = "cerebook_uder_id")
    private CerebookUser author;

    @ManyToOne(cascade = CascadeType.REFRESH)
    // NRO 2021-11-10 : Just remove this useless annotation
    @JoinColumn(name = "post_id")
    private Post postId;

    public Comment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CerebookUser getAuthor() {
        return author;
    }

    public void setAuthor(CerebookUser author) {
        this.author = author;
    }

    public Post getPostId() {
        return postId;
    }

    public void setPostId(Post postId) {
        this.postId = postId;
    }
}
