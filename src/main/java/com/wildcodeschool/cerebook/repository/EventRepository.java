package com.wildcodeschool.cerebook.repository;

import com.wildcodeschool.cerebook.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {


    Event findEventByName(String name);

    Event getEventById(Long id);

    @Query("SELECT e FROM Event e WHERE e.creator = :creator AND e.id = :eventId")
    Event getEventByIdAndByCreator(@Param("creator") User creator, @Param("eventId") Long eventId);

    @Query("SELECT e FROM Event e WHERE e.creator = :creator")
    List<Event> getAllEventsByCreator(@Param("creator") User creator);

 /*   @Modifying
    @Query("UPDATE Event e SET e.backgroundPhoto= :backgroundPhoto, e.date= :date, e.description= :description, e.name= :name, e.eventCategory= :eventCategory WHERE e.id = :eventId AND e.creator = :creator")
    void updateEventByIdAndByUser(@Param("id") long eventId, @Param("creator") User creator, @Param("backgroundPhoto") String backgroundPhoto, @Param("date") Date date, @Param("description") String description, @Param("name") String name, @Param("eventCategory") EventCategory eventCategory);*/
}
