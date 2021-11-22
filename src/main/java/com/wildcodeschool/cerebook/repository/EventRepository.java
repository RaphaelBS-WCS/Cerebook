package com.wildcodeschool.cerebook.repository;


import com.wildcodeschool.cerebook.entity.CerebookUser;
import com.wildcodeschool.cerebook.entity.Event;
import com.wildcodeschool.cerebook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    Event findEventByName (String name);
    Event findEventByCreator (CerebookUser user);
    Event findEventByDate (Date date);
    

}