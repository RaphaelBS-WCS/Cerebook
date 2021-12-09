package com.wildcodeschool.cerebook.repository;

import com.wildcodeschool.cerebook.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MembershipRepository extends JpaRepository<Membership, Long> {

    @Query("SELECT m FROM Membership m")
    List<Membership> getAll();
}