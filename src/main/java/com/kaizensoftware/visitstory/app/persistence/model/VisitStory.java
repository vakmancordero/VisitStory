package com.kaizensoftware.visitstory.app.persistence.model;

import com.kaizensoftware.visitstory.app.persistence.model.auth.User;
import com.kaizensoftware.visitstory.common.persistence.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "visitstory")
@Table(name = "visitstory", schema = "visitStory")
public class VisitStory extends BaseEntity {

    private String name;
    private String description;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "permission_id")
    private Permission permission;

    @OneToMany(mappedBy = "visitStory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<View> views;

    @OneToMany(mappedBy = "visitStory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Content> contents;

    @OneToMany(mappedBy = "visitStory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "visitStory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaggedUser> taggedUsers;

}
