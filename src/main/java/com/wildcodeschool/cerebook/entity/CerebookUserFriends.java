package com.wildcodeschool.cerebook.entity;

import com.wildcodeschool.cerebook.entity.ids.CerebookUserFriendsId;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "cerebook_user_friends")
@IdClass(CerebookUserFriendsId.class)
public class CerebookUserFriends implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "cerebook_user_id")
    private CerebookUser originatedUser;

    @Id
    @ManyToOne
    @JoinColumn(name = "friends_id")
    private CerebookUser friend;

    /*public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    private boolean isAccepted;*/

    public CerebookUser getOriginatedUser() {
        return originatedUser;
    }

    public void setOriginatedUser(CerebookUser originatedUser) {
        this.originatedUser = originatedUser;
    }

    public CerebookUser getFriend() {
        return friend;
    }

    public void setFriend(CerebookUser friend) {
        this.friend = friend;
    }
}