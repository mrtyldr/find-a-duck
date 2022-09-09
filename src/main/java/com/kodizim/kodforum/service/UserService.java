package com.kodizim.kodforum.service;


import com.kodizim.kodforum.entity.User;
import com.kodizim.kodforum.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public void addUser(String userId,String userName, String email){
        var user = new User(userId,userName,email);

        userRepository.save(user);
    }
}
