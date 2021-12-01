package com.wildcodeschool.cerebook.repository;

import com.wildcodeschool.cerebook.entity.CerebookUser;
import com.wildcodeschool.cerebook.entity.Event;
import com.wildcodeschool.cerebook.entity.Post;
import com.wildcodeschool.cerebook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {


    Event findEventByName(String name);

    @Query("SELECT e FROM Event e WHERE e.creator = :creator")
    List<Event> getAllEventsByCreator(@Param("creator") User creator);


}
