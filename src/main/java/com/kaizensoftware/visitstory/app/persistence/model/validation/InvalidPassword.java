package com.kaizensoftware.visitstory.app.persistence.model.validation;

import com.kaizensoftware.visitstory.common.persistence.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "InvalidPassword")
@Table(name = "InvalidPassword", schema = "visitStory")
public class InvalidPassword extends BaseEntity {

    private String password;

}
