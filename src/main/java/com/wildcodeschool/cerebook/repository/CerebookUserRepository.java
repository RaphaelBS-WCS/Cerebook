package com.wildcodeschool.cerebook.repository;

import com.wildcodeschool.cerebook.entity.CerebookUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CerebookUserRepository extends JpaRepository<CerebookUser, Long> {

    CerebookUser findCerebookUserByUserId(Long user_id);



}
