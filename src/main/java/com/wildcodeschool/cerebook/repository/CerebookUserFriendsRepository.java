package com.wildcodeschool.cerebook.repository;

import com.wildcodeschool.cerebook.entity.CerebookUser;
import com.wildcodeschool.cerebook.entity.CerebookUserFriends;
import com.wildcodeschool.cerebook.entity.ids.CerebookUserFriendsId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CerebookUserFriendsRepository extends JpaRepository<CerebookUserFriends, CerebookUserFriendsId> {
    List<CerebookUserFriends> findCerebookUserFriendsByOriginatedUser(CerebookUser cerebookUser);
}