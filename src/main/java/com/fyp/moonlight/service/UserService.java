package com.fyp.moonlight.service;

import com.fyp.moonlight.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean doesUserExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public void saveUser(String email, String password) {
        var encryptedPassword = passwordEncoder.encode(password);
        userRepository.save(new com.fyp.moonlight.model.User(email, encryptedPassword, Instant.now()));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Could not find user with email: " + email));
        return new User(user.getEmail(), user.getPassword(), List.of());
    }

    public Optional<com.fyp.moonlight.model.User> findUserByUserId(String userId) {
        return userRepository.findById(userId);
    }

    public Optional<com.fyp.moonlight.model.User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
