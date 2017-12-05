package com.cyber.api.services;

import com.cyber.api.models.Authority;
import com.cyber.api.models.User;
import com.cyber.api.repository.AuthorityRepository;
import com.cyber.api.repository.UserRepository;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    public void save(User user, List authorities) {
        String password = encoder.encode(user.getPassword());
        user.setPassword(password);

        userRepository.save(user);

        for (Object row : authorities) {
            Authority authority = new Authority();
            authority.setUsername(user.getUsername());
            authority.setAuthority(row.toString());
            authorityRepository.save(authority);
        }
    }

    public User getAuthenticatedUser() {
        org.springframework.security.core.userdetails.User principal
                = (org.springframework.security.core.userdetails.User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        System.out.println("get principal");
        System.out.println(principal);
        User user = userRepository.findByUsername(principal.getUsername());
        return user;
    }

}
