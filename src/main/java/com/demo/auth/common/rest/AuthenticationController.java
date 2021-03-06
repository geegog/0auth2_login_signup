package com.demo.auth.common.rest;

import com.demo.auth.common.application.dto.ApiResponse;
import com.demo.auth.common.application.dto.AuthResponse;
import com.demo.auth.common.application.dto.LoginRequest;
import com.demo.auth.common.application.dto.SignUpRequest;
import com.demo.auth.common.application.exception.BadRequestException;
import com.demo.auth.common.application.service.TokenProvider;
import com.demo.auth.user.domain.model.AuthProvider;
import com.demo.auth.user.domain.model.Permission;
import com.demo.auth.user.domain.model.Role;
import com.demo.auth.user.domain.model.User;
import com.demo.auth.user.domain.repository.PermissionRepository;
import com.demo.auth.user.domain.repository.RoleRepository;
import com.demo.auth.user.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }

        // Creating user's account
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setProvider(AuthProvider.local);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Permission permission = new Permission();
        permission.setName("READ");

        Permission savedPermission = permissionRepository.save(permission);

        Set permissions = new HashSet<>();
        permissions.add(savedPermission);

        Role role = new Role();
        role.setName("USER");
        role.setPermissions(permissions);

        Role savedRole = roleRepository.save(role);

        Set roles = new HashSet<>();
        roles.add(savedRole);

        user.setRoles(roles);

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "User registered successfully@"));
    }


}