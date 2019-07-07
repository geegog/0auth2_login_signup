package com.demo.auth.user.domain.model;

import com.demo.auth.common.domain.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
public class User extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @Column(nullable = false)
    private String name;

    private String imageUrl;

    @Column(nullable = false)
    private Boolean emailVerified = false;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

}
