package com.dayappchallenge.auth.user.rest;

import com.dayappchallenge.auth.common.application.exception.ResourceNotFoundException;
import com.dayappchallenge.auth.common.infrastructure.security.CurrentUser;
import com.dayappchallenge.auth.common.infrastructure.security.UserPrincipal;
import com.dayappchallenge.auth.user.domain.model.User;
import com.dayappchallenge.auth.user.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

}
