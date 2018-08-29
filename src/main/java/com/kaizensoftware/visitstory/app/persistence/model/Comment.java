package com.kaizensoftware.visitstory.app.persistence.model;

import com.kaizensoftware.visitstory.app.persistence.model.auth.User;
import com.kaizensoftware.visitstory.common.persistence.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "Comment")
@Table(name = "Comment", schema = "visitStory")
public class Comment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visitstory_id")
    private VisitStory visitStory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id")
    private Content content;

}
