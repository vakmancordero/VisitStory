package com.kaizensoftware.visitstory.app.persistence.model;

import com.kaizensoftware.visitstory.common.persistence.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "User")
@Table(name = "User", schema = "visitStory")
public class User extends BaseEntity {

    @Column(length = 60, unique = true, nullable = false)
    private String email;

    @Column(length = 150, nullable = false)
    private String password;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String lastName;

    @Column(length = 15)
    private String phone;

    @Column(length = 10)
    private String phoneRegion;

    @Column
    private LocalDate birthday;

    @Column(columnDefinition = "TEXT")
    private String aboutMe;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gender_reference_id")
    private GenderReference genderReference;

    @Column(nullable = false)
    private boolean enabled;

    @Column(unique = true)
    private String confirmationToken;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VisitStory> visitStories;

}
