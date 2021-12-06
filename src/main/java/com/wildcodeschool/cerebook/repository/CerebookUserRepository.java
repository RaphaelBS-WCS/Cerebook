package com.wildcodeschool.cerebook.repository;

import com.wildcodeschool.cerebook.entity.CerebookUser;

import com.wildcodeschool.cerebook.entity.Post;
import com.wildcodeschool.cerebook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.List;

@Repository
public interface CerebookUserRepository extends JpaRepository<CerebookUser, Long> {
    CerebookUser findCerebookUserById(Long user);

  /*  @Query("SELECT c FROM CerebookUser c INNER JOIN User u ON c.user = u WHERE u.username = :username")
    CerebookUser findCerebookUserByUsername(@Param("username") String username);
*/
}
