package com.amela.service.user;

import com.amela.model.User;
import com.amela.service.IGeneralService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface IUserService extends IGeneralService<User>, UserDetailsService {
    Optional<User> findByUsername(String username);
    Optional<User> findById(Long id);
}
