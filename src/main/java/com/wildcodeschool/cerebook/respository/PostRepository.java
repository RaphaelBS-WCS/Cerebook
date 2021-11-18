package com.wildcodeschool.cerebook.repository;

import com.wildcodeschool.cerebook.entity.CerebookUser;
import com.wildcodeschool.cerebook.entity.Post;
import org.hibernate.sql.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.security.Permission;
import java.security.Principal;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByAuthorOrderByCreatedAtDesc(CerebookUser author);
/*
    @Query("SELECT p FROM Post p WHERE p.author IN :author.friends")
    List<Post> findAllByAuthorOrByAuthorFriends(@Param("author") CerebookUser author);*/

    @Query("SELECT p FROM Post p INNER JOIN CerebookUser c ON p.author=c WHERE p.author = :author OR p.author IN (SELECT cf FROM c.friends cf) ORDER BY p.createdAt DESC ")
    List<Post> findAllByAuthorOrByAuthorFriends(@Param("author") CerebookUser author);
};