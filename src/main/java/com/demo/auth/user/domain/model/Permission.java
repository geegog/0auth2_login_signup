package com.demo.auth.user.domain.model;

import com.demo.auth.common.domain.model.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
public class Permission extends BaseEntity {

    @NotNull
    private String name;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles;

}
