package com.api.services;

import com.api.model.User;
import com.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class ResetPasswordService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ValidationService validationService;

    public void updateResetPasswordToken(String token, String email) {
        User user = userRepository.findByEmail(email);
        if(user != null) {
            userRepository.updateResetPasswordToken(token, email);
        } else {
            throw new UsernameNotFoundException("Could not find any user with email " + email);
        }
    }

    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = bCryptPasswordEncoder.encode(newPassword);
        userRepository.updatePassword(encodedPassword, user.getUEmail());
        userRepository.updateResetPasswordToken(null, user.getUEmail());
    }

}
