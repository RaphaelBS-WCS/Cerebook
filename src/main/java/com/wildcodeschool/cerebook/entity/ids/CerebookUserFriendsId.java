package com.wildcodeschool.cerebook.entity.ids;

import com.wildcodeschool.cerebook.entity.CerebookUser;

import java.io.Serializable;

public class CerebookUserFriendsId implements Serializable {
    private CerebookUser originatedUser;
    private CerebookUser friend;

    public CerebookUserFriendsId() {}
    public CerebookUserFriendsId(CerebookUser originatedUser, CerebookUser friend) {
        this.originatedUser = originatedUser;
        this.friend = friend;
    }
}