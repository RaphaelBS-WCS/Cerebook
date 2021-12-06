package com.wildcodeschool.cerebook.repository;

import com.wildcodeschool.cerebook.entity.CerebookUser;
import com.wildcodeschool.cerebook.entity.Event;
import com.wildcodeschool.cerebook.entity.Participation;
import com.wildcodeschool.cerebook.entity.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {

    @Query("SELECT p FROM Participation p WHERE p.event = :event")
    List<Participation> getAllByEvent(@Param("event") Event event);
}
