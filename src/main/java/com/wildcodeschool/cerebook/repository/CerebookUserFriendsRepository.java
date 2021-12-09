package com.wildcodeschool.cerebook.repository;

import com.wildcodeschool.cerebook.entity.CerebookUser;
import com.wildcodeschool.cerebook.entity.CerebookUserFriends;
import com.wildcodeschool.cerebook.entity.ids.CerebookUserFriendsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CerebookUserFriendsRepository extends JpaRepository<CerebookUserFriends, CerebookUserFriendsId> {

    // Query served to get all friend requests (unconfirmed relation)
    @Query("select c from CerebookUserFriends c where c.originatedUser = :originatedUser AND c.isAccepted = false")
    public List<CerebookUserFriends> getAllFriendRequests(@Param("originatedUser") CerebookUser originatedUser);

    // Query served to get all friends of current  (confirmed relation)
    @Query("SELECT c FROM CerebookUserFriends c WHERE c.originatedUser = :originatedUser AND c.isAccepted = true")
    public List<CerebookUserFriends> findCerebookUserFriendsByOriginatedUserAndAccepted(@Param("originatedUser") CerebookUser originatedUser);

    // Query served to save a friend into friend list after accepting the invitation
    @Modifying(clearAutomatically = true)
    @Query("UPDATE CerebookUserFriends c SET c.isAccepted = true WHERE c.originatedUser = :originatedUser AND c.friend = :friend")
    @Transactional
    void acceptFriend(@Param("originatedUser") CerebookUser originatedUser, @Param("friend") CerebookUser friend);

    // Query served to add a suggested friend into friend list
    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO CerebookUserFriends c (originatedUser, friend, isAccepted) VALUES (:originatedUser, :friend, false)",
            nativeQuery = true)
    @Transactional
    void addFriend(@Param("originatedUser") CerebookUser originatedUser, @Param("friend") CerebookUser friend);
/*
    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM Friendship f WHERE (f.userSender = :user AND f.userReceiver = :friend) " +
            "OR (f.userSender = :friend AND f.userReceiver = :user)")
    void deleteFriendRequests(@Param("user") User user, @Param("friend") User friend);*/
}
