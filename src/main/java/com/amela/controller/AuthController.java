package com.amela.controller;

import com.amela.model.Role;
import com.amela.model.User;
import com.amela.repository.IRoleRepository;
import com.amela.repository.IUserRepository;
import com.amela.request.SignUpForm;
import com.amela.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok(200);
    }

    @PostMapping("/api/signup")
    public ResponseEntity registerUser(@Valid @RequestBody SignUpForm signUpRequest) {

        // Creating user's account
        User user = new User(signUpRequest.getUsername(), passwordEncoder.encode(signUpRequest.getPassword()), signUpRequest.getName(), null);

        Set<String> strRoles = signUpRequest.getRole();
        Set roles = new HashSet<>();

        strRoles.forEach(role -> {
            switch(role) {
                case "admin":
                    Role adminRole = roleRepository.findByName("ADMIN");
                    roles.add(adminRole);
                    break;
                case "pm":
                    Role pmRole = roleRepository.findByName("PM");
                    roles.add(pmRole);
                    break;
                default:
                    Role userRole = roleRepository.findByName("USER");
                    roles.add(userRole);
                    break;
            }
        });

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok().body("User registered successfully!");
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>("Hello World", HttpStatus.OK);
    }

    @GetMapping("/user")
    public String user(){
        return "error.404.html";
    }
}
