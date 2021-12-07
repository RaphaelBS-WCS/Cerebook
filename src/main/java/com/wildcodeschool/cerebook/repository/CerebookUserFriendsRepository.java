package com.wildcodeschool.cerebook.repository;

import com.wildcodeschool.cerebook.entity.CerebookUserFriends;
import com.wildcodeschool.cerebook.entity.ids.CerebookUserFriendsId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CerebookUserFriendsRepository extends JpaRepository<CerebookUserFriends, CerebookUserFriendsId> {
}