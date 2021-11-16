package com.wildcodeschool.cerebook.repository;

import com.wildcodeschool.cerebook.entity.CerebookUser;
import com.wildcodeschool.cerebook.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    void deleteById(Long id);

    List<Post> findAllByAuthor(CerebookUser author);

    List<Post> findAllByAuthorFriendsMatchesOrAuthor(List<CerebookUser> author_friends, CerebookUser author);
}
