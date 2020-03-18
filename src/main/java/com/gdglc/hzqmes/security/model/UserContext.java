package com.gdglc.hzqmes.security.model;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * 
 * @author vladimir.stankovic
 *
 * Aug 4, 2016
 */
public class UserContext {
    private final String username;
    private final String avatar;
    private final String nickname;
    private final List<GrantedAuthority> authorities;

    private UserContext(String username, List<GrantedAuthority> authorities) {
        this.username = username;
        this.authorities = authorities;
        this.avatar = null;
        this.nickname = null;
    }

    private UserContext(String username, List<GrantedAuthority> authorities, String avatar, String nickname) {
        this.username = username;
        this.authorities = authorities;
        this.avatar = avatar;
        this.nickname = nickname;
    }

    public static UserContext create(String username, List<GrantedAuthority> authorities) {
        if (StringUtils.isBlank(username)){
            throw new IllegalArgumentException("Username is blank: " + username);
        }
        return new UserContext(username, authorities);
    }

    /**
     * 新增方法，将用户头像和昵称保存起来
     * @param username
     * @param authorities
     * @param avatar
     * @return
     */
    public static UserContext create(String username, List<GrantedAuthority> authorities, String avatar, String nickname) {
        if (StringUtils.isBlank(username)){
            throw new IllegalArgumentException("Username is blank: " + username);
        }
        return new UserContext(username, authorities, avatar, nickname);
    }

    public String getUsername() {
        return username;
    }

    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getNickname() {
        return nickname;
    }
}
