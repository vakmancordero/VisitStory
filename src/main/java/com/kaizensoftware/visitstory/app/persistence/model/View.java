package com.kaizensoftware.visitstory.app.persistence.model;

import com.kaizensoftware.visitstory.app.persistence.model.auth.User;
import com.kaizensoftware.visitstory.common.persistence.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "View")
@Table(name = "View", schema = "visitStory")
public class View extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visitstory_id")
    private VisitStory visitStory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
